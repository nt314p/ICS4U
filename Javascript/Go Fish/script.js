/* jshint esversion: 6 */

var nameMap = ["shark", "starfish", "whale", "turtle", "octopus", "jellyfish", "crab", "pufferfish", "tuna", "squid", "eel", "seahorse"];
var colorMap = ["#a5bac9", "#ffde91", "#306cba", "#9aba63", "#9c69cf", "#ed9adb", "#e66060", "#f7e883", "#babbd4", "#ff9cc2", "#ccba91", "#ffdf61"];
var names = ["Ethan", "Chris", "Christian", "Sonya", "Kaitlin", "Shree"];
var playerColors = ["red", "orange", "yellow", "green", "blue", "indigo", "purple", "pink"];
var initalCards = 5;
var numPlayers = 8;
var winningPoints = 10;
var players = [];
var canvas, ctx;
var mouseX = 0;
var mouseY = 0;
var mouseDown = false;
var playerIconR = 230;
var playerHandR = 330;
var cardH = 112;
var cardW = 80;
var handW = 150;
var selectedCard = null;
var selectedPlayer = null;
var clientRect;

class Player {
    constructor(name, color) {
        this.hand = new Hand();
        this.points = 0;
        this.name = name;
        this.color = color;
        this.selected = false;
    }

    drawCard() {
        var card = drawCardDeck();
        this.hand.addCard(card);
        if (this instanceof HumanPlayer) {
            console.log(this.name + " drew: " + card);
        }
    }

    addCard(card) {
        this.hand.addCard(card);
    }

    hasCard(card) {
        return this.hand.hasCard(card);
    }

    removeCard(card) {
        return this.hand.removeCard(card);
    }

    ensureEnoughCards() {
        if (this.hand.hand.length == 0) { // hand is empty, draw 5 cards
            console.log(this.name + " ran out of cards, drawing 5 cards.");
            for (var i = 0; i < 5; i++) {
                this.drawCard();
            }
        }
    }

    removeMatches() {
        var matches = this.hand.removeMatches();
        for (var i = 0; i < matches.length; i++) {
            console.log(this.name + " matched " + matches[i].getName());
            this.points += 1;
        }
        this.ensureEnoughCards();
        if (this.hand.hasMatches()) {
            this.removeMatches();
        }
    }

    takeTurn(player, card) {
        console.log(this.name + ": Hey " + player.name + ", do you have a " + card.getName() + "?");
        if (player.hasCard(card)) { // get card
            console.log(player.name + ": Yes I do...");
            this.addCard(player.removeCard(card));
            player.ensureEnoughCards();
        } else { // Go Fish!
            console.log(player.name + ": Sorry, go fish!");
            this.drawCard();
        }
        this.removeMatches();

        console.log(this.name + " has " + this.points + " point(s).");
        return this.points >= winningPoints;
    }

    draw(i) {
        var dAng = 360 / players.length;
        var deg = (i * dAng + 90) * Math.PI / 180;
        var hidden = (i != 0);
        ctx.fillStyle = this.color;
        var cX = playerIconR * Math.cos(deg) + 400;
        var cY = playerIconR * Math.sin(deg) + 400;
        var mouseOver = (900 >= Math.pow(cX - mouseX, 2) + Math.pow(cY - mouseY, 2));

        ctx.beginPath();
        ctx.ellipse(cX, cY, 30, 30, 0, 0, 2 * Math.PI);
        ctx.fill();

        if (mouseOver && mouseDown) {
            selectedPlayer = this;
        }
        this.selected = (selectedPlayer == this);

        if (hidden && (this.selected || mouseOver)) {
            ctx.strokeStyle = "white";
            ctx.beginPath();
            ctx.ellipse(cX, cY, 30, 30, 0, 0, 2 * Math.PI);
            ctx.stroke();
        }

        this.hand.drawHand(playerHandR * Math.cos(deg) + 400, playerHandR * Math.sin(deg) + 400, deg * 180 / Math.PI - 90, hidden);
    }
}

class BullyRandom extends Player {
    constructor() {
        super(names.popRandomElement());
    }

    takeTurn() {
        return super.takeTurn(players[0], this.hand.hand.randomElement());
    }
}

