import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class Multiple {
    public static void main(String[] args) {
        System.out.println(multiples());
        System.out.println(evenFibSum());
        System.out.println(pollardRho(13195));
        ArrayList<Integer> r = trialDivision(324324);
        for (int e : r)
            System.out.print(e + " ");
        System.out.println();
        ArrayList<BigInteger> rx =
                trialDivision(new BigInteger("600851475143"));
        for (BigInteger ex : rx)
            System.out.print(ex.toString() + " ");
        System.out.println();
        System.out.println(isPalindrome(9009));
        System.out.println(isPalindrome(9019));
        System.out.println(largePalindromeProduct());
        System.out.println(largestPalindromeProduct());
        System.out.println(smallestMultiple());
    }

    private static int smallestMultiple() {
        boolean smallestMultipleFound = false;
        int n = 0;
        while (!smallestMultipleFound) {
            n++;
            for (int i = 1; i <= 20; i++) {
                if (n % i != 0)
                    break;
                if (i == 20)
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
            if (isPalindrome(n))
                System.out.println
                        (a + " * " + b
                                + " = " + n);
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
