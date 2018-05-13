from math import sqrt
import decimal

def sieve_of_erathostenes(n):
    A = [None] * n
    for i in range (2, n):
        A[i] = True
    for i in range (1, int(sqrt(n)) + 1):
        if(A[i]):
            for j in range(i**2,n,i):
                A[j] = False
    B = []
    for i in range(1, n):
        if (A[i]):
            B.append(i)
    return B

def sum_square_difference(n):
    difference = square_of_sum(n) - sum_of_squares(n)
    return difference

def square_of_sum(n):
    sum = 0
    for i in range(1, n+1):
        sum += i
    square_of_sum = sum ** 2
    return square_of_sum

def sum_of_squares(n):
    sum = 0
    for i in range(1, n+1):
        sum += i ** 2
    return sum

def smallest_multiple(largest_factor):
    n = 0
    smallest_multiple_found = False

    while (not smallest_multiple_found):
        n += largest_factor
        for i in range(1, largest_factor + 1):
            if (n % i != 0):
                break
            if (i == largest_factor):
                smallest_multiple_found = True
    return n

def palindrome_product():
    f = []
    for i in range(1, 1000):
        for j in range(1, 1000):
            product = i * j
            pstr = str(product)
            pstr_reversed = pstr[::-1]
            if (pstr == pstr_reversed):
                f += [product]
    return f

def prime_factors(n):
    f = []
    while (n % 2 == 0):
        f += [2]
        n = n / 2
    for i in range(3, int(sqrt(n))+1, 2):
        while (n % i == 0):
            f += [i]
            n = n / i
    if (n > 2):
        f += [n]
    return f

def sum_of_even_valued_fib_numbers():
    sum = 0
    for n in range (2, 10000):
        fib = fibonacci(n)
        if (fib >= 4000000):
            break
        if (fib % 2 == 0):
            sum += fib
    return sum

def fibonacci(n):
    f = {}
    f[0] = 0
    f[1] = 1
    for i in range (2, n+1):
        f[i] = f[i-1] + f[i-2]
    return f[n]

def multiples():
    sum = 0
    for number in range(1,1000):
        if (number % 3 == 0 or number % 5 == 0):
            sum += number
    return sum

def hello():
    print("Hello World")

if __name__ == '__main__':
    hello()
    result = multiples()
    print(result)
    print(sum_of_even_valued_fib_numbers())
    print(prime_factors(315))
    print(prime_factors(600851475143))
    print(max(palindrome_product()))
    print(smallest_multiple(10))
    #print(smallest_multiple(20))
    print(sum_square_difference(10))
    print(sum_square_difference(100))
    print(sieve_of_erathostenes(100))
    print(sieve_of_erathostenes(100)[5])
    print(sieve_of_erathostenes(200000)[10000])
    print(sieve_of_erathostenes(300000000))
    
    
    