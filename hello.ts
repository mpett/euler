function fib(n: number): number {
    var f:number[] = new Array(n);
    
    f[0] = 0;
    f[1] = 1;

    var num:number = 0;

    for (num = 2; num < n; num++) {
        f[num] = f[num - 1] + f[num - 2];
    }
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