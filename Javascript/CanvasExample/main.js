var image = null;



function createImage(elem){
  var canvas = document.getElementById(elem);   // used to get the canvas to draw on it
  var width = canvas.width;         // declares a variable called width and assigns it the width of the canvas
  var height = canvas.height;       // declares a variable called height and assigns it the height of the canvas
  
  var ctx = canvas.getContext("2d");
  var img = new Image();
  img.src ="images/cards/clubs10.png";    // creates a blank SimpleImage with width and height
  
  

  ctx.drawImage(img, 5, 5, img.width/3, img.height/3);           // this was like our print(img);  
}


