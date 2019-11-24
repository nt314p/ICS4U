/* jshint esversion: 8 */

var nameMap = ["shark", "starfish", "whale", "turtle", "octopus", "jellyfish", "crab", "pufferfish", "tuna", "squid", "eel", "seahorse"];
var colorMap = ["#a5bac9", "#ffde91", "#306cba", "#9aba63", "#9c69cf", "#ed9adb", "#e66060", "#f7e883", "#babbd4", "#ff9cc2", "#ccba91", "#ffdf61"];
var names = ["Ethan", "Chris", "Christian", "Sonya", "Kaitlin", "Shree", "Karol", "Alex"];
var playerColors = ["red", "orange", "#fcf11b", "green", "blue", "indigo", "purple", "pink"];
var initalCards = 5;
var numPlayers = 6;
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
var msgX = 400;
var msgY = 340;
var msgW = 200;
var msgH = 120;
var selectedCard = null;
var selectedPlayer = null;
var hoverPlayer = null;
var clientRect;

class Button {
    constructor(x, y, w, h, text, color, func) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.text = text;
        this.color = color;
        this.func = func;
        this.down = false;
    }

    draw() {
        var x = this.x;
        var y = this.y;
        var w = this.w;
        var h = this.h;
        ctx.fillStyle = this.color;
        ctx.fillRect(x - w / 2, y - h / 2, w, h);
        ctx.fillStyle = "white";
        ctx.font = "25px Arial";
        ctx.textAlign = "center";
        ctx.textBaseline = "middle";
        ctx.fillText(this.text, x, y);
        if (mouseX <= x + w / 2 && mouseX >= x - w / 2 && mouseY >= y - h / 2 && mouseY <= y + h / 2) {
            ctx.strokeStyle = "white";
            ctx.beginPath();
            ctx.rect(x - w / 2, y - h / 2, w, h);
            ctx.stroke();
        }
    }
}

class Player {
    constructor(name, color) {
        this.hand = new Hand();
        this.points = 0;
        this.name = name;
        this.color = color;
        this.selected = false;
        this.message = null;
        this.cX = 0;
        this.cy = 0;
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

    async takeTurn(player, card) {
        await this.say("Hey " + player.name + ", do you have a " + card.getName() + "?", 2000);
        if (player.hasCard(card)) { // get card
            await player.say("Yes I do...", 2000);
            this.addCard(player.removeCard(card));
            player.ensureEnoughCards();
        } else { // Go Fish!
            await player.say("Sorry, go fish!", 2000);
            this.drawCard();
        }
        this.removeMatches();

        console.log(this.name + " has " + this.points + " point(s).");
        return this.points >= winningPoints;
    }

    async say(message, ms) {
        this.message = message;
        let d = await delay(ms);
        this.message = null;
    }

    draw(i) {
        if (this.message != null) {
            this.drawMsg(this.message);
        }

        var dAng = 360 / players.length;
        var deg = (i * dAng + 90) * Math.PI / 180;
        var hidden = (i != 0);
        this.cX = playerIconR * Math.cos(deg) + canvas.width / 2;
        this.cY = playerIconR * Math.sin(deg) + canvas.width / 2;
        var mouseOver = (900 >= Math.pow(this.cX - mouseX, 2) + Math.pow(this.cY - mouseY, 2));

        if (mouseOver && mouseDown) {
            if (selectedPlayer == this) {
                selectedPlayer = null;
            } else {
                selectedPlayer = this;
            }
        }
        this.selected = (selectedPlayer == this);

        if (mouseOver) {
            hoverPlayer = this;
        } else if (hoverPlayer == this) {
            hoverPlayer = null;
        }

        if (hidden && ((this.selected && (hoverPlayer == this || hoverPlayer == null)) || mouseOver)) {

            ctx.strokeStyle = "white";
            ctx.beginPath(); // player's circle icon
            ctx.ellipse(this.cX, this.cY, 31, 31, 0, 0, 2 * Math.PI);
            ctx.stroke();

            // ctx.beginPath(); // line pointing to player
            // ctx.moveTo(msgX, msgY);
            // ctx.lineTo(cX, cY);
            // ctx.stroke();
            // ctx.fillStyle = "white";
            // ctx.fillRect(msgX - msgW / 2, msgY - msgH / 2, msgW, msgH); // message box
            // ctx.strokeStyle = "#5da4f0";
            // ctx.strokeRect(msgX - msgW / 2, msgY - msgH / 2, msgW, msgH); // outer card stroke
            // ctx.fillStyle = "#5da4f0";
            // ctx.font = "30px Arial";
            // ctx.fillText(this.name, msgX, 300);
            // ctx.font = "20px Arial";
            this.drawMsg("Points: " + this.points);
        }

        ctx.fillStyle = this.color;
        ctx.beginPath();
        ctx.ellipse(this.cX, this.cY, 30, 30, 0, 0, 2 * Math.PI);
        ctx.fill();

        this.hand.drawHand(playerHandR * Math.cos(deg) + 400, playerHandR * Math.sin(deg) + 400, deg * 180 / Math.PI - 90, hidden);
    }

    drawMsg(text) {
        ctx.strokeStyle = "white";
        ctx.beginPath(); // line pointing to player
        ctx.moveTo(msgX, msgY);
        ctx.lineTo(this.cX, this.cY);
        ctx.stroke();
        ctx.fillStyle = "white";
        ctx.fillRect(msgX - msgW / 2, msgY - msgH / 2, msgW, msgH); // message box
        ctx.strokeStyle = "#5da4f0";
        ctx.strokeRect(msgX - msgW / 2, msgY - msgH / 2, msgW, msgH); // outer card stroke
        ctx.fillStyle = "#5da4f0";
        ctx.font = "30px Arial";
        ctx.fillText(this.name, msgX, 300);
        ctx.font = "20px Arial";
        wrapText(text, msgX, msgY, 30, msgW - 20);
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
            if (selectedCard == this) {
                selectedCard = null;
            } else {
                selectedCard = this;
            }
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
            ctx.fillStyle = "white";
            ctx.font = "15px Arial";
            ctx.textAlign = "center";
            ctx.fillText(this.getName().capitalize(), 0, 20 - cardH / 2);
            ctx.font = "70px Arial";
            ctx.fillText(this.cardNum, 0, 70 - cardH / 2);
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

function sleep(ms) {
    var initial = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - initial) > ms) {
            break;
        }
    }
}

