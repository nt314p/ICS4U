function buttoned() {
    console.clear();
    var n = document.getElementById("in").value;
    initialize(n);
    // var s = new Date().getTime();
    // console.log(recursion(n));
    // console.log("Recursion took " + ((new Date().getTime()) - s));
    s = new Date().getTime();
    console.log(memorization(n));
    console.log("Memorization took " + ((new Date().getTime()) - s));
    s = new Date().getTime();
    console.log(dynamic(n));
    console.log("Dynamic took " + ((new Date().getTime()) - s));
}

var solutions = [];
function initialize(n) {
    for (var i = 0; i <= n; i++) {
        solutions[i] = -1;
    }
    solutions[1] = 1;
    solutions[2] = 1;
}

function memorization(n) {
    if (solutions[n] > 0) {
        return solutions[n];
    }
    solutions[n] = memorization(n - 1) + memorization(n - 2);
    return solutions[n];
}

function dynamic(n) {
    for (var i = 3; i <= n; i++) {
        solutions[i] = solutions[i - 1] + solutions[i - 2];
    }
    return solutions[n];
}

function recursion(n) {
    if (n == 1 || n == 2) {
        return 1;
    }
    return recursion(n - 1) + recursion(n - 2);
}