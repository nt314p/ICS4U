/* jshint esversion: 6 */

var nameMap = ["shark", "starfish", "whale", "turtle", "octopus", "jellyfish", "crab", "pufferfish", "tuna", "squid", "eel", "seahorse"];
var colorMap = ["#a5bac9", "#ffde91", "#306cba", "#9aba63", "#9c69cf", "#ed9adb", "#e66060", "#f7e883", "#babbd4", "#ff9cc2", "#ccba91", "#ffdf61"];
var names = ["Ethan", "Chris", "Christian", "Sonya", "Kaitlin", "Shree"];
var initalCards = 5;
var numPlayers = 6;
var winningPoints = 10;
var turn = 0;
var players = [];
var canvas, ctx;
var mouseX = 0;
var mouseY = 0;
var mouseDown = false;
var cardH = 112;
var cardW = 80;
var HPselectedCard = null;
var clientRect;

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

    drawHand(x, y, deg, flipped) {
        var totalX = 200;
        var offset, start;

        if (this.hand.length == 1) {
            start = 0;
            offset = totalX;
        } else {
            start = -totalX / 2;
            offset = totalX / (this.hand.length - 1);
        }

        var tW = Math.min(cardW / 2, offset / 2);

        ctx.translate(x, y);
        ctx.rotate(deg * Math.PI / 180);

        for (var i = 0; i < this.hand.length; i++) {
            var tX = i * offset + start;
            var xHover = (mouseX > x + tX - tW && mouseX < x + tX + tW);
            var yHover = (mouseY > y - cardH / 2 && mouseY < y + cardH / 2);
            this.hand[i].draw(tX, 0, 0, flipped, xHover && yHover);
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
        var skyH = 30;
        var oceanH = cardH - skyH;
        if (mouseDown && !mouseOver) {
            this.selected = false;
        } else if (mouseOver || this.selected) {
            if (mouseDown) {
                this.selected = true;
            }
            y -= 60;
        }

        //ctx.save();
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
            //ctx.fillRect(-cardW/2, -skyH/2, cardW, oceanH);

            grd = ctx.createLinearGradient(-cardW / 2, y, x, y + skyH); // sky
            grd.addColorStop(0, "#243775"); // blue
            grd.addColorStop(0.3, "#e0acfc"); // purple
            grd.addColorStop(1, "#fcd5ac"); // orange

            //ctx.fillStyle = grd;


            ctx.fillStyle = "white";
            ctx.fillRect(-cardW / 2, -cardH / 2, cardW, cardH);

            
            // ctx.fillStyle = "#aefcfa";
            // ctx.beginPath();
            // ctx.ellipse(0, 0, 30, 20, 0, 0, 2 * Math.PI);
            // ctx.fill();

            ctx.beginPath(); //diamond
            ctx.moveTo(0, -cardH/2);
            ctx.lineTo(-cardW / 2, 0);
            ctx.lineTo(0, cardH/2);
            ctx.lineTo(cardW / 2, 0);
            ctx.closePath();
            ctx.fillStyle = "#3464c9";
            ctx.fill();

            ctx.fillStyle = "#b7d2f7";
            ctx.beginPath();
            ctx.ellipse(0, 0, 30, 40, 0, 0, 2 * Math.PI);
            ctx.fill();

            ctx.strokeStyle = "#5da4f0";
            ctx.lineWidth = 3;
            ctx.strokeRect(- cardW / 2, - cardH / 2, cardW, cardH);
        } else {
            ctx.strokeStyle = "#5da4f0";
            ctx.lineWidth = 3;
            ctx.strokeRect(- cardW / 2, - cardH / 2, cardW, cardH);
            // if (mouseOver || this.selected) {
            //     ctx.fillStyle = "#f2ffff";
            // } else {
            //     ctx.fillStyle = "white";
            // }
            ctx.fillStyle = colorMap[this.cardNum];
            ctx.fillRect(- cardW / 2, - cardH / 2, cardW, cardH); // main rectangle
            ctx.fillStyle = "black";
            ctx.font = "15px Arial";
            ctx.textAlign = "center";
            ctx.fillText(this.getName().capitalize(), 0, 25 - cardH / 2);
            ctx.font = "70px Arial";
            ctx.fillText(this.cardNum, 0, 100 - cardH / 2);
        }

        ctx.rotate(-deg * Math.PI / 180);
        ctx.translate(-x, -y);
        //ctx.restore();
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
    clientRect = canvas.getBoundingClientRect();

    document.addEventListener('mousemove', e => {
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
        var dAng = 360 / players.length;
        var cAng = (i * dAng + 90) * Math.PI / 180;
        var r = 320;
        players[i].hand.drawHand(r * Math.cos(cAng) + 400, r * Math.sin(cAng) + 400, dAng * i, i != 0);
    }
    window.requestAnimationFrame(draw);
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





