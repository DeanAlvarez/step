// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter; 
import com.google.sps.data.User;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {


  @Override
  public void init() {
        Entity userEntity = new Entity("User");
        userEntity.setProperty("username", "Jimmy");
        userEntity.setProperty("password", "test123");
        userEntity.setProperty("userId",0);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(userEntity);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("User");
    PreparedQuery results = datastore.prepare(query);

    ArrayList<User> users = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String username = (String) entity.getProperty("username");
      String password = (String) entity.getProperty("password");

      User user = new User(id, username, password);
      users.add(user);
    }

    String json = convertToJsonUsingGson(users);
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String name = request.getParameter("name-input");
    String password = request.getParameter("password-input");
    if(!validate(name,password)){
        Entity userEntity = new Entity("User");
        userEntity.setProperty("username", name);
        userEntity.setProperty("password", password);
    

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(userEntity);
    }
    HttpSession session = request.getSession();
    session.setAttribute("username", name);
    response.sendRedirect("/comments.html");
  }


    /**
   * Converts a ServerStats instance into a JSON string using the Gson library. Note: We first added
   * the Gson library dependency to pom.xml.
   */
  private String convertToJsonUsingGson(ArrayList<?> map) {
    Gson gson = new Gson();
    String json = gson.toJson(map);
    return json;
  }


  private Boolean validate(String username, String password){
    Query query = Query.newEntityQueryBuilder().setKind("User").setFilter(PropertyFilter.eq("username", username)).build();
    PreparedQuery results = datastore.prepare(query);
    Boolean userFound = false;
    Entity user;
    for(Entity entity : results.asIterable()){
        if(((String)entity.getProperty("username")).equals(username)){
            user = entity;
            userFound = true;
            break;
        }
    }
    if(user == null){
        return false;
    }
    return User.validate(password, (String)user.getProperty("password"));
  }
}
