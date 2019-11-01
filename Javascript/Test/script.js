var myObj = new Test(1, 2);
console.log(myObj.x);
console.log(myObj.y);
console.log(myObj.sum);
console.log(myObj.getSum());
myObj.setSum(99);
console.log(myObj.getSum());
console.log(myObj);

myObj.sum = 100;
console.log(myObj.getSum());
console.log(myObj.sum);
console.log(myObj);

var myObj2 = new Test(10, 9);
console.log(myObj.getSum());
console.log(myObj);
console.log(myObj2.getSum());
console.log(myObj2);


function Test(x, y) {
	this.x = x;
	this.y = y;
    var sum = x + y;
    
	this.getSum = function() {
		return sum;
    }
    
    this.setSum = function(n) {
        sum = n;
    }
}