// from https://www.html5canvastutorials.com/tutorials/html5-canvas-wrap-text-tutorial/
function wrapText(text, x, y, lineHeight, maxWidth) {
    var words = text.split(' ');
    var line = '';
    for (var n = 0; n < words.length; n++) {
        var testLine = line + words[n] + ' ';
        var metrics = ctx.measureText(testLine);
        var testWidth = metrics.width;
        if (testWidth > maxWidth && n > 0) {
            ctx.fillText(line, x, y);
            line = words[n] + ' '; line = words[n] + ' ';
            y += lineHeight;
        }
        else {
            line = testLine;
        }
    }
    ctx.fillText(line, x, y);
}

// from https://stackoverflow.com/questions/17883692
async function delay(delayInms) {
    return new Promise(resolve => {
        setTimeout(() => {
            resolve(2);
        }, delayInms);
    });
}


window.onload = function () {    // initialization -----
    canvas = document.getElementById("canvas");
    ctx = canvas.getContext("2d");
    clientRect = canvas.getBoundingClientRect();
    ctx.textBaseline = "middle";

    document.addEventListener('mousemove', e => {
        clientRect = canvas.getBoundingClientRect();
        mouseX = e.clientX - clientRect.left;
        mouseY = e.clientY - clientRect.top;
    });

    document.addEventListener('mousedown', e => {
        mouseDown = true;
    });
    // document.addEventListener('mouseup', e => {
    //     mouseDown = false;
    // });

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

var turnButton = new Button(400, 500, 140, 80, "Take Turn", "#3464c9", 1);

function draw() {
    ctx.fillStyle = "lightblue";
    ctx.fillRect(0, 0, canvas.width, canvas.height);

    for (var i = 0; i < players.length; i++) {
        players[i].draw(i);
    }
    if (selectedCard != null && selectedPlayer != null) {
        turnButton.draw();
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

async function HPT() {
    console.clear();
    var playerNum;
    var cardNum = document.getElementById("cardval").value;

    for (var i = 1; i < numPlayers; i++) {
        if (document.getElementById("c" + i).checked) {
            playerNum = i;
        }
    }

    var won = await players[0].takeTurn(players[playerNum], new Card(cardNum));
    for (i = 1; i < numPlayers; i++) {
        await players[i].takeTurn();
        if (won) { // fix player winning
            console.log(players[i].name + " wins!");
            break;
        }
        //let d = await delay(1000);
    }
    console.log(players[0].hand.toString());
}