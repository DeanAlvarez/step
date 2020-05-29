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
const canvasSize = 800;
var targetSize = 10;
var targets = [];

//this function starts the game. This is what is called onload by the webpage.
function startGame() {
    gameArea.start();
    targets[0] = new target(targetSize,"red",240,240);
    updateGameArea();
}

//This function checks if a click is in target by seeing if the red value is 255
function inTarget(r){
    if(r != 0){
        console.log("target clicked");
        return true;
    }
    return false;
}

var gameArea = {
    canvas : document.createElement("canvas"),
    start : function() {
        this.canvas.width = canvasSize;
        this.canvas.height = canvasSize;
        this.canvas.setAttribute("id", "gameCanvas");
        this.context = this.canvas.getContext("2d");
        var canv = this.canvas;
        document.getElementById("gameArea").appendChild(canv);
        this.canvas.addEventListener('click', (e) => {
            //mouse potition
   	    	const pos = {
    	        x: e.clientX - this.canvas.offsetLeft,
    	        y: e.clientY - this.canvas.offsetTop
  	        };
            // get pixel under cursor
  	        const pixel = ctx.getImageData(pos.x, pos.y, 3, 3).data;
            if(inTarget(pixel[0])){
                updateGameArea();
            }
		});
    },
    clear : function() {
    	this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
  	},
  	stop : function() {
    	clearInterval(this.interval);
  	}
}

function target(size, color, x, y){
    this.width = size;
    this.height = size;   
    this.x = x;
    this.y = y;    
    this.update = function() {
        ctx = gameArea.context;
        ctx.fillStyle = color;
        ctx.fillRect(this.x, this.y, this.width, this.height);
    }
    this.randomPos = function() {
        min = 20;
        max = canvasSize;
        this.x = Math.floor(Math.random()*(max - min)+min);
        this.y = Math.floor(Math.random()*(max - min)+min);  
    }
    this.currentX = function(){
        return this.x;
    }
    this.currentY = function(){
        return this.y;
    }
}

//updates the game area and puts the target in a new random spot. 
function updateGameArea(){
    gameArea.clear();
    targets[0].randomPos();    
    targets[0].update();
}
