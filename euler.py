from math import sqrt
import decimal

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
    print(smallest_multiple_2(10))
    print(smallest_multiple_2(20))
    
    