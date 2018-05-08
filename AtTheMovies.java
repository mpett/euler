import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AtTheMovies {

    private static ArrayList<Review> reviews;
    private static ArrayList<Review> trainingSet;
    private static ArrayList<Review> testSet;

    private static final int TEST_SET_SIZE = 100;

    public static void main(String[] args) throws Exception {
        reviews = new ArrayList<>();

        try {
            handleTrainingData
                    ("train.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        trainingSet = new ArrayList<>();
        testSet = new ArrayList<>();

        int reviewIndex = 0;
        for (Review review : reviews) {
            if (reviewIndex < reviews.size()
                    - TEST_SET_SIZE)
                trainingSet.add(review);
            else
                testSet.add(review);
            reviewIndex++;
        }

        System.out.println("Training Set Size: "
                + trainingSet.size());
        System.out.println("Test Set Size: "
                + testSet.size());

        predictAllReviews();

        System.out.println();

        for (Review review : trainingSet) {
            cleanReviewTextFromNonAlphCharacters(review);
        }

        for (Review review : testSet) {
            cleanReviewTextFromNonAlphCharacters(review);
        }

        PredictionModel predictionModel = new PredictionModel(trainingSet);

        for (Review review : trainingSet) {
            System.out.println("Title: " + review.movieTitle);
            System.out.println("Critic Name: " + review.criticName);
            System.out.println("Stars: " + review.starValue);
            System.out.println("Text: " + review.reviewText);
            System.out.println("Commas: " + review.numCommas);
            System.out.println("Dots: " + review.numDots);
            System.out.println("Ings: " + predictionModel
                    .numberOfIngInText(review));
            System.out.println();
        }

        predictionModel.train();

        int numCorrect = 0;

        for (Review testReview : testSet) {
            String predictedCritic
                    = predictionModel.predict(testReview);
            if (predictedCritic.equals(testReview.criticName))
                numCorrect++;

        }

        System.out.println();
        System.out.println("Num Correct: " + numCorrect + " / " + TEST_SET_SIZE);
    }

    private static void predictAllReviews() {
        int numCorrect = 0;

        for (Review testReview : testSet) {
            Random random = new Random();
            int prediction = random.nextInt(4);
            String predictedAuthor = "";

            switch (prediction) {
                case 0:
                    predictedAuthor = "Alpha";
                    break;
                case 1:
                    predictedAuthor = "Beta";
                    break;
                case 2:
                    predictedAuthor = "Gamma";
                    break;
                default:
                    predictedAuthor = "Delta";
            }

            if (predictedAuthor.equals(testReview.criticName))
                numCorrect++;
        }

        System.out.println("Num correct: " + numCorrect
                        + " / " + TEST_SET_SIZE);
    }

    private static void cleanReviewTextFromNonAlphCharacters(Review review) {
        String reviewText = review.reviewText;
        StringBuilder stringBuilder = new StringBuilder();

        for (int characterIndex = 0; characterIndex
                < reviewText.length(); characterIndex++) {
            char currentCharacter = reviewText.charAt(characterIndex);
            if (currentCharacter == 44)
                review.numCommas += 1;
            if (currentCharacter == 46)
                review.numDots += 1;

            if (characterIndex >= 1) {
                char previousCharacter = reviewText.charAt(characterIndex - 1);
                if ((currentCharacter == 117 && previousCharacter == 92)
                        || (currentCharacter == 32 && previousCharacter == 32))
                    continue;
                if (previousCharacter >= 48 && previousCharacter <= 57)
                    continue;
                if (previousCharacter == 92)
                    continue;
            }

            if ((currentCharacter >= 65 && currentCharacter <= 90)
                    || (currentCharacter >= 97 && currentCharacter <= 122)
                    || (currentCharacter == 32))
                stringBuilder.append(currentCharacter);
        }

        reviewText = stringBuilder.toString().toLowerCase();
        review.reviewText = reviewText;
    }

    public static double distance(double[] p1, double[] p2)
                        throws Exception {
        if (p1.length != p2.length)
            throw new Exception();
        double sum = 0.0D;

        for(int i = 0; i < p1.length; ++i) {
            double dp = p1[i] - p2[i];
            sum += dp * dp;
        }

        return Math.sqrt(sum);
    }

    private static void handleTrainingData(String inputFileName)
            throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(inputFileName));
        String line = reader.readLine();
        StringBuilder stringBuilder = new StringBuilder();

        while (line != null) {
            stringBuilder.append(line);
            line = reader.readLine();
        }

        String input = stringBuilder.toString();
        String[] inputs = input.split("}");

        for (String review : inputs) {
            String[] reviewElements
                    = review.split("\", \"");
            String title = reviewElements[0].substring(11);
            String[] reviewAndStars =
                    reviewElements[1].split("\"Review\"");
            String stars = reviewAndStars[0].substring(8, 11);
            String reviewText = reviewAndStars[1].substring(3);
            String criticName = reviewElements[2]
                    .substring(10, reviewElements[2].length()-1);
            double starValue = Double.parseDouble(stars);
            reviews.add(new Review(title, starValue,
                    reviewText, criticName));
        }
    }

    static class PredictionModel {
        private HashMap<String, Integer> alphaWords;
        private HashMap<String, Integer> betaWords;
        private HashMap<String, Integer> gammaWords;
        private HashMap<String, Integer> deltaWords;

        private ArrayList<Review> trainingSet;

        private int numberOfAlphaReviews;
        private int numberOfBetaReviews;
        private int numberOfDeltaReviews;
        private int numberOfGammaReviews;

        private int[] referenceFeatureAlpha;
        private int[] referenceFeatureBeta;
        private int[] referenceFeatureGamma;
        private int[] referenceFeatureDelta;

        private int averageAlphaDots;
        private int averageBetaDots;
        private int averageGammaDots;
        private int averageDeltaDots;

        private int averageAlphaCommas;
        private int averageBetaCommas;
        private int averageGammaCommas;
        private int averageDeltaCommas;

        private final int NUMBER_OF_REFERENCE_FEATURES = 10;

        public PredictionModel(ArrayList<Review> trainingSet) {
            alphaWords = new HashMap<>();
            betaWords = new HashMap<>();
            gammaWords = new HashMap<>();
            deltaWords = new HashMap<>();
            this.trainingSet = trainingSet;

            referenceFeatureAlpha = new int[NUMBER_OF_REFERENCE_FEATURES];
            referenceFeatureBeta = new int[NUMBER_OF_REFERENCE_FEATURES];
            referenceFeatureGamma = new int[NUMBER_OF_REFERENCE_FEATURES];
            referenceFeatureDelta = new int[NUMBER_OF_REFERENCE_FEATURES];
        }

        private void calculateIngReferenceFeatures() {
            referenceFeatureAlpha[9] = 0;
            referenceFeatureBeta[9] = 0;
            referenceFeatureGamma[9] = 0;
            referenceFeatureDelta[9] = 0;

            for (Review review : this.trainingSet) {
                if (review.criticName.equals("Alpha"))
                    referenceFeatureAlpha[9]
                            += numberOfIngInText(review);
                else if (review.criticName.equals("Beta"))
                    referenceFeatureBeta[9] +=
                            numberOfIngInText(review);
                else if (review.criticName.equals("Gamma"))
                    referenceFeatureGamma[9] +=
                            numberOfIngInText(review);
                else if (review.criticName.equals("Delta"))
                    referenceFeatureDelta[9] +=
                            numberOfIngInText(review);
            }

            referenceFeatureAlpha[9] /= numberOfAlphaReviews;
            referenceFeatureBeta[9] /= numberOfBetaReviews;
            referenceFeatureGamma[9] /= numberOfGammaReviews;
            referenceFeatureDelta[9] /= numberOfDeltaReviews;
        }

        private int numberOfIngInText(Review review) {
            String text = review.reviewText;
            String[] words = text.split(" ");
            int ingCounter = 0;
            for (String word : words) {
                if (word.contains("ing"))
                    ingCounter++;
            }
            return ingCounter;
        }

        private void countAverageDotsAndCommas() {
            int totalAlphaDots = 0; int totalBetaDots = 0;
            int totalGammaDots = 0; int totalDeltaDots = 0;
            int totalAlphaCommas = 0; int totalBetaCommas = 0;
            int totalGammaCommas = 0; int totalDeltaCommas = 0;
            int totalDots = 0; int totalCommas = 0;

            for (Review review : this.trainingSet) {
                if (review.criticName.equals("Alpha")) {
                    totalAlphaCommas += review.numCommas;
                    totalAlphaDots += review.numDots;
                    totalCommas += review.numCommas;
                    totalDots += review.numDots;
                } else if (review.criticName.equals("Beta")) {
                    totalBetaCommas += review.numCommas;
                    totalBetaDots += review.numDots;
                    totalCommas += review.numCommas;
                    totalDots += review.numDots;
                } else if (review.criticName.equals("Gamma")) {
                    totalGammaCommas += review.numCommas;
                    totalGammaDots += review.numDots;
                    totalCommas += review.numCommas;
                    totalDots += review.numDots;
                } else if (review.criticName.equals("Delta")) {
                    totalDeltaCommas += review.numCommas;
                    totalDeltaDots += review.numDots;
                    totalCommas += review.numCommas;
                    totalDots += review.numDots;
                }
            }

            System.out.println("Total Dots: " + totalDots);
            System.out.println("Total Commas: " + totalCommas);
            System.out.println("Total Alpha Dots: " + totalAlphaDots);
            System.out.println("Total Alpha Commas: " + totalAlphaCommas);

            averageAlphaDots = totalAlphaDots / numberOfAlphaReviews;
            averageBetaDots = totalBetaDots / numberOfBetaReviews;
            averageGammaDots = totalGammaDots / numberOfGammaReviews;
            averageDeltaDots = totalDeltaDots / numberOfDeltaReviews;

            averageAlphaCommas = totalAlphaCommas / numberOfAlphaReviews;
            averageBetaCommas = totalBetaCommas / numberOfBetaReviews;
            averageGammaCommas = totalGammaCommas / numberOfGammaReviews;
            averageDeltaCommas = totalDeltaCommas / numberOfDeltaReviews;

            System.out.println("Avg Alpha Commas: " + averageAlphaCommas);
            System.out.println("Avg Alpha Dots: " + averageAlphaDots);
        }

        public String predict(Review review) throws Exception {
            String[] reviewWords = review.reviewText.split(" ");
            double[] featureVector = new double[NUMBER_OF_REFERENCE_FEATURES];
            double numThe = 0.0; double numAnd = 0.0; double numOf = 0.0;
            double numIt = 0.0; double numIs = 0.0; double numA = 0.0;
            double numTo = 0.0; double numAs = 0.0;

            for (String word : reviewWords) {
                if (word.equals("the"))
                    numThe += 1.0;
                else if (word.equals("and"))
                    numAnd += 1.0;
                else if (word.equals("of"))
                    numOf += 1.0;
                else if (word.equals("it"))
                    numIt += 1.0;
                else if (word.equals("is"))
                    numIs += 1.0;
                else if (word.equals("a"))
                    numA += 1.0;
                else if (word.equals("to"))
                    numTo += 1.0;
                else if (word.equals("as"))
                    numAs += 1.0;
            }

            featureVector[0] = numThe; featureVector[1] = numAnd;
            featureVector[2] = numOf; featureVector[3] = numIt;
            featureVector[4] = numIs; featureVector[5] = numA;
            featureVector[6] = numTo;

            featureVector[7] = review.numDots;
            featureVector[8] = review.numCommas;
            featureVector[9] = numberOfIngInText(review);

            //System.out.println();
            //for (double v : featureVector)
            //    System.out.print(v + " ");
            //System.out.println(review.criticName + "\n");

            double[] refAlpha = new double[NUMBER_OF_REFERENCE_FEATURES];
            double[] refBeta = new double[NUMBER_OF_REFERENCE_FEATURES];
            double[] refGamma = new double[NUMBER_OF_REFERENCE_FEATURES];
            double[] refDelta = new double[NUMBER_OF_REFERENCE_FEATURES];

            for (int i = 0; i < NUMBER_OF_REFERENCE_FEATURES; i++)
                refAlpha[i] = (double) referenceFeatureAlpha[i];
            for (int i = 0; i < NUMBER_OF_REFERENCE_FEATURES; i++)
                refBeta[i] = (double) referenceFeatureBeta[i];
            for (int i = 0; i < NUMBER_OF_REFERENCE_FEATURES; i++)
                refGamma[i] = (double) referenceFeatureGamma[i];
            for (int i = 0; i < NUMBER_OF_REFERENCE_FEATURES; i++)
                refDelta[i] = (double) referenceFeatureDelta[i];

            HashMap<String, Double> similarities = new HashMap<>();

            similarities.put("Alpha", distance(featureVector, refAlpha));
            similarities.put("Beta", distance(featureVector, refBeta));
            similarities.put("Gamma", distance(featureVector, refGamma));
            similarities.put("Delta", distance(featureVector, refDelta));

            similarities = sortMapDouble(similarities);

            Object[] result = similarities.keySet().toArray();

            String prediction = result[0].toString();

            return prediction;
        }

        public void train() {
            populateCriticWordMaps();
            System.out.println("Maps have been populated");

            alphaWords = sortMap(alphaWords);
            betaWords = sortMap(betaWords);
            gammaWords = sortMap(gammaWords);
            deltaWords = sortMap(deltaWords);

            Object[] commonAlphaWords = alphaWords.keySet().toArray();
            Object[] commonBetaWords = betaWords.keySet().toArray();
            Object[] commonGammaWords = gammaWords.keySet().toArray();
            Object[] commonDeltaWords = deltaWords.keySet().toArray();

            int outputLength = 31;

            System.out.println("Maps have been sorted");

            System.out.println();
            System.out.println("ALPHA");

            for (int i = commonAlphaWords.length-1;
                 i > commonAlphaWords.length-outputLength; i--) {
                System.out.println(commonAlphaWords[i].toString() + " "
                        + alphaWords.get(commonAlphaWords[i]
                                .toString()));
            }

            System.out.println();
            System.out.println("BETA");

            for (int i = commonBetaWords.length-1;
                 i > commonBetaWords.length-outputLength; i--) {
                System.out.println(commonBetaWords[i].toString() + " "
                        + betaWords.get(commonBetaWords[i]
                        .toString()));
            }

            System.out.println();
            System.out.println("GAMMA");

            for (int i = commonGammaWords.length-1;
                 i > commonGammaWords.length-outputLength; i--) {
                System.out.println(commonGammaWords[i].toString() + " "
                        + gammaWords.get(commonGammaWords[i]
                        .toString()));
            }

            System.out.println();
            System.out.println("DELTA");

            for (int i = commonDeltaWords.length-1;
                 i > commonDeltaWords.length-outputLength; i--) {
                System.out.println(commonDeltaWords[i].toString() + " "
                        + deltaWords.get(commonDeltaWords[i]
                        .toString()));
            }

            countReviewsByCritic();

            System.out.println();

            System.out.println("Alpha: " + numberOfAlphaReviews);
            System.out.println("Beta: " + numberOfBetaReviews);
            System.out.println("Delta: " + numberOfDeltaReviews);
            System.out.println("Gamma: " + numberOfGammaReviews);
            System.out.println("----------------------------");
            System.out.println("Total: " + this.trainingSet.size());

            referenceFeatureAlpha[0] = alphaWords.get("the") / numberOfAlphaReviews;
            referenceFeatureBeta[0] = betaWords.get("the") / numberOfBetaReviews;
            referenceFeatureGamma[0] = gammaWords.get("the") / numberOfGammaReviews;
            referenceFeatureDelta[0] = deltaWords.get("the") / numberOfDeltaReviews;

            referenceFeatureAlpha[1] = alphaWords.get("and") / numberOfAlphaReviews;
            referenceFeatureBeta[1] = betaWords.get("and") / numberOfBetaReviews;
            referenceFeatureGamma[1] = gammaWords.get("and") / numberOfGammaReviews;
            referenceFeatureDelta[1] = deltaWords.get("and") / numberOfDeltaReviews;

            referenceFeatureAlpha[2] = alphaWords.get("of") / numberOfAlphaReviews;
            referenceFeatureBeta[2] = betaWords.get("of") / numberOfBetaReviews;
            referenceFeatureGamma[2] = gammaWords.get("of") / numberOfGammaReviews;
            referenceFeatureDelta[2] = deltaWords.get("of") / numberOfDeltaReviews;

            referenceFeatureAlpha[3] = alphaWords.get("it") / numberOfAlphaReviews;
            referenceFeatureBeta[3] = betaWords.get("it") / numberOfBetaReviews;
            referenceFeatureGamma[3] = gammaWords.get("it") / numberOfGammaReviews;
            referenceFeatureDelta[3] = deltaWords.get("it") / numberOfDeltaReviews;

            referenceFeatureAlpha[4] = alphaWords.get("is") / numberOfAlphaReviews;
            referenceFeatureBeta[4] = betaWords.get("is") / numberOfBetaReviews;
            referenceFeatureGamma[4] = gammaWords.get("is") / numberOfGammaReviews;
            referenceFeatureDelta[4] = deltaWords.get("is") / numberOfDeltaReviews;

            referenceFeatureAlpha[5] = alphaWords.get("a") / numberOfAlphaReviews;
            referenceFeatureBeta[5] = betaWords.get("a") / numberOfBetaReviews;
            referenceFeatureGamma[5] = gammaWords.get("a") / numberOfGammaReviews;
            referenceFeatureDelta[5] = deltaWords.get("a") / numberOfDeltaReviews;

            referenceFeatureAlpha[6] = alphaWords.get("to") / numberOfAlphaReviews;
            referenceFeatureBeta[6] = betaWords.get("to") / numberOfBetaReviews;
            referenceFeatureGamma[6] = gammaWords.get("to") / numberOfGammaReviews;
            referenceFeatureDelta[6] = deltaWords.get("to") / numberOfDeltaReviews;

            System.out.println();
            System.out.println("Done populating reference features.");
            System.out.println();

            countAverageDotsAndCommas();

            System.out.println("Counted average number of " +
                    "dots and commas per critic.\n");

            referenceFeatureAlpha[7] = averageAlphaDots;
            referenceFeatureBeta[7] = averageBetaDots;
            referenceFeatureGamma[7] = averageGammaDots;
            referenceFeatureDelta[7] = averageDeltaDots;

            referenceFeatureAlpha[8] = averageAlphaCommas;
            referenceFeatureBeta[8] = averageBetaCommas;
            referenceFeatureGamma[8] = averageGammaCommas;
            referenceFeatureDelta[8] = averageDeltaCommas;

            calculateIngReferenceFeatures();

            for (int v : referenceFeatureAlpha)
                System.out.print(v + " ");
            System.out.println();
            for (int v : referenceFeatureBeta)
                System.out.print(v + " ");
            System.out.println();
            for (int v : referenceFeatureGamma)
                System.out.print(v + " ");
            System.out.println();
            for (int v : referenceFeatureDelta)
                System.out.print(v + " ");
            System.out.println();
        }

        private void countReviewsByCritic() {
            for (Review review : this.trainingSet) {
                if (review.criticName.equals("Alpha"))
                    numberOfAlphaReviews++;
                else if (review.criticName.equals("Beta"))
                    numberOfBetaReviews++;
                else if (review.criticName.equals("Delta"))
                    numberOfDeltaReviews++;
                else if (review.criticName.equals("Gamma"))
                    numberOfGammaReviews++;
            }
        }

        private void populateCriticWordMaps() {
            for (Review review : this.trainingSet) {
                String[] reviewWords = review.reviewText.split(" ");
                for (String reviewWord : reviewWords) {
                    if (review.criticName.equals("Alpha")) {
                        if (alphaWords.containsKey(reviewWord)) {
                            int count = alphaWords.get(reviewWord);
                            int incremented = (count + 1);
                            alphaWords.replace(reviewWord,
                                    count, incremented);
                        } else {
                            alphaWords.put(reviewWord, 1);
                        }
                    } else if (review.criticName.equals("Beta")) {
                        if (betaWords.containsKey(reviewWord)) {
                            int count = betaWords.get(reviewWord);
                            int incremented = (count + 1);
                            betaWords.replace(reviewWord,
                                    count, incremented);
                        } else {
                            betaWords.put(reviewWord, 1);
                        }
                    } else if (review.criticName.equals("Gamma")) {
                        if (gammaWords.containsKey(reviewWord)) {
                            int count = gammaWords.get(reviewWord);
                            int incremented = (count + 1);
                            gammaWords.replace(reviewWord,
                                    count, incremented);
                        } else {
                            gammaWords.put(reviewWord, 1);
                        }
                    } else if (review.criticName.equals("Delta")) {
                        if (deltaWords.containsKey(reviewWord)) {
                            int count = deltaWords.get(reviewWord);
                            int incremented = (count + 1);
                            deltaWords.replace(reviewWord,
                                    count, incremented);
                        } else {
                            deltaWords.put(reviewWord, 1);
                        }
                    }
                }
            }
        }

        private HashMap<String, Double>
        sortMapDouble(HashMap<String, Double> unsortedMap) {
            HashMap<String, Double> sortedMap =
                    unsortedMap.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue())
                            .collect(Collectors.toMap(Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    (e1, e2) -> e1, LinkedHashMap::new));
            return sortedMap;
        }

        private HashMap<String, Integer>
            sortMap(HashMap<String, Integer> unsortedMap) {
            HashMap<String, Integer> sortedMap =
                    unsortedMap.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue())
                            .collect(Collectors.toMap(Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    (e1, e2) -> e1, LinkedHashMap::new));
            return sortedMap;
        }
    }

    static class Review {
        private String movieTitle;
        private double starValue;
        private String reviewText;
        private String criticName;
        private int numDots;
        private int numCommas;

        public Review(String movieTitle, double starValue,
                      String reviewText, String criticName) {
            this.movieTitle = movieTitle;
            this.starValue = starValue;
            this.reviewText = reviewText;
            this.criticName = criticName;
            numDots = 0;
            numCommas = 0;
        }
    }
}
