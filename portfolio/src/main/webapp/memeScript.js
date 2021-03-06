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

let arr = ["parsing image for humor", "calculating latent humor residuals", "mapping stochastic eigenvectors", "filtering negative humor space",
	"optimizing humor function", "applying schroeder-bernstein", "calculating comedy convolutions"];

// Function to randomly shuffle an array
// Using the FISHER-YATES SHUFFLE algorithm
function shuffle(array){
	for(let i = array.length - 1; i > 0; i--){
		let j = Math.floor(Math.random() * (i+1)); //random number j such that 0<j<i
		let temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}

function start(){
    var memeDiv = document.getElementById('server-data');
    memeDiv.style.display = "none";
    getMeme();
    shuffle(arr);
	console.log(arr);
    startLoadingBar();
}

function startLoadingBar(){
    var bar = document.getElementById("theBarPart");
    var width = 5;
    var counter = 0;
    bar.innerText = "Reviewing meme...";
    var id = setInterval(frame, 100);
    function frame() {
        if (width >= 100) {
            clearInterval(id);
	    document.getElementById("barParent").style.display = "none";
            document.getElementById("server-data").style.display = "block";
        } else {
	    if(width % 20 === 0){
	        bar.innerText = arr[counter];
		counter++;
	    }
            width++;
            bar.style.width = width + "%";
        }
    }
}

function getMeme() {
  fetch('/meme-reviewer').then(response => response.json()).then((meme) => {
    const dataElement = document.getElementById('server-data');
    dataElement.innerText = '';
	const h1El = document.createElement('h1');
	h1El.innerText = "This meme is: ";
	const h2El = document.createElement('h2');
	h2El.innerText = "NOT FUNNY";
	dataElement.appendChild(h1El);
	dataElement.appendChild(h2El);
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
  pElement.innerText = message;
  return pElement;
}
