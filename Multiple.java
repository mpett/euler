import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Multiple {
    public static void main(String[] args) {
        System.out.println(multiples());
        System.out.println(evenFibSum());
        System.out.println(pollardRho(13195));

        ArrayList<Integer> r = trialDivision(324324);

        String a_b;

        for (int e : r)
            System.out.print(e + " ");
        System.out.println();

        ArrayList<BigInteger> rx =
                trialDivision(
                        new BigInteger("600851475143"));

        for (BigInteger ex : rx)
            System.out.print(ex.toString() + " ");

        System.out.println();
        System.out.println(isPalindrome(9009));
        System.out.println(isPalindrome(9019));
        System.out.println(largePalindromeProduct());
        System.out.println(largestPalindromeProduct());

        System.out.println(smallestMultiple(10));
        System.out.println(smallestMultiple(20));
        System.out.println(sumSquareDifference(10));
        System.out.println(sumSquareDifference(100));
        System.out.println();

        ArrayList<Integer> ry = sieveOfErathostenes(200000);
        System.out.println(ry.size());
        System.out.println(ry.get(10000));
        System.out.println();

        System.out.println(pythagoreanTriplet());

        System.out.println(summationOfPrimes());

        long[] f = factors(28);
        for (long e : f)
            System.out.print(e + " ");
        System.out.println();

        //System.out.println(firstTriangNumberWithFactors(500));
        System.out.println(fasterFirstTriangNumber(500));

        try {
            largeSum();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void largeSum() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < 100; i++) {
            String input = reader.readLine();
            BigInteger inputNumber = new BigInteger(input);
            sum = sum.add(inputNumber);
        }
        String sumString = sum.toString();
        String result = "";
        for (int i = 0; i < 10; i++)
            result += sumString.charAt(i);
        System.out.println(result);
    }

    private static int fasterFirstTriangNumber(int numberOfFactors) {
        int t = 1;
        int a = 1;
        int cnt = 0;
        int tt; int i;
        int exponent;

        ArrayList<Integer> primes = sieveOfErathostenes(2000);
        int[] primeArray = new int[primes.size()];

        for (int j = 0; j < primeArray.length; j++)
            primeArray[j] = primes.get(j);

        while (cnt <= numberOfFactors) {
            cnt = 1;
            a++;
            t += a;
            tt = t;
            for (i = 0; i < primeArray.length; i++) {
                if (primeArray[i]*primeArray[i] > tt) {
                    cnt = 2 * cnt;
                    break;
                }
                exponent = 1;
                while (tt % primeArray[i] == 0) {
                    exponent++;
                    tt /= primeArray[i];
                }
                if (exponent > 1)
                    cnt *= exponent;
                if (tt == 1)
                    break;
            }
        }

        return t;
    }

    private static long firstTriangNumberWithFactors(int numberOfFactors) {
        int n = 1;
        long maxFactor = 0;
        while (true) {
            long triangularNumber = generateTriangularNumber(n);
            int factors = factors(triangularNumber).length;
            if (factors >= numberOfFactors)
                break;
            n++;
        }
        return generateTriangularNumber(n);
    }

    private static long generateTriangularNumber(int index) {
        long triangularNumber = 0;
        for (int p = 1; p <= index; p++)
            triangularNumber += p;
        return triangularNumber;
    }

    private static long[] factors(long n) {
        ArrayList<Long> factors = new ArrayList<>();
        for (long i = 1; i <= (n/2); i++) {
            if (n % i == 0)
                factors.add(i);
        }
        long[] f = new long[factors.size() + 1];
        for (int i = 0; i < factors.size(); i++) {
            f[i] = factors.get(i);
        }
        f[f.length - 1] = n;
        return f;
    }

    private static int gridProduct() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        int[][] inputMatrix = new int[20][20];

        int a = 0;
        int b;

        for (int i = 0; i < 20; i++) {
            String input = reader.readLine();
            String[] numbers = input.split(" ");
            for (int j = 0; j < numbers.length; j++) {
                inputMatrix[i][j] =
                        Integer.parseInt(numbers[j]);
            }
        }

        ArrayList<Integer> adjacentProducts
                = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                // Check up
                if ((i-3) >= 0)
                    adjacentProducts.add(inputMatrix[i][j]
                            * inputMatrix[i-1][j]
                            * inputMatrix[i-2][j]
                            * inputMatrix[i-3][j]);
                // Check down
                if ((i+3) < 20)
                    adjacentProducts.add(inputMatrix[i][j]
                            * inputMatrix[i+1][j]
                            * inputMatrix[i+2][j]
                            * inputMatrix[i+3][j]);
                // Check left
                if ((j-3) >= 0)
                    adjacentProducts.add(inputMatrix[i][j]
                            * inputMatrix[i][j-1]
                            * inputMatrix[i][j-2]
                            * inputMatrix[i][j-3]);
                // Check right
                if ((j+3) < 20)
                    adjacentProducts.add(inputMatrix[i][j]
                            * inputMatrix[i][j+1]
                            * inputMatrix[i][j+2]
                            * inputMatrix[i][j+3]);
                // Check right diagonal
                if ((i+3) < 20 && (j+3) < 20)
                    adjacentProducts.add(inputMatrix[i][j]
                            * inputMatrix[i+1][j+1]
                            * inputMatrix[i+2][j+2]
                            * inputMatrix[i+3][j+3]);
                // Check left diagonal
                if ((i+3) < 20 && (j-3) >= 0)
                    adjacentProducts.add(inputMatrix[i][j]
                            * inputMatrix[i+1][j-1]
                            * inputMatrix[i+2][j-2]
                            * inputMatrix[i+3][j-3]);
            }
        }

        reader.close();

        return Collections.max(adjacentProducts);
    }

    private static long summationOfPrimes() {
        ArrayList<Integer> primes
                = sieveOfErathostenes(2000000);
        long sum = 0;
        for (int element : primes) {
            sum += element;
        }

        return sum;
    }

    private static int pythagoreanTriplet() {
        for (int a = 1; a <= 1000; a++) {
            for (int b = 1; b <= 1000; b++) {
                for (int c = 1; c <= 1000; c++) {
                    if ((a+b+c) == 1000
                            && ((a*a) + (b*b)) == (c*c)) {
                        return a*b*c;
                    }
                }
            }
        }
        return -1;
    }

    private static long largestProduct() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        int consLength = 13;

        ArrayList<Long> consecutiveNumbers
                    = new ArrayList<>();

        String inputNumber = "";

        for (int i = 0; i < 20; i++) {
            String inputString = reader.readLine();
            inputNumber += inputString;
        }

        System.out.println();

        for (int i = 0; i < inputNumber.length()
                            - consLength + 1; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < consLength; j++) {
                sb.append(inputNumber.charAt(i+j));
            }

            String substring = sb.toString();
            System.out.println(substring + " = "
                    + digitProduct(substring));
            consecutiveNumbers.add(digitProduct(substring));
        }

        return Collections.max(consecutiveNumbers);
    }

    private static long digitProduct(String s) {
        long product = 1;
        for (int i = 0; i < s.length(); i++) {
            long n = Integer.parseInt(s.charAt(i) + "");
            product *= n;
        }
        return product;
    }

    private static ArrayList<Integer> sieveOfErathostenes(int n) {
        boolean[] A = new boolean[n];
        for (int i = 0; i < n; i++)
            A[i] = true;

        int nSqrt = (int) Math.sqrt((double) n);

        // Maybe <= here
        for (int i = 2; i < nSqrt; i++) {
            if (A[i]) {
                for (int j = (i*i); j < n; j+=i)
                    A[j] = false;
            }
        }

        ArrayList<Integer> results = new ArrayList<>();

        for (int i = 2; i < n; i++) {
            if (A[i])
                results.add(i);
        }

        return results;
    }

    private static int sumSquareDifference(int n) {
        int sumOfSquares = 0;
        int naturalSum = 0;
        for (int i = 1; i <= n; i++) {
            sumOfSquares += (i * i);
            naturalSum += i;
        }
        int squareOfSum = naturalSum * naturalSum;
        int sumSquareDifference = squareOfSum - sumOfSquares;
        return sumSquareDifference;
    }

    private static int smallestMultiple(int largestFactor) {
        boolean smallestMultipleFound = false;
        int n = 0;

        while (!smallestMultipleFound) {
            n+=largestFactor;
            for (int i = 1; i <= largestFactor; i++) {
                if (n % i != 0)
                    break;
                if (i == largestFactor)
                    smallestMultipleFound = true;
            }
        }
        return n;
    }

    private static int largestPalindromeProduct() {
        ArrayList<Integer> palindromes
                = new ArrayList<>();

        for (int a = 999; a >= 900; a--) {
            for (int b = 999; b >= 900; b--) {
                int n = a * b;
                if (isPalindrome(n))
                    palindromes.add(n);
            }
        }
        return Collections.max(palindromes);
    }

    private static int largePalindromeProduct() {
        int a = 999;
        int b = 999;
        int largeThreeDigitProduct = a * b;
        int n = largeThreeDigitProduct;
        int c = 2;

        while (true) {
            if (c % 2 == 0)
                a--;
            else
                b--;
            if (a == 100)
                break;
            n = a * b;
            c++;
        }
        return n;
    }

    private static boolean isPalindrome(int n) {
        String numberString = n + "";
        String reversed = new StringBuilder(
                numberString).reverse().toString();
        if (numberString.equals(reversed))
            return true;
        else
            return false;
    }

    private static ArrayList<BigInteger> trialDivision(BigInteger n) {
        ArrayList<BigInteger> a = new ArrayList<>();
        BigInteger f = new BigInteger("2");

        while (n.compareTo(BigInteger.ONE) == 1) {
            if (n.mod(f) == BigInteger.ZERO) {
                a.add(f);
                n = n.divide(f);
            } else {
                f = f.add(BigInteger.ONE);
            }
        }
        return a;
    }

    private static ArrayList<Integer>
                trialDivision(int n) {
        ArrayList<Integer> a
                = new ArrayList<>();
        int f = 2;

        while (n > 1) {
            if (n % f == 0) {
                a.add(f);
                n /= f;
            } else
                f += 1;
        }
        return a;
    }

    private static int g(int x, int n) {
        double dx = (double) x;
        dx = dx + 1.0;
        int rx = (int) Math.pow(dx, 2.0);
        return rx % n;
    }

    private static int pollardRho(int n) {
        int x = 2;
        int y = 2;
        int d = 1;

        while (d == 1) {
            x = g(x, n);
            y = g(g(y,n),n);
            d = gcd(Math.abs(x-y), n);
        }

        if (d == n) {
            return -1;
        } else
            return d;
    }

    private static int gcd(int a, int b) {
        int remainder = 0;
        while (b != 0) {
            remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    private static int evenFibSum() {
        int n = 2;
        int sumOfEvenFibNumbers = 0;

        while (true) {
            int fibNumber = fib(n);
            if (fibNumber >= 4000000)
                break;
            if (fibNumber % 2 == 0) {
                sumOfEvenFibNumbers
                        += fibNumber;
            }
            n++;
        }
        return sumOfEvenFibNumbers;
    }

    private static int multiples() {
        int sum = 0;

        for(int realNumber = 1; realNumber < 1000;
                                    realNumber++) {
            if (realNumber % 3 == 0
                    || realNumber % 5 == 0)
                sum += realNumber;
        }
        return sum;
    }

    private static int fib(int n) {
        int f[] = new int[n+1];
        int i;
        f[0] = 0;
        f[1] = 1;

        for (i = 2; i <= n; i++) {
            f[i] = f[i-1] + f[i-2];
        }

        return f[n];
    }
}
