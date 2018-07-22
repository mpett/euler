function main():void {
    console.log("Hello World");
    var a:number[] = [151,51,51,81,7,68,971,9635,5,1,4];
    console.log(a);
    quicksort(a, 0, a.length - 1);
    console.log(a);
    var b:number[] = [1,1,51,8,7,87,1681,68125,151,7,196,1];
    console.log(b);
    heapsort(b);
    console.log(b);
    var c:number[] = sieve(300);
    console.log(c);
}

function sieve(n:number):number[] {
    var a:boolean[] = new Array(n);
    for (var i:number = 2; i < n; i++) {
        a[i] = true;
    }
    for (var i:number = 2; i < Math.sqrt(n); i++) {
        for (var j:number = i*i; j < n; j += i) {
            if (a[j]) {
                a[j] = false;
            }
        }
    }
    var b:number[] = new Array();
    for (var i:number = 0; i < n; i++) {
        if (a[i]) {
            b.push(i);
        }
    }
    
    return b;
}

function heapsort(a:number[]) {
    var n:number = a.length;
    for (var i:number = Math.floor(n/2-1); i >= 0; i--) {
        heapify(a, n, i);
    }
    for (var i:number = n-1; i >= 0; i--) {
        var tmp:number = a[0];
        a[0] = a[i];
        a[i] = tmp;
        heapify(a, i, 0);
    }
}

function heapify(a:number[], n:number, i:number) {
    var hi:number = i;
    var l:number = 2*i+1;
    var r:number = 2*i+2;
    if (l < n && a[l] > a[hi]) {
        hi = l;
    }
    if (r < n && a[r] > a[hi]) {
        hi = r;
    }
    if (hi != i) {
        var tmp:number = a[hi];
        a[hi] = a[i];
        a[i] = tmp;
        heapify(a, n, hi);
    }
}

function quicksort(a:number[], lo:number, hi:number):void {
    if (lo < hi) {
        var p:number = partition(a, lo, hi);
        quicksort(a, p + 1, hi);
        quicksort(a, lo, p - 1);
    }
}

function partition(a:number[], lo:number, hi:number):number {
    var pivot:number = a[hi];
    var i:number = lo - 1;

    for (var j:number = lo; j < hi; j++) {
        if (a[j] < pivot) {
            i++;
            var tmp:number = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    var tmp:number = a[i+1];
    a[i+1] = a[hi];
    a[hi] = tmp;

    return i+1;
}

main();
