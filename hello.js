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
