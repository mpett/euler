function sieve_of_erathostenes(n) {
    var A = new Array(n);
    for (var index = 0; index < n; index++)
        A[index] = true;
    for (var i = 2; i < Math.sqrt(n); i++) {
        if (A[i]) {
            for (var j = i * i; j < n; j += i) {
                A[j] = false;
            }
        }
    }
    A = A.map(function(x, i) { if (x) return i; })
            .filter(function(x) { if (x) return x; });
    return A;
}
function sum_square_difference(n) {
    var array_of_natural_numbers = new Array(n);
    for (var num = 1; num <= n; num++)
        array_of_natural_numbers[num - 1] = num;
    var sum = array_of_natural_numbers
                .reduce(function(x, y) { return x + y; });
    var square_of_sum = sum * sum;
    var sum_of_squares = array_of_natural_numbers
                            .map(function(x) { return x*x; })
                                .reduce(function(x, y) { return x + y; });
    var sum_square_difference = 
        square_of_sum - sum_of_squares;
    return sum_square_difference;
}
function smallest_multiple(n) {
    var upper_limit = n;
    var return_value = -1;
    var num = 0;
    while (true) {
        num++;
        var sum_of_remainders = 0;
        for (var divisor = 1; divisor <= upper_limit; divisor++) {
            sum_of_remainders += (num % divisor);
        }
        if (sum_of_remainders == 0) {
            return_value = num;
            break;
        }
    }
    return return_value;
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
    if (n > 2) {
        prime_factors.push(n);
    }
    return prime_factors;
}
function fib(n) {
    var f = new Array(n);
    f[0] = 0;
    f[1] = 1;
    var num = 0;
    for (num = 2; num < n; num++) {
        f[num] = f[num - 1] + f[num - 2];
    }
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
console.log(smallest_multiple(10));
//console.log(smallest_multiple(20)); TODO: implement faster version
console.log(sum_square_difference(10));
console.log(sum_square_difference(100));
console.log(sieve_of_erathostenes(100).toString());
