/* jshint esversion: 6 */

var nameMap = ["shark", "starfish", "whale", "turtle", "octopus", "jellyfish", "crab", "pufferfish", "tuna", "squid", "eel", "seahorse"];
var names = ["Ethan", "Chris", "Christian", "Sonya", "Kaitlin", "Shree"];
var initalCards = 5;
var numPlayers = 4;
var winningPoints = 10;
var turn = 0;
var players = [];
var canvas, ctx;
var mouseX = 0;
var mouseY = 0;

class Player {
    constructor(name) {
        this.hand = new Hand();
        this.points = 0;
        this.name = name;
    }

    drawCard() {
        var card = drawCardDeck();
        this.hand.addCard(card);
        //if (this instanceof HumanPlayer) {
        console.log(this.name + " drew: " + card);
        //}
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

    checkForMatches() {
        return this.hand.checkForMatches();
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
        }
        this.ensureEnoughCards();
        return matches;
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
        var removedCards = this.removeMatches();

        this.points += removedCards.length;
        console.log(this.name + " has " + this.points + " point(s).");
        return this.points >= winningPoints;
    }
}

class BullyRandom extends Player {
    constructor() {
        var n = names.randomElement();
        for (var i = 0; i < names.length; i++) {
            if (n == names[i]) {
                names.splice(i, 1);
            }
        }
        super(n);
    }

    takeTurn() {
        return super.takeTurn(players[0], this.hand.hand.randomElement());
    }
}

class RandomPlayer extends Player {
    constructor() {
        var n = names.randomElement();
        for (var i = 0; i < names.length; i++) {
            if (n == names[i]) {
                names.splice(i, 1);
            }
        }
        super(n);
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

    drawHand(x, y) {
        var dAng;
        var overallAng = 40;
        if (this.hand.length != 1) {
            dAng = overallAng / (this.hand.length - 1);
        } else {
            dAng = 0;
        }
        for (var i = 0; i < this.hand.length; i++) {

            var currAng = -overallAng / 2 + i * dAng;
            this.hand[i].draw(80 * i + x, y - (Math.cos(currAng * Math.PI / 180) - 1) * 400, currAng);
        }
    }

    checkForMatches() { // TODO use code from removeMatches to update code
        for (var i = 0; i < this.hand.length - 1; i++) {
            for (var j = i + 1; j < this.hand.length; j++) {
                if (this.hand[i].equals(this.hand[j])) {
                    return hand[i];
                }
            }
        }
        return null; // THIS SHOULD RETURN ARRAY OF ALL MATCHED CARDS
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
        for (var j = indexesToPurge.length -1 ; j >= 0; j--) { 
            this.removeIndex(indexesToPurge[j]); // iterate backwards to avoid smudging indexes
        }
        return removedCards;
    }
}

class Card {
    constructor(cardNum) {
        this.cardNum = cardNum;
    }

    getName() {
        return nameMap[this.cardNum];
    }

    equals(other) {
        return this.cardNum == other.cardNum;
    }

    draw(x, y, deg, flipped) {
        var cardH = 180;
        var cardW = 120;
        var skyH = 50;
        var oceanH = cardH - skyH;
        ctx.save();
        ctx.translate(x, y);
        ctx.rotate(deg * Math.PI / 180);

        // ctx.globalAlpha = 0.5; // its shadow but work on gameplay boi
        // ctx.fillStyle = "black";
        // ctx.fillRect(- 4 - cardW / 2, - 4 - cardH / 2, cardW + 8, cardH + 8);
        ctx.globalAlpha = 1.0;
        if (flipped) {
            var grd = ctx.createLinearGradient(x, y + skyH, x, y + cardH); // ocean
            grd.addColorStop(0, "#c7ebff"); // light blue
            grd.addColorStop(1, "#00578c"); // dark blue
            ctx.fillStyle = grd;
            ctx.fillRect(x, y + skyH, cardW, oceanH);

            grd = ctx.createLinearGradient(x, y, x, y + skyH); // sky
            grd.addColorStop(0, "#243775"); // blue
            grd.addColorStop(0.3, "#e0acfc"); // purple
            grd.addColorStop(1, "#fcd5ac"); // orange

            ctx.fillStyle = grd;
            ctx.fillRect(x, y, cardW, skyH);

        } else {
            ctx.strokeStyle = "#5da4f0";
            ctx.lineWidth = 3;
            ctx.strokeRect(- cardW / 2, - cardH / 2, cardW, cardH);
            if (mouseX > x && mouseX < x + cardW && mouseY > y && mouseY < y + cardH) {
                ctx.fillStyle = "#f2ffff";
            } else {
                ctx.fillStyle = "white";
            }
            ctx.fillRect(- cardW / 2, - cardH / 2, cardW, cardH);
            ctx.fillStyle = "black";
            ctx.font = "20px Arial";
            ctx.textAlign = "center";
            ctx.fillText(this.getName().capitalize(), 0, 50 - cardH / 2);
            ctx.font = "100px Arial";
            ctx.fillText(this.cardNum, 0, 150 - cardH / 2);
        }
        //ctx.rotate(-deg * Math.PI / 180);
        //ctx.translate(-offset.x, -offset.y);
        // ctx.rect(-cardW / 2, -cardH / 2, cardW, cardH);
        ctx.restore();
    }

    toString() {
        return this.getName() + ", " + this.cardNum;
    }
}

class Sprite {
    constructor(x, y) {
        this.x = x;
        this.y = y;
        this.draw = function () { };
    }
}

Card.prototype.toString = function () {
    return this.getName() + ":" + this.cardNum;
};

Hand.prototype.toString = function () {
    return this.hand.toString();
};

Array.prototype.randomElement = function () {
    return this[Math.floor(Math.random() * this.length)];
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

    document.addEventListener('mousemove', e => {
        var rect = canvas.getBoundingClientRect();
        mouseX = e.clientX - rect.left;
        mouseY = e.clientY - rect.top;
    });

    players[0] = new HumanPlayer("GOD");
    draw();

    for (var i = 0; i < numPlayers; i++) {
        if (i != 0) {
            players[i] = new RandomPlayer();
            document.getElementById("r" + i).innerHTML = players[i].name;
        }
        for (var j = 0; j < initalCards; j++) {
            players[i].drawCard();
        }
        players[i].removeMatches();
    }
    console.log(players[0].hand.toString());
};
var cq = new Card(11);
var ca = new Card(2);

function draw() {
    window.requestAnimationFrame(draw);
    ctx.fillStyle = "lightblue";
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    var h = mouseX;
    var k = mouseY;
    // cq.draw(h, k);
    // ctx.fillStyle = "black";
    // ctx.beginPath();
    // ctx.ellipse(h, k, 50, 50, Math.PI / 4, 0, 2 * Math.PI);
    // ctx.stroke();
    // ca.draw(mouseX, mouseY, true);
    for (var i = 0; i < players.length; i++) {
        players[i].hand.drawHand(100, i * 200 + 100);
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





