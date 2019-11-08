var nameMap = ["shark", "starfish", "whale", "turtle", "octopus", "jellyfish", "crab", "pufferfish", "tuna", "squid", "eel", "seahorse"];
console.log(nameMap.length);








function 

function Player() {
    var hand = new Hand();

    this.drawCard = function () {
        hand.addCard(drawCardDeck());
    };

    this.hasCard = function (card) {
        return hand.hasCard(card);
    };

    this.removeCard = function (card) {
        return hand.removeCard(card);
    };
}

function Hand() {
    this.hand = [];

    this.addCard = function(card) {
        hand.push(card);
    };

    this.hasCard = function(card) {
        return hand.indexOf(card) != -1;
    };

    this.removeCard = function(card) {
        return hand.splice(hand.indexOf(card), 1);
    };

    this.cardArr = function() {
        return hand.slice(); // shallow copy array
    };
}

function Card(cardNum) {
    this.cardNum = cardNum;

    this.getName = function() {
        return nameMap[cardNum];
    };

    this.equals = function(other) {
        return cardNum == other.cardNum;
    };
}

function drawCardDeck() {
    return new Card(Math.random() * nameMap.length);
}