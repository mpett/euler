import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AtTheMovies {

    private static ArrayList<Review> reviews;

    public static void main(String[] args) {
        reviews = new ArrayList<>();

        try {
            handleTrainingData("train.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Review review : reviews) {
            cleanReviewTextFromNonAlphCharacters(review);

            System.out.println("Title: " + review.movieTitle);
            System.out.println("Stars: " + review.starValue);
            System.out.println("Critic: " + review.criticName);
            System.out.println("Review: " + review.reviewText);
            System.out.println();
        }
    }

    private static void cleanReviewTextFromNonAlphCharacters(Review review) {
        String reviewText = review.reviewText;
        StringBuilder stringBuilder = new StringBuilder();

        for (int characterIndex = 0; characterIndex
                < reviewText.length(); characterIndex++) {
            char currentCharacter = reviewText.charAt(characterIndex);
            if (currentCharacter >= 65 && currentCharacter <= 90)
                stringBuilder.append(currentCharacter);
            if (currentCharacter >= 97 && currentCharacter <= 122)
                stringBuilder.append(currentCharacter);
            if (currentCharacter == 32)
                stringBuilder.append(currentCharacter);
        }

        reviewText = stringBuilder.toString();
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
            String[] reviewElements = review.split("\", \"");
            String title = reviewElements[0].substring(11);
            String[] reviewAndStars =
                    reviewElements[1].split("\"Review\"");
            String stars = reviewAndStars[0].substring(8, 11);
            String reviewText = reviewAndStars[1].substring(3);
            String criticName = reviewElements[2]
                    .substring(10, reviewElements[2].length()-1);
            double starValue = Double.parseDouble(stars);
            reviews.add(new Review(title, starValue, reviewText, criticName));
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
