function main() {
    console.log("Hello World");
    var a = [151, 51, 51, 81, 7, 68, 971, 9635, 5, 1, 4];
    console.log(a);
    quicksort(a, 0, a.length - 1);
    console.log(a);
    var b = [1, 1, 51, 8, 7, 87, 1681, 68125, 151, 7, 196, 1];
    console.log(b);
    heapsort(b);
    console.log(b);
    var c = sieve(300);
    console.log(c);
}
function sieve(n) {
    var a = new Array(n);
    for (var i = 2; i < n; i++) {
        a[i] = true;
    }
    for (var i = 2; i < Math.sqrt(n); i++) {
        for (var j = i * i; j < n; j += i) {
            if (a[j]) {
                a[j] = false;
            }
        }
    }
    var b = new Array();
    for (var i = 0; i < n; i++) {
        if (a[i]) {
            b.push(i);
        }
    }
    return b;
}
function heapsort(a) {
    var n = a.length;
    for (var i = Math.floor(n / 2 - 1); i >= 0; i--) {
        heapify(a, n, i);
    }
    for (var i = n - 1; i >= 0; i--) {
        var tmp = a[0];
        a[0] = a[i];
        a[i] = tmp;
        heapify(a, i, 0);
    }
}
function heapify(a, n, i) {
    var hi = i;
    var l = 2 * i + 1;
    var r = 2 * i + 2;
    if (l < n && a[l] > a[hi]) {
        hi = l;
    }
    if (r < n && a[r] > a[hi]) {
        hi = r;
    }
    if (hi != i) {
        var tmp = a[hi];
        a[hi] = a[i];
        a[i] = tmp;
        heapify(a, n, hi);
    }
}
function quicksort(a, lo, hi) {
    if (lo < hi) {
        var p = partition(a, lo, hi);
        quicksort(a, p + 1, hi);
        quicksort(a, lo, p - 1);
    }
}
function partition(a, lo, hi) {
    var pivot = a[hi];
    var i = lo - 1;
    for (var j = lo; j < hi; j++) {
        if (a[j] < pivot) {
            i++;
            var tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }
    var tmp = a[i + 1];
    a[i + 1] = a[hi];
    a[hi] = tmp;
    return i + 1;
}
main();
