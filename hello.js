"use strict";
exports.__esModule = true;
var fs = require("fs");
function summationOfPrimes() {
    var primeList = sieveOfErathostenes(2500000);
    var sum = primeList
        .filter(function (x) { if (x <= 2000000)
        return x; })
        .reduce(function (x, y) { return x + y; });
    return sum;
}
function pythagoreanTriplet() {
    var failure = -1;
    for (var a = 1; a < 1000; a++) {
        for (var b = 1; b < 1000; b++) {
            for (var c = 1; c < 1000; c++) {
                if (a + b + c == 1000) {
                    if ((a * a) + (b * b) == (c * c)) {
                        var specialTriplet = a * b * c;
                        return specialTriplet;
                    }
                }
            }
        }
    }
    return failure;
}
function largestSeriesInProduct(adj) {
    var inputString = fs.readFileSync('problem_8_input.txt', 'utf8');
    inputString = inputString.replace(/\D/g, '');
    var maxAdjacentSum = -1;
    for (var i = 0; i < inputString.length - adj; i++) {
        var adjacentNumbers = new Array(adj);
        for (var j = 0; j < adj; j++)
            adjacentNumbers.push(parseInt(inputString.charAt(i + j)));
        var tmp = adjacentNumbers
            .reduce(function (x, y) { return x * y; });
        if (tmp > maxAdjacentSum)
            maxAdjacentSum = tmp;
    }
    return maxAdjacentSum;
}
function inputTest() {
    var stdin = process.openStdin();
    stdin.addListener("data", function (d) {
        console.log("you entered: [" + d.toString().trim() + "]");
    });
    var lol = fs.readFileSync('foo.txt', 'utf8');
    console.log(lol);
}
function sieveOfErathostenes(n) {
    var A = new Array(n);
    for (var i = 2; i < n; i++)
        A[i] = true;
    for (var i = 2; i < Math.sqrt(n); i++) {
        if (A[i]) {
            for (var j = i * i; j < n; j += i) {
                A[j] = false;
            }
        }
    }
    var B = new Array(n);
    B = A.map(function (x, i) { if (x)
        return i; })
        .filter(function (x) { if (x)
        return x; });
    return B;
}
function sumSquareDifference(n) {
    var arrayOfNaturalNumbers = new Array(n);
    for (var num = 1; num <= n; num++)
        arrayOfNaturalNumbers[num - 1] = num;
    var sum = arrayOfNaturalNumbers
        .reduce(function (x, y) { return x + y; });
    var squareOfSum = sum * sum;
    var sumOfSquares = arrayOfNaturalNumbers
        .map(function (x) { return x * x; })
        .reduce(function (x, y) { return x + y; });
    var sumSquareDifference = squareOfSum - sumOfSquares;
    return sumSquareDifference;
}
function smallestMultiple(n) {
    var upperLimit = n;
    var returnValue = -1;
    var num = 0;
    while (true) {
        num++;
        var sumOfRemainders = 0;
        for (var divisor = 1; divisor <= upperLimit; divisor++)
            sumOfRemainders += (num % divisor);
        if (sumOfRemainders == 0) {
            returnValue = num;
            break;
        }
    }
    return returnValue;
}
function large_palindromic_number() {
    var i = 0;
    var j = 0;
    var palindromes = new Array();
    for (i = 1; i < 1000; i++) {
        for (j = 1; j < 1000; j++) {
            var product_num = i * j;
            var product_string = String(product_num);
            var reversed_product_string = reverse_string(product_string);
            if (reversed_product_string === product_string) {
                var palindromic_number = parseInt(product_string);
                palindromes.push(palindromic_number);
            }
        }
    }
    var largest_palindromic_product = Math.max.apply(Math, palindromes);
    return largest_palindromic_product;
}
function reverse_string(str) {
    var split_string = str.split("");
    var reverse_array = split_string.reverse();
    var join_array = reverse_array.join("");
    return join_array;
}
function prime_factors(n) {
    var prime_factors = new Array();
    while (n % 2 == 0) {
        prime_factors.push(2);
        n /= 2;
    }
    var num = 0;
    for (num = 3; num <= Math.sqrt(n); num += 2) {
        while (n % num == 0) {
            prime_factors.push(num);
            n /= num;
        }
    }
    if (n > 2)
        prime_factors.push(n);
    return prime_factors;
}
function fib(n) {
    var f = new Array(n);
    f[0] = 0;
    f[1] = 1;
    var num = 0;
    for (num = 2; num < n; num++)
        f[num] = f[num - 1] + f[num - 2];
    var sum = f.filter(function (x) { return x % 2 == 0; })
        .filter(function (x) { return x <= 4000000; })
        .reduce(function (x, y) { return x + y; });
    return sum;
}
function multiples(x) {
    var num = 0;
    var sum = 0;
    for (num = 1; num < x; num++) {
        if (num % 3 == 0) {
            sum += num;
        }
        else if (num % 5 == 0) {
            sum += num;
        }
    }
    return sum;
}
function add(x, y) {
    return x + y;
}
console.log(add(2, 2)); // 4
console.log(multiples(10));
console.log(multiples(1000));
console.log(fib(400));
console.log(prime_factors(100));
console.log(prime_factors(13195));
console.log(prime_factors(600851475143));
console.log(large_palindromic_number());
console.log(smallestMultiple(10));
//console.log(smallest_multiple(20)); TODO: implement faster version
console.log(sumSquareDifference(10));
console.log(sumSquareDifference(100));
console.log(sieveOfErathostenes(16516511)[10000].toString());
//inputTest();
console.log(largestSeriesInProduct(4));
console.log(largestSeriesInProduct(13));
console.log(pythagoreanTriplet());
console.log(summationOfPrimes());