class RandomPlayer extends Player {
    constructor() {
        super(names.popRandomElement(), playerColors.popRandomElement());
    }

    takeTurn() {
        var player = this;
        while (player == this) {
            player = players.randomElement();
        }
        return super.takeTurn(player, this.hand.hand.randomElement());
    }
}

class HumanPlayer extends Player {
    constructor(name) {
        super(name);
    }

    takeTurn(player, card) {
        return super.takeTurn(player, card);
    }
}

class Hand {
    constructor() {
        this.hand = [];
    }

    addCard(card) {
        this.hand.push(card);
    }

    hasCard(card) {
        return this.indexOf(card) != -1;
    }

    indexOf(card) {
        for (var i = 0; i < this.hand.length; i++) {
            if (this.hand[i].equals(card)) {
                return i;
            }
        }
        return -1;
    }

    removeCard(card) {
        var index = this.indexOf(card);
        return this.hand.splice(index, 1)[0]; // return deleted items
    }

    removeIndex(index) {
        return this.hand.splice(index, 1)[0]; // return deleted items
    }

    removeAll(card) {
        while (this.indexOf(card) != -1) {
            this.removeCard(card);
        }
    }

    cardArr() {
        return this.hand.slice(); // shallow copy array
    }

    drawHand(x, y, deg, flipped) {
        var offset, start;
        var maxOffset = 60;

        start = -handW / 2;
        offset = handW / (this.hand.length - 1);
        offset = Math.min(maxOffset, offset);
        start = -(offset * (this.hand.length - 1)) / 2;

        var tW = Math.min(cardW / 2, offset / 2);

        ctx.translate(x, y);
        ctx.rotate(deg * Math.PI / 180);

        for (var i = 0; i < this.hand.length; i++) {
            var tX = i * offset + start;
            var xHover = (mouseX > x + tX - tW && mouseX < x + tX + tW);
            var yHover = (mouseY > y - cardH / 2 && mouseY < y + cardH / 2);
            this.hand[i].draw(tX, 0, 0, flipped, xHover && yHover && !flipped);
        }

        ctx.rotate(-deg * Math.PI / 180);
        ctx.translate(-x, -y);
    }

    hasMatches() {
        var cards = [];

        for (var i = 0; i < this.hand.length; i++) {
            var currCard = this.hand[i];
            if (cards[currCard.cardNum] == null) { // no cards no match
                cards[currCard.cardNum] = true;
            } else { // card already there, we found a match
                return true;
            }
        }
        return false;
    }

    removeMatches() {
        var cards = [];
        var removedCards = [];
        var indexesToPurge = [];
        for (var k = 0; k < nameMap.length; k++) {
            cards[k] = -1; // fill array with -1 for initialization
        }
        for (var i = 0; i < this.hand.length; i++) {
            var currCard = this.hand[i];
            if (cards[currCard.cardNum] == -1) { // no cards no match
                cards[currCard.cardNum] = i;
            } else { // card already there, we found a match
                removedCards.push(currCard);
                indexesToPurge.push(cards[currCard.cardNum]); // push previous match index
                indexesToPurge.push(i); // push current index
                cards[currCard.cardNum] = -1; // reset index
            }
        }
        indexesToPurge.sort();
        for (var j = indexesToPurge.length - 1; j >= 0; j--) {
            this.removeIndex(indexesToPurge[j]); // iterate backwards to avoid smudging indexes
        }
        return removedCards;
    }
}

class Card {
    constructor(cardNum) {
        this.cardNum = cardNum;
        this.selected = false;
    }

    getName() {
        return nameMap[this.cardNum];
    }

    equals(other) {
        return this.cardNum == other.cardNum;
    }

