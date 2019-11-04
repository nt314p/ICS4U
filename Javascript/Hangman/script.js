var solution = "get well soon shree".toUpperCase();
var currentSolution = ""
var guesses = 0;
var maxGuesses = 10;
var input, canvas, c, guesstxt, solutiontxt;
var validre = new RegExp("[A-Za-z]");

window.onload = function load() {
    input = document.getElementById("inputbox");
    //canvas = document.getElementById("canvas");
    //c = canvas.getContext("2d");
    guesstxt = document.getElementById("guesses");
    solutiontxt = document.getElementById("solution");
    guesstxt.innerText = "Guesses: " + guesses;
    solutiontxt.innerText = printSolution();
    console.clear();
}

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
    output = output.replace(/[a-z]/g, "\$& ")
    return output;
}