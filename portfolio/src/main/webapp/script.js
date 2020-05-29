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
const canvasSize = 480;
var targetSize = 15;

function startGame() {
    gameArea.start();
    clickTarget = new target(targetSize,"red",240,240)
}

var gameArea = {
    canvas : document.createElement("canvas"),
    start : function() {
        this.canvas.width = canvasSize;
        this.canvas.height = canvasSize;
        this.context = this.canvas.getContext("2d");
        var canv = this.canvas;
        document.getElementById("gameArea").appendChild(canv); 
        this.canvas.addEventListener('click', () => {
   	    	console.log('canvas click');
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
        ctx = myGameArea.context;
        ctx.fillStyle = color;
        ctx.fillRect(this.x, this.y, this.width, this.height);
    }
    this.randomPos = function() {
        min = 10;
        max = canvasSize;
        this.x = Math.floor(Math.random()*(max - min)+min);
        this.y = Math.floor(Math.random()*(max - min)+min);  
    }
}
