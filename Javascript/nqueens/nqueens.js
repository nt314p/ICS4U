window.onload = function load() {
    c = document.getElementById("maincanvas");
    button = document.getElementById("n");
    ctx = c.getContext("2d");
}

var c = document.getElementById("maincanvas");
var button = document.getElementById("n");
var ctx;


function solveQueens() {
    drawQueens(solve(button.value));
}

function solve(n) {
    queens = [];
    tempConflict = [];
    var filled = 0;

    queens.push(new Queen(0, 0));

    while (filled != n) {

        var conflict = false;

        var top = queens.pop();
        tempConflict.push(top);
        while (queens.length != 0 && !conflict) { // pop off
            tempConflict.push(queens.pop());
            if (tempConflict[tempConflict.length - 1].conflictsWith(top)) {
                conflict = true;
            }
        }
        while (tempConflict.length != 0) { // pop on
            queens.push(tempConflict.pop());
        }

        if (!conflict) {
            filled++;
            queens.push(new Queen(0, filled));
        } else if (conflict && queens[queens.length - 1].canShift(n)) {
            queens[queens.length - 1].incX();
        } else if (conflict) {
            while (queens.length != 0 && !queens[queens.length - 1].canShift(n)) {
                queens.pop();
                filled--;
            } // exits if stack is empty (no solution) or queen can be shifted
            if (queens.length == 0) {
                break;
            }
            queens[queens.length - 1].incX();
        }
    }


    if (queens.length == 0) {
        console.log("No solutions.");
    } else {
        queens.pop();
    }

    // while (queens.length != 0) {
    //     console.log(queens.pop().str(n));
    // }

    return queens;
}

function drawQueens(queens) {
    ctx.fillStyle = "white";
    ctx.fillRect(0, 0, c.width, c.height);
    console.log("drawing!");
    ctx.fillStyle = "red";
    for (var i = 0; i < queens.length; i++) {
        var curr = queens[i];
        var x = curr.x;
        var y = curr.y;
        ctx.fillRect(x*10, y*10, 10, 10);
    }
}

function Queen(x, y) {
    this.x = x;
    this.y = y;

    this.incX = function () {
        this.x = this.x + 1;
    }

    this.canShift = function (n) {
        return this.x + 1 < n;
    }

    this.conflictsWith = function (other) {
        return this.x == other.x || this.y == other.y || Math.abs((this.y - other.y) / ((this.x - other.x))) == 1;
    }

    this.str = function (n) {
        var ret = "";
        for (var i = 0; i < this.x; i++) {
            ret += " +";
        }
        ret += " Q";
        for (var i = this.x + 1; i < n; i++) {
            ret += " +";
        }
        return ret;
    }
    return this;
}