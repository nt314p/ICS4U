var pressed = false;

function press() {
    pressed = true;
}

function main() {
    while (true) {
        pauseForInput();
        pressed = false;
        console.log("Got input!");
    }
}


function pauseForInput() {
    while (!pressed)
    return true;
}

function checkInput() {
    if (pressed) {
        return true;
    }
    return checkInput();
}