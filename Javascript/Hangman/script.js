var solution = "get well soon shree".toUpperCase();
var currentSolution = "";
var guesses = 0;
var maxGuesses = 10;
var input, canvas, c, guesstxt, solutiontxt;
var validre = new RegExp("[A-Za-z]");

window.onload = function load() {
    input = document.getElementById("inputbox");
    canvas = document.getElementById("canvas");
    ctx = canvas.getContext("2d");
    guesstxt = document.getElementById("guesses");
    solutiontxt = document.getElementById("solution");
    guesstxt.innerText = "Guesses: " + guesses;
    solutiontxt.innerText = printSolution();
    console.clear();
    draw();
};

function guess() {
    var letter = (input.value + "").toUpperCase();
    if (letter == "") return;
    console.clear();
    if (guesses < maxGuesses) {
        console.log("You guessed: " + letter);
        if (validre.test(letter)) {
            if (solution.indexOf(letter) != -1) {
                var re = new RegExp(letter, 'g');
                solution = solution.replace(re, letter.toLowerCase());
            } else {
                guesses++;
            }
        } else {
            console.log(letter + " is an invalid input.");
        }
        console.log(printSolution());
        console.log("Guesses: " + guesses);

        solutiontxt.innerText = printSolution();
        guesstxt.innerText = "Guesses: " + guesses;

        input.value = "";
    } else {
        console.log("MAN HANGED!");
    }
}

function printSolution() {
    var output = solution.replace(/\W/g, "  ");
    output = output.replace(/[A-Z]/g, "_ ");
    output = output.replace(/[a-z]/g, "\$& ");
    return output;
}

var ang = 0;    
var l = new limb(new point(100, 100), new point(100, 200), 100, 0, 1, "#A06600");

function draw() {
    ctx.fillStyle = "#C7F5FF";
	ctx.fillRect(0, 0, canvas.width, canvas.height); // setting background
    window.requestAnimationFrame(draw);
    l.j.setAngle(ang);
    ang++;
    l.update();
    l.draw();
}

function drawGallow() {
	ctx.fillStyle = "beige";
}




function limb(f, e, l, a, r, color) {
	this.j = new joint(f, e, l, a);
	this.r = r;
	this.color = color;

	this.update = function() {
		this.j.setEnd(this.j.e);
	};

	this.draw = function() {
		ctx.fillStyle = color;
		ctx.beginPath();
		ctx.arc(this.j.f.x, this.j.f.y, this.r, 0, 2 * Math.PI); // fulcrum circle
		ctx.fill();

		var r = new quad(
			new point(this.j.f.x - this.r, this.j.f.y),
			new point(this.j.f.x + this.r, this.j.f.y),
			new point(this.j.f.x + this.r, this.j.f.y + this.j.l),
			new point(this.j.f.x - this.r, this.j.f.y + this.j.l)
		);
		r.rotate(this.j.f, this.j.a - 90);
		r.draw();

		ctx.beginPath();
		ctx.arc(this.j.e.x, this.j.e.y, this.r, 0, 2 * Math.PI); // end circle
		ctx.fill();
	};
}

function quad(p1, p2, p3, p4, color) { // quadrilateral object
	this.p1 = p1;
	this.p2 = p2;
	this.p3 = p3;
	this.p4 = p4;

	this.draw = function() {
		ctx.fillStyle = color;
		ctx.beginPath();
		ctx.moveTo(p1.x, p1.y);
		ctx.lineTo(p2.x, p2.y);
		ctx.lineTo(p3.x, p3.y);
		ctx.lineTo(p4.x, p4.y);
		ctx.fill();
	};

	this.rotate = function(com, rot) {
		this.p1 = rotatePoint(com, this.p1, rot);
		this.p2 = rotatePoint(com, this.p2, rot);
		this.p3 = rotatePoint(com, this.p3, rot);
		this.p4 = rotatePoint(com, this.p4, rot);
	};
}

function point(x, y) {
    this.x = x;
    this.y = y;
}

function joint(f, e, l, a) {
	this.f = f;
	this.e = e;
	this.l = l;
	this.a = a;

	this.setAngle = function(a) {
		this.a = a;
		var oX = this.l * Math.cos(this.a * Math.PI / 180); // offset x
		var oY = this.l * Math.sin(this.a * Math.PI / 180); // offset y
		this.e.x = this.f.x + oX;
		this.e.y = this.f.y + oY;
	};

	this.setEnd = function(e) {
		this.e = e;
		this.a = Math.asin(this.e.x / this.l) * 180 / Math.PI; // setting angle
	};
}