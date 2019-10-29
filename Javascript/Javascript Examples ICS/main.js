function consoleTest() {
    //console.log("Hello World!");
    var x = 6;
    x++;
    console.log(x);

    x = 'text';
    //console.log(x);


    var arr = [];
    console.log(arr);

    arr[6] = 'test';
    console.log(arr);
    arr[8] = 3;
    //console.log(arr);

    arr[9] = [];
    //console.log(arr);

    var obj = {};
    obj.color = 'red';
    obj.height = 80;

    arr[10] = obj;
    console.log(obj);
    console.log(arr);

    obj['shoesize'] = 'small';
    obj[10] = 'why?';
    obj[11] = 'why not?';

    obj.func1 = function () { alert('I am func 1'); };

    console.log(obj);
    obj.func1();

    obj['myQueen'] = Queen(7, 8);
    console.log(obj);

    iterator(arr);

}

function iterator(coll) {
    for (var val of coll) {
        if (val !== undefined) {
            console.log('key:' + val + ' value:' + coll[val]);
        }
    }
}

function Queen(row, col) {
    var queen = {};

    this.row = row;
    this.col = col;

    return queen;
}



















