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

    drawHand(y) {
        for (var i = 0; i < this.hand.length; i++) {
            this.hand[i].draw(125 * i + 10, y);
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
        for (var i = 0; i < this.hand.length; i++) {
            var currCard = this.hand[i];
            if (cards[currCard.cardNum] == null) { // no cards no match
                cards[currCard.cardNum] = currCard;
            } else { // card already there, we found a match
                removedCards.push(currCard);
                cards[currCard.cardNum] = null;
            }
        }
        for (var j = 0; j < removedCards.length; j++) {
            this.removeAll(removedCards[j]);
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

    draw(x, y, flipped) {
        var cardH = 180;
        var cardW = 120;
        var skyH = 50;
        var oceanH = cardH - skyH;
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
            if (mouseX > x && mouseX < x + cardW && mouseY > y && mouseY < y + cardH) {
                ctx.fillStyle = "#f2ffff";
            } else {
                ctx.fillStyle = "white";
            }
            ctx.fillRect(x, y, cardW, cardH);
            ctx.fillStyle = "black";
            ctx.font = "20px Arial";
            ctx.textAlign = "center";
            ctx.fillText(this.getName().capitalize(), x + cardW / 2, y + 50);
            ctx.font = "100px Arial";
            ctx.fillText(this.cardNum, x + cardW / 2, y + 150);
        }
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
    //cq.draw(100, 100);
    //ca.draw(mouseX, mouseY, true);
    for (var i = 0; i < players.length; i++) {
        players[i].hand.drawHand(i * 190 + 10);
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





