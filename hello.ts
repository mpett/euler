import fs = require('fs');
import bigInt = require('big-integer');

function largestGridProduct():number {
    var inputString:string = fs.readFileSync("number_grid.txt", "utf8");
    console.log(inputString);
    var lineArray:string[] = inputString.split("\n");
    var i:number = 0;
    var numberGrid:any[] = new Array();
    
    lineArray.forEach(function(element) {
        var numberArray:string[] = element.split(" ");
        var j:number = 0;
        numberArray.forEach(function(num) {
            var parsedNum:number = parseInt(num);
            if (!numberGrid[i]) {
                numberGrid[i] = [];
            }
            numberGrid[i][j] = parsedNum;
            j++;
        });
        i++;
    });
    
    numberGrid.pop();
    var sum:number = 0;
    var n:number = numberGrid.length;
    var products:number[] = new Array();

    for (var i:number = 0; i < n; i++) {
        for (var j:number = 0; j < n; j++) {
            if (j + 3 < n) {
                var product:number = numberGrid[i][j] 
                * numberGrid[i][j+1] * numberGrid[i][j+2] 
                * numberGrid[i][j+3];
                products.push(product);
            }
            if (i + 3 < n) {
                var product:number = numberGrid[i][j] 
                * numberGrid[i+1][j] * numberGrid[i+2][j] 
                * numberGrid[i+3][j];
                sum += product;
                products.push(product);
            }            
            if (i + 3 < n && j + 3 < n) {
                var product:number = numberGrid[i][j] 
                * numberGrid[i+1][j+1] * numberGrid[i+2][j+2] 
                * numberGrid[i+3][j+3];
                sum += product;
                products.push(product);
            }        
            if (i + 3 < n && j - 3 >= 0) {
                var product:number = numberGrid[i][j] 
                * numberGrid[i+1][j-1] * numberGrid[i+2][j-2] 
                * numberGrid[i+3][j-3];
                sum += product;
                products.push(product);
            }            
        }
    }

    var greatestProduct:number = Math.max.apply(null, products);
    return greatestProduct;
}

function factorialDigitSum(n:number):number {
    var bn = bigInt(n);
    for (var i:number = 1; i <= n; i++) {
        var bi = bigInt(i);
        bn = bn.multiply(bi);
    }
    var factorialString:string = bn.toString();
    var digitSum:number = 0;
    for (var i:number = 0; i < factorialString.length; i++) {
        digitSum += parseInt(factorialString.charAt(i));
    }
    return digitSum;
}

function powerDigitSum(n:number):number {
    var powerString:string = bigInt(2).pow(n).toString();
    console.log(powerString);
    var digitSum:number = 0;
    for (var i:number = 0; i < powerString.length; i++) {
        var digit:number = parseInt(powerString.charAt(i));
        digitSum += digit;
    }
    return digitSum;
}

function longestCollatzSequence(n:number):number {
    var max:number = 0;
    var maxStartNumber:number = n;

    for (var i:number = 2; i < n; i++) {
        var length:number = collatzSequence(i).length;
        if (length > max) {
            max = length;
            maxStartNumber = i;
        }
    }

    return maxStartNumber;
}

function collatzSequence(n:number):number[] {
    var collatzSequence:number[] = new Array();
    collatzSequence.push(n);
    while (true) {
        if (n == 1) {
            break;
        } else if (n % 2 == 0) {
            n = n / 2;
            collatzSequence.push(n);
        } else {
            n = 3 * n + 1;
            collatzSequence.push(n);
        }
    }
    return collatzSequence;
}

function largeSum():string {
    var inputString:string = fs.readFileSync("problem_11_input.txt", "utf8");
    var lineArray:string[] = inputString.split("\n");
    var finalSum:number = 0;
    lineArray.forEach(function(element) {
        var numberFromLine:number = parseInt(element);
        if (isNaN(numberFromLine))
            return;
        finalSum += numberFromLine;
    });
    var finalSumString:string = finalSum.toString();
    return finalSumString;
}

function fasterTriangNumber(p:number, k:number): number {
    var t:number = 1;
    var a:number = 1;
    var cnt:number = 0;
    var tt:number = 0;
    var i:number = 0;
    var exponent:number = 0;

    var primeArray:number[] = sieveOfErathostenes(p);

    while (cnt <= k) {
        cnt = 1;
        a = a + 1;
        t = t + a;
        tt = t;
        for (i = 0; i < p; i++) {
            if (primeArray[i] * primeArray[i] > tt) {
                cnt = 2 * cnt;
                break;
            } 
            exponent = 1;
            while (tt % primeArray[i] == 0) {
                exponent++;
                tt = tt / primeArray[i];
            }
            if (exponent > 1)
                cnt = cnt * exponent;
            if (tt == 1)
                break;
        }
    }

    return t;
}

function firstTriangularNumberWithOverKDivisors(n:number, k:number): number {
    var triangulars:number[] = triangularNumbers(n);
    var returnValue:number = -1;
    var maxDivisors:number = 0;
    for (var i:number = 0; i < n; i++) {
        var triangularNumber:number = triangulars[i];
        var numDivisors:number 
            = numberOfDivisors(triangularNumber);
        var tmp:number = numDivisors;
        if (tmp > maxDivisors) {
            maxDivisors = tmp;
            console.log(maxDivisors);
        }
        if (numDivisors > k) {
            returnValue 
                = triangularNumber;
            break;
        }
    }
    return returnValue;
}

function numberOfDivisors(n:number): number {
    var listOfFactors:number[] = factors(n);
    var numberOfDivisors:number = listOfFactors.length;
    return numberOfDivisors;
}

function factors(n:number): number[] {
    var factors:number[] = new Array();
    for (var i:number = 1; i <= (n/2); i++) {
        if (n % i == 0)
            factors.push(i);
    }
    factors.push(n);
    return factors;
}

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
console.log(factors(28));
console.log(firstTriangularNumberWithOverKDivisors(100,5));
//console.log(firstTriangularNumberWithOverKDivisors(100000,500));
console.log(fasterTriangNumber(100,5));
console.log(fasterTriangNumber(2000, 500));
console.log(fasterTriangNumber(20000, 1000));

console.log(largeSum());
console.log(collatzSequence(13));
console.log(longestCollatzSequence(15));
console.log(longestCollatzSequence(1000));
console.log(powerDigitSum(15));
console.log(powerDigitSum(1000));
console.log(factorialDigitSum(10));
console.log(factorialDigitSum(100));
console.log(largestGridProduct());