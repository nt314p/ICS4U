const nameMap = ["shark", "starfish", "whale", "turtle", "octopus", "jellyfish", "crab", "pufferfish", "tuna", "squid", "eel", "seahorse"];
console.log(nameMap.length);












function Hand() {
    this.hand = [];

    this.addCard = function(card) {
        hand.push(card);
    };

    this.hasCard = function(card) {
        return hand.indexOf(card) != -1;
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