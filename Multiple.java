import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

public class Multiple {

    private static ArrayList<String> permutations;

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

        long[] q = collatzSequence(13);

        for (long e : q) {
            if (e != q[q.length - 1])
                System.out.print(e + " -> ");
            else
                System.out.print(e);
        }

        System.out.println();
        //System.out.println(longestCollatzSequence());

        BigInteger number = BigInteger.TWO;
        number = number.pow(15);
        System.out.println(number.toString());
        int digitSum = digitSum(number);
        System.out.println(digitSum);

        System.out.println(wordNumberCount(5));
        System.out.println(wordNumberCount(1000));

        System.out.println(numberOfLatticePaths(20));

        System.out.println(digitSum(factorial(10)));
        System.out.println(digitSum(factorial(100)));

        System.out.println(d(220));
        System.out.println(d(284));

        System.out.println(sumOfAmicableNumbers(10000));

        try {
            System.out.println(namesScores());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(sumOfProperDivisors(28));

        permutation("0123456789");
        Collections.sort(permutations);
        String millionthPermutation = permutations.get(999999);
        System.out.println(millionthPermutation);
    }

    private static void permutation(String str) {
        permutations = new ArrayList<>();
        permutation("", str);
    }

    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0)
            permutations.add(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i),
                        str.substring(0,i)
                                + str.substring(i+1, n));
        }
    }

    private static long nonAbundantSum() {
        long limit = 28123;
        ArrayList<Long> abundantNumbers
                = new ArrayList<>();
        for (long n = 1; n <= limit; n++) {
            if (n%100 == 0)
                System.out.println(n);
            if (isAbundant(n))
                abundantNumbers.add(n);
        }

        ArrayList<Long> abundantSums = new ArrayList<>();

        System.out.println("Added abundant numbers");

        for (long a : abundantNumbers) {
            if (a%100 == 0)
                System.out.println(a);
            for (long b : abundantNumbers) {
                long abundantSum = a + b;
                abundantSums.add(abundantSum);
            }
        }

        System.out.println("Added abundant sums");

        ArrayList<Long> nonAbundantSums
                = new ArrayList<>();

        for (long n = 1; n <= limit; n++) {
            if (n%100 == 0)
                System.out.println(n);
            if (!abundantSums.contains(n))
                nonAbundantSums.add(n);
        }

        System.out.println("Added non-abundant sums");

        long sumOfNonAbundantSums = 0;

        for (long e : nonAbundantSums)
            sumOfNonAbundantSums += e;

        System.out.println("Summed");

        return sumOfNonAbundantSums;
    }

    private static boolean isAbundant(long n) {
        return sumOfProperDivisors(n) > n ? true : false;
    }

    private static long sumOfProperDivisors(long n) {
        long[] divisors = factors(n);
        long sumOfDivisors = 0;
        divisors[divisors.length - 1] = 0;
        for (long e : divisors)
            sumOfDivisors += e;
        return sumOfDivisors;
    }

    private static long namesScores() throws IOException {
        BufferedReader reader =
                new BufferedReader(
                        new FileReader("p022_names.txt"));
        String line = reader.readLine();
        StringBuilder stringBuilder = new StringBuilder();

        while (line != null) {
            stringBuilder.append(line);
            line = reader.readLine();
        }

        String input = stringBuilder.toString();
        String[] names = input.split("\",\"");
        String firstElement = names[0];
        firstElement = firstElement.substring(1);
        String lastElement = names[names.length - 1];
        lastElement
                = lastElement.substring
                (0,
                        lastElement.length() - 1);
        names[names.length - 1] = lastElement;
        names[0] = firstElement;

        ArrayList<String> arrayOfNames = new ArrayList<>();
        for (String element : names)
            arrayOfNames.add(element);
        Collections.sort(arrayOfNames);

        long sum = 0;

        for (int i = 0; i < arrayOfNames.size(); i++) {
            int position = i + 1;
            String name = arrayOfNames.get(i);
            sum += position * getNameScore(name);
        }

        return sum;
    }

    private static int getNameScore(String name) throws IOException {
        int alphabetDelimiter = 64;
        int sum = 0;
        for (int i = 0; i < name.length(); i++) {
            char letter = name.charAt(i);
            int asciiValue = (int) letter;
            int alpabetValue = asciiValue - alphabetDelimiter;
            sum += alpabetValue;
        }
        return sum;
    }

    private static long sumOfAmicableNumbers(int limit) {
        ArrayList<Long> amicableNumbers = new ArrayList<>();

        for (int i = 1; i < limit; i++) {
            long a = (long) i;
            long b = d(a);
            if (d(b) == a && a != b) {
                if (!amicableNumbers.contains(a))
                    amicableNumbers.add(a);
                if (!amicableNumbers.contains(b))
                    amicableNumbers.add(b);
            }
        }

        long sum = 0;
        for (long e : amicableNumbers)
            sum += e;

        return sum;
    }

    private static long d(long n) {
        long[] divisors = factors(n);
        long sum = 0;
        for (int i = 0; i < divisors.length - 1; i++)
            sum += divisors[i];
        return sum;
    }

    private static BigInteger factorial(int factorialOf) {
        BigInteger factorial = BigInteger.ONE;

        for (int i = 1; i <= factorialOf; i++) {
            BigInteger index = new BigInteger(i + "");
            factorial = factorial.multiply(index);
        }

        return factorial;
    }

    private static void maximumPathSum() throws IOException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> inputs = new ArrayList<>();

        while (true) {
            String input = scanner.nextLine();
            if (input.equals(""))
                break;
            inputs.add(input);
        }

        String lastRow = inputs.get(inputs.size() -1);
        String[] lastRowSplit = lastRow.split(" ");
        int arraySize = lastRowSplit.length;

        int[][] triangleMatrix = new int[inputs.size()][arraySize];

        for (int i = 0; i < inputs.size(); i++) {
            String[] split = inputs.get(i).split(" ");
            for (int j = 0; j < split.length; j++) {
                triangleMatrix[i][j] = Integer.parseInt(split[j]);
            }
        }

        int previousSum = 0;

        Random r = new Random();
        int randomNumber = r.nextInt(100);

        while (true) {
            int k = 0;
            int sum = triangleMatrix[0][0];
            for (int i = 0; i < triangleMatrix.length - 1; i++) {
                if (r.nextInt(100) % 2 == 0)
                    sum += triangleMatrix[i+1][k];
                else {
                    sum += triangleMatrix[i+1][k+1];
                    k++;
                }
            }

            if (sum > previousSum) {
                System.out.println(sum);
                previousSum = sum;
            }
        }
    }

    /**
     * Number of lattice paths from (0,0) to (gridSize, gridSize)
     * in a quadratic lattice.
     *
     * @param gridSize
     * @return numberOfPaths
     */
    private static long numberOfLatticePaths(int gridSize) {
        int m = 2 * gridSize;
        int n = gridSize;
        double k = choose(m, n);
        long numberOfPaths = (long) k;
        return numberOfPaths;
    }

    /**
     * A simple n choose k implementation.
     *
     * @param x
     * @param y
     * @return
     */
    private static double choose(int x, int y) {
        if (y < 0 || y > x)
            return 0;

        if (y > x/2)
            y = x - y;

        double denominator = 1.0, numerator = 1.0;

        for (int i = 1; i <= y; i++) {
            denominator *= i;
            numerator *= (x + 1 - i);
        }

        return numerator / denominator;
    }

    private static int wordNumberCount(int range) {
        String bigStringOfWordNumbers = "";
        for (int i = 1; i <= range; i++) {
            String inputNumber = i + "";
            if (inputNumber.length() == 1)
                inputNumber = "000" + inputNumber;
            if (inputNumber.length() == 2)
                inputNumber = "00" + inputNumber;
            if (inputNumber.length() == 3)
                inputNumber = "0" + inputNumber;
            try {
                bigStringOfWordNumbers
                        += numberToWord(inputNumber) + " ";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        bigStringOfWordNumbers =
                bigStringOfWordNumbers
                        .replaceAll("\\s+","");

        return bigStringOfWordNumbers.length();
    }

    private static String numberToWord(String inputNumber) throws Exception {
        if (inputNumber.length() != 4) {
            System.err.println("Input String has to be four characters long.");
            throw new Exception();
        }

        String[] numberToWord = new String[1001];

        numberToWord[0] = "zero";

        numberToWord[1] = "one"; numberToWord[2] = "two"; numberToWord[3] = "three";
        numberToWord[4] = "four"; numberToWord[5] = "five"; numberToWord[6] = "six";
        numberToWord[7] = "seven"; numberToWord[8] = "eight"; numberToWord[9] = "nine";

        numberToWord[10] = "ten";

        numberToWord[11] = "eleven"; numberToWord[12] = "twelve"; numberToWord[13] = "thirteen";
        numberToWord[14] = "fourteen"; numberToWord[15] = "fifteen"; numberToWord[16] = "sixteen";
        numberToWord[17] = "seventeen"; numberToWord[18] = "eighteen"; numberToWord[19] = "nineteen";

        numberToWord[20] = "twenty"; numberToWord[30] = "thirty"; numberToWord[40] = "forty";
        numberToWord[50] = "fifty"; numberToWord[60] = "sixty"; numberToWord[70] = "seventy";
        numberToWord[80] = "eighty"; numberToWord[90] = "ninety";

        numberToWord[100] = "hundred"; numberToWord[1000] = "thousand";

        String input = inputNumber;
        String output = "";

        int firstDigit = Integer.parseInt(input.charAt(0) + "");
        int secondDigit = Integer.parseInt(input.charAt(1) + "");
        int thirdDigit = Integer.parseInt(input.charAt(2) + "");
        int fourthDigit = Integer.parseInt(input.charAt(3) + "");

        if (firstDigit != 0) {
            output += numberToWord[firstDigit] + " " + numberToWord[1000] + " ";
            if (secondDigit == 0 && (thirdDigit != 0 || fourthDigit != 0))
                output += "and ";
        }

        if (secondDigit != 0) {
            output += numberToWord[secondDigit] + " " + numberToWord[100] + " ";
            if (thirdDigit != 0 || fourthDigit != 0)
                output += "and ";
        }

        if (secondDigit == 0 && thirdDigit == 0 && fourthDigit != 0)
            output += numberToWord[fourthDigit];

        if (thirdDigit == 1 && fourthDigit == 0)
            output += numberToWord[10];
        else if (thirdDigit == 1
                && (fourthDigit >= 1 && fourthDigit <= 9)) {
            String twoFinalDigits = thirdDigit + "" + fourthDigit;
            int finalDigits = Integer.parseInt(twoFinalDigits);
            output += numberToWord[finalDigits];
        } else if ((thirdDigit >= 2 && thirdDigit <= 9)
                && fourthDigit == 0) {
            String twoFinalDigits = thirdDigit + "" + fourthDigit;
            int finalDigits = Integer.parseInt(twoFinalDigits);
            output += numberToWord[finalDigits];
        } else if ((thirdDigit >= 2 && thirdDigit <= 9)
                && (fourthDigit >= 1 && fourthDigit <= 9)) {
            String twoFinalDigits = thirdDigit + "0";
            int finalDigits = Integer.parseInt(twoFinalDigits);
            output += numberToWord[finalDigits];
            output += " ";
            output += numberToWord[fourthDigit];
        } else if (secondDigit != 0 && thirdDigit == 0 && fourthDigit != 0)
            output += numberToWord[fourthDigit];

        return output;
    }

    private static int digitSum(BigInteger input) {
        String inputString = input.toString();
        int sum = 0;
        for (int i = 0; i < inputString.length(); i++) {
            int digit = Integer
                    .parseInt(inputString.charAt(i) + "");
            sum += digit;
        }
        return sum;
    }

    private static long longestCollatzSequence() {
        long maxSequence = 0;
        long n = 999999;
        long biggestStartingNumber = 0;
        while (n > 1) {
            long sequenceLength
                    = collatzSequence(n).length;
            if (sequenceLength > maxSequence) {
                maxSequence = sequenceLength;
                biggestStartingNumber = n;
            }

            n--;
        }
        return biggestStartingNumber;
    }

    private static long[] collatzSequence(long n) {
        ArrayList<Long> sequence = new ArrayList<>();
        sequence.add(n);

        while (true) {
            if (n == 1)
                break;
            if (n % 2 == 0)
                n /= 2;
            else
                n = 3*n + 1;
            sequence.add(n);
        }

        long[] collatzSequence = new long[sequence.size()];
        for (int i = 0; i < collatzSequence.length; i++)
            collatzSequence[i] = sequence.get(i);
        return collatzSequence;
    }

    private static void largeSum() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < 100; i++) {
            String input = reader.readLine();
            BigInteger inputNumber
                    = new BigInteger(input);
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
