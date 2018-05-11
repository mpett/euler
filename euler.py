from math import sqrt
import decimal

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
    
    