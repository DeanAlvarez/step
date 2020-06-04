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
import com.google.sps.data.User;
import com.google.gson.Gson;
import java.util.HashMap;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private HashMap<String,User> users;

  @Override
  public void init() {
      users = new HashMap<>();
      User u1 = new User(1,"Dean","test");
      User u2 = new User(2,"AlsoDean","test123");
      String key1 = "u1";
      String key2 = "u2";
      users.put(key1,u1);
      users.put(key2,u2);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;");
    String json = convertToJsonUsingGson(users);
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String name = request.getParameter("name-input");
    String password = request.getParameter("password-input");

    Entity userEntity = new Entity("User");
    userEntity.setProperty("username", name);
    userEntity.setProperty("password", password);
    userEntity.setProperty("userId",0);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(userEntity);
    response.sendRedirect("/index.html");
  }


    /**
   * Converts a ServerStats instance into a JSON string using the Gson library. Note: We first added
   * the Gson library dependency to pom.xml.
   */
  private String convertToJsonUsingGson(HashMap<String,User> map) {
    Gson gson = new Gson();
    String json = gson.toJson(map);
    return json;
  }
}
