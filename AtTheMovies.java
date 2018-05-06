import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AtTheMovies {

    private static ArrayList<Review> reviews;
    private static ArrayList<Review> trainingSet;
    private static ArrayList<Review> testSet;

    private static final int TEST_SET_SIZE = 100;

    public static void main(String[] args) {
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

        test();

        System.out.println();

        for (Review review : trainingSet) {
            cleanReviewTextFromNonAlphCharacters(review);
        }

        for (Review review : trainingSet) {
            System.out.println("Title: " + review.movieTitle);
            System.out.println("Critic Name: " + review.criticName);
            System.out.println("Stars: " + review.starValue);
            System.out.println("Text: " + review.reviewText);
            System.out.println();
        }

        PredictionModel predictionModel = new PredictionModel(trainingSet);
        predictionModel.train();
    }

    private static void test() {
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

        public PredictionModel(ArrayList<Review> trainingSet) {
            alphaWords = new HashMap<>();
            betaWords = new HashMap<>();
            gammaWords = new HashMap<>();
            deltaWords = new HashMap<>();
            this.trainingSet = trainingSet;
        }

        public void train() {
            populateCriticWordMaps();
        }

        private void populateCriticWordMaps() {
            for (Review review : this.trainingSet) {
                String[] reviewWords = review.reviewText.split(" ");
                for (String reviewWord : reviewWords) {
                    if (review.criticName.equals("Alpha")) {
                        if (alphaWords.containsKey(reviewWord)) {
                            int count = alphaWords.get(reviewWord);
                            int incremented = count++;
                            alphaWords.replace(reviewWord,
                                    count, incremented);
                        } else {
                            alphaWords.put(reviewWord, 1);
                        }
                    } else if (review.criticName.equals("Beta")) {
                        if (betaWords.containsKey(reviewWord)) {
                            int count = betaWords.get(reviewWord);
                            int incremented = count++;
                            betaWords.replace(reviewWord,
                                    count, incremented);
                        } else {
                            betaWords.put(reviewWord, 1);
                        }
                    } else if (review.criticName.equals("Gamma")) {
                        if (gammaWords.containsKey(reviewWord)) {
                            int count = gammaWords.get(reviewWord);
                            int incremented = count++;
                            gammaWords.replace(reviewWord,
                                    count, incremented);
                        } else {
                            gammaWords.put(reviewWord, 1);
                        }
                    } else if (review.criticName.equals("Delta")) {
                        if (deltaWords.containsKey(reviewWord)) {
                            int count = deltaWords.get(reviewWord);
                            int incremented = count++;
                            deltaWords.replace(reviewWord,
                                    count, incremented);
                        } else {
                            deltaWords.put(reviewWord, 1);
                        }
                    }
                }
            }
            System.out.println("Maps have been populated");
        }
    }

    static class Review {
        private String movieTitle;
        private double starValue;
        private String reviewText;
        private String criticName;

        public Review(String movieTitle, double starValue,
                      String reviewText, String criticName) {
            this.movieTitle = movieTitle;
            this.starValue = starValue;
            this.reviewText = reviewText;
            this.criticName = criticName;
        }
    }
}