    draw(x, y, deg, flipped, mouseOver) {

        if (mouseOver && mouseDown) {
            selectedCard = this;
        }
        this.selected = (selectedCard == this);

        if (mouseOver || this.selected) {
            y -= 60;
        }

        ctx.translate(x, y); // translate
        ctx.rotate(deg * Math.PI / 180);

        if (flipped) {
            ctx.fillStyle = "white";
            ctx.fillRect(-cardW / 2, -cardH / 2, cardW, cardH);

            ctx.beginPath(); //diamond
            ctx.moveTo(0, -cardH / 2);
            ctx.lineTo(-cardW / 2, 0);
            ctx.lineTo(0, cardH / 2);
            ctx.lineTo(cardW / 2, 0);
            ctx.closePath();
            ctx.fillStyle = "#3464c9";
            ctx.fill();

            ctx.fillStyle = "#b7d2f7"; // oval in middle
            ctx.beginPath();
            ctx.ellipse(0, 0, 30, 40, 0, 0, 2 * Math.PI);
            ctx.fill();

            ctx.strokeStyle = "#5da4f0";
            ctx.lineWidth = 3;
            ctx.strokeRect(- cardW / 2, - cardH / 2, cardW, cardH); // outer card stroke
        } else {
            ctx.strokeStyle = "#5da4f0";
            ctx.lineWidth = 3;
            ctx.strokeRect(- cardW / 2, - cardH / 2, cardW, cardH);

            ctx.fillStyle = colorMap[this.cardNum];
            ctx.fillRect(- cardW / 2, - cardH / 2, cardW, cardH); // main rectangle
            ctx.fillStyle = "black";
            ctx.font = "15px Arial";
            ctx.textAlign = "center";
            ctx.fillText(this.getName().capitalize(), 0, 25 - cardH / 2);
            ctx.font = "70px Arial";
            ctx.fillText(this.cardNum, 0, 100 - cardH / 2);
        }

        ctx.rotate(-deg * Math.PI / 180); // untranslate
        ctx.translate(-x, -y);
    }

    toString() {
        return this.getName() + ", " + this.cardNum;
    }
}

Card.prototype.toString = function () {
    return this.getName() + ":" + this.cardNum;
};

Hand.prototype.toString = function () {
    return this.hand.toString();
};

Array.prototype.popRandomElement = function () {
    return this.splice(Math.floor(Math.random() * this.length), 1)[0];
};

String.prototype.capitalize = function () {
    return this.charAt(0).toUpperCase() + this.slice(1);
};

function drawCardDeck() {
    return new Card(Math.floor(Math.random() * nameMap.length));
}

function randomElement(arr) {
    return arr[Math.floor(Math.random() * arr.length)];
}

// -- Actual Game Code -- //

window.onload = function () {    // initialization
    canvas = document.getElementById("canvas");
    ctx = canvas.getContext("2d");
    clientRect = canvas.getBoundingClientRect();

    document.addEventListener('mousemove', e => {
        clientRect = canvas.getBoundingClientRect();
        mouseX = e.clientX - clientRect.left;
        mouseY = e.clientY - clientRect.top;
    });

    document.addEventListener('mousedown', e => {
        mouseDown = true;
    });
    document.addEventListener('mouseup', e => {
        mouseDown = false;
    });

    players[0] = new HumanPlayer("GOD");
    draw();

    for (var i = 0; i < numPlayers; i++) {
        if (i != 0) {
            players[i] = new RandomPlayer();
            //document.getElementById("r" + i).innerHTML = players[i].name;
        }
        for (var j = 0; j < initalCards; j++) {
            players[i].drawCard();
        }
        players[i].removeMatches();
    }
    console.log(players[0].hand.toString());
};

function draw() {
    ctx.fillStyle = "lightblue";
    ctx.fillRect(0, 0, canvas.width, canvas.height);

    for (var i = 0; i < players.length; i++) {
        players[i].draw(i);
    }

    window.requestAnimationFrame(draw);
    if (mouseDown) { // ensure mouseDown is true for only one frame
        mouseDown = false;
    }
}

function stepTurns() {
    console.clear();
    for (var i = 1; i < numPlayers; i++) {
        if (players[i].takeTurn()) {
            console.log(players[i].name + " wins!");
            break;
        }
    }
}

function HPT() {
    console.clear();
    var playerNum;
    var cardNum = document.getElementById("cardval").value;

    for (var i = 1; i < numPlayers; i++) {
        if (document.getElementById("c" + i).checked) {
            playerNum = i;
        }
    }

    var won = players[0].takeTurn(players[playerNum], new Card(cardNum));
    for (i = 1; i < numPlayers; i++) {
        if (won || players[i].takeTurn()) {
            console.log(players[i].name + " wins!");
            break;
        }
    }
    console.log(players[0].hand.toString());
}