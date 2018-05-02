import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AtTheMovies {
    public static void main(String[] args) {
        try {
            handleTrainingData("train.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        ArrayList<Review> reviews = new ArrayList<>();

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

        for (Review review : reviews) {
            System.out.println("Title: " + review.movieTitle);
            System.out.println("Stars: " + review.starValue);
            System.out.println("Critic: " + review.criticName);
            System.out.println();
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
