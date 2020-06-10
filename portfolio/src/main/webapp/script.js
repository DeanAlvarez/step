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


function getComments() {
  fetch('/comment').then(response => response.json()).then((comments) => {
    const dataElement = document.getElementById('server-data');
    dataElement.innerText = '';
    comments.forEach((comment) => {
        dataElement.appendChild(createUserElement(comment));
        dataElement.appendChild(createCommentElement(comment))
    })
  });
}


/** Creates an <h2> element containing the users name. */
function createUserElement(comment) {
  const h2Element = document.createElement('h2');
  h2Element.innerText = comment.username;
  return h2Element;
}

/** Creates a <p> element containing the text of the comment */
function createCommentElement(comment) {
  const pElement = document.createElement('p');
  pElement.innerText = comment.comment;
  return pElement;
}
