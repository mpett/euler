import fs = require('fs');

function triangularNumbers(n:number): number[] {
    var listOfTriangularNumbers:number[] = new Array(n);
    for (var i:number = 1; i <= n; i++) {
        var triangularNumber:number = 0;
        for (var j:number = 1; j <= i; j++)
            triangularNumber += j;
        listOfTriangularNumbers[i - 1] = triangularNumber;
    }
    return listOfTriangularNumbers;
}

function summationOfPrimes(): number {
    var primeList:number[] = sieveOfErathostenes(2500000);
    var sum:number = primeList
                       .filter(function(x) { if (x <= 2000000) return x; } )
                          .reduce(function(x,y) { return x + y; } );
    return sum;
}

function pythagoreanTriplet():number {
    var failure:number = -1;
    for (var a:number = 1; a < 1000; a++) {
        for (var b:number = 1; b < 1000; b++) {
            for (var c:number = 1; c < 1000; c++) {
                if (a + b + c == 1000) {
                    if ( (a*a) + (b*b) == (c*c) ) {
                        var specialTriplet:number = a * b * c;
                        return specialTriplet;
                    }
                }
            }
        }
    }
    return failure;
}

function largestSeriesInProduct(adj: number):number {
    var inputString:string = 
        fs.readFileSync('problem_8_input.txt','utf8');
    inputString = inputString.replace(/\D/g,'');
    var maxAdjacentSum:number = -1;
    for (var i = 0; i < inputString.length - adj; i++) {
        var adjacentNumbers:number[] = new Array(adj);
        for (var j = 0; j < adj; j++)
            adjacentNumbers.push(
                parseInt(inputString.charAt(i + j)));
        var tmp:number = adjacentNumbers
                            .reduce(function(x,y) 
                                { return x * y; } );
        if (tmp > maxAdjacentSum)
            maxAdjacentSum = tmp;
    }
    return maxAdjacentSum;
}

function inputTest():void {
    var stdin = process.openStdin();
    stdin.addListener("data", function(d) {
        console.log("you entered: [" + d.toString().trim() + "]");
    });
    var lol:string = fs.readFileSync('foo.txt','utf8');
    console.log(lol);
}

function sieveOfErathostenes(n: number):number[] {
    var A:boolean[] = new Array(n);
    for (var i = 2; i < n; i++)
        A[i] = true;
    for (var i = 2; i < Math.sqrt(n); i++) {
        if (A[i]) {
            for (var j = i * i; j < n; j += i) {
                A[j] = false;
            }
        }
    }
    var B:number[] = new Array(n);
    B = A.map(function(x, i) { if (x) return i; } )
            .filter(function(x) { if (x) return x; } );
    return B;
}

function sumSquareDifference(n: number): number {
    var arrayOfNaturalNumbers:number[] = new Array(n);
    for (var num = 1; num <= n; num++)
        arrayOfNaturalNumbers[num - 1] = num;
    var sum:number = arrayOfNaturalNumbers
                        .reduce(function(x,y) { return x + y; });
    var squareOfSum:number = sum * sum;
    var sumOfSquares:number = arrayOfNaturalNumbers
                                .map(function(x) { return x * x; })
                                    .reduce(function(x,y) { return x + y; } );
    var sumSquareDifference:number = squareOfSum - sumOfSquares;
    return sumSquareDifference;
}

function fasterSmallestMultiple(k: number): number {
    var n:number = 1;
    var i:number = 0;
    var check:boolean = true;
    var limit:number = Math.sqrt(k);
    var a:number[] = new Array(k);
    var p:number[] = new Array(k);
    p = sieveOfErathostenes(k);
    
    for (var num = 0; num < k; num++)
        a[num] = 0;

    while (p[i] <= k) {
        a[i] = 1;
        if (check) {
            if (p[i] <= limit)
                a[i] = Math.floor(
                    Math.log(k) / Math.log(p[i]));
            else
                check = false;
        }
        n = n * Math.pow(p[i], a[i]);
        i = i+1;
    }

    return n;
}

function smallestMultiple(n: number): number {
    var upperLimit:number = n;
    var returnValue:number = -1;
    var num:number = 0;

    while (true) {
        num++;
        var sumOfRemainders:number = 0;
        for (var divisor = 1; divisor <= upperLimit; divisor++)
            sumOfRemainders += (num % divisor);
        if (sumOfRemainders == 0) {
            returnValue = num;
            break;
        }
    }
    return returnValue;
}

function large_palindromic_number(): number {
    var i:number = 0;
    var j:number = 0;
    var palindromes:number[] = new Array();
    for (i = 1; i < 1000; i++) {
        for(j = 1; j < 1000; j++) {
            var product_num = i * j;
            var product_string = String(product_num);
            var reversed_product_string = reverse_string(product_string);
            if (reversed_product_string === product_string) {
                var palindromic_number:number = parseInt(product_string);
                palindromes.push(palindromic_number);
            }
        }
    }
    var largest_palindromic_product:number = Math.max.apply(Math, palindromes);
    return largest_palindromic_product;
}

function reverse_string(str: string): string {
    var split_string = str.split("");
    var reverse_array = split_string.reverse();
    var join_array = reverse_array.join("");
    return join_array;
}

function prime_factors(n: number): number[] {
    var prime_factors:number[] = new Array();
    while (n % 2 == 0) {
        prime_factors.push(2);
        n /= 2;
    }
    var num:number = 0;
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
                
function fib(n: number): number {
    var f:number[] = new Array(n);
    f[0] = 0;
    f[1] = 1;
    var num:number = 0;
    for (num = 2; num < n; num++)
        f[num] = f[num - 1] + f[num - 2];
    var sum:number = f.filter(function(x) {return x % 2 == 0})
                        .filter(function(x) {return x <= 4000000})
                            .reduce(function(x,y) {return x + y});

    return sum;
}

function multiples(x: number): number {
    var num:number = 0;
    var sum:number = 0;
    for (num = 1; num < x; num++) {
        if (num % 3 == 0) {
            sum += num;
        } else if (num % 5 == 0) {
            sum += num;
        }
    }
    return sum;
}

function add(x: number, y: number): number {
    return x + y;
}

console.log(add(2,2)); // 4
console.log(multiples(10));
console.log(multiples(1000));
console.log(fib(400));

console.log(prime_factors(100));
console.log(prime_factors(13195));
console.log(prime_factors(600851475143));
console.log(large_palindromic_number());

console.log(smallestMultiple(10));
console.log(fasterSmallestMultiple(10));
console.log(fasterSmallestMultiple(20));
console.log(sumSquareDifference(10));
console.log(sumSquareDifference(100));
console.log(sieveOfErathostenes(16516511)[10000].toString());

console.log(largestSeriesInProduct(4));
console.log(largestSeriesInProduct(13));
console.log(pythagoreanTriplet());
console.log(summationOfPrimes());
console.log(triangularNumbers(10));