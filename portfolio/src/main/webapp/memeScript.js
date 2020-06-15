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
function start(){
    startLoadingBar();
    getMeme();
}




function startLoadingBar(){
    var percent = 0;
    var bar = document.getElementById("theBarPart");
    var width = 1;
    var id = setInterval(frame, 100);
    function frame() {
        if (width >= 100) {
            clearInterval(id);
        } else {
            width++;
            elem.style.width = width + "%";
        }
    }
}

function getMeme() {
  fetch('/meme-reviewer').then(response => response.json()).then((meme) => {
    const dataElement = document.getElementById('server-data');
    dataElement.innerText = '';
    dataElement.appendChild(createImgElement(meme.url));
    dataElement.appendChild(createMessageElement(meme.message));
  });
}


/** Creates an <h2> element containing the users name. */
function createImgElement(url) {
  const imgElement = document.createElement('img');
  imgElement.src = url;
  return imgElement;
}

/** Creates a <p> element containing the meme message */
function createMessageElement(message) {
  const pElement = document.createElement('p');
  pElement.innerText = message.message;
  return pElement;
}
