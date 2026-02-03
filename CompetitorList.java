package Coursework1;
import java.util.*;

/**
 * Manages a collection of QuizCompetitor objects.
 * Provides functionality for adding competitors,
 * analysing scores, and displaying leaderboard information.
 */
public class CompetitorList {

    // List that stores all quiz competitors
    private List<QuizCompetitor> competitors;

    /**
     * Default constructor.
     * Initialises an empty list of competitors.
     */
    public CompetitorList() {
        competitors = new ArrayList<>();
    }

    /**
     * Constructor that initialises the list using an array.
     *
     * @param array array of QuizCompetitor objects
     */
    public CompetitorList(QuizCompetitor[] array) {
        competitors = new ArrayList<>();
        for (QuizCompetitor c : array) {
            competitors.add(c);
        }
    }

    /**
     * Adds a competitor to the list.
     *
     * @param c the QuizCompetitor to add
     */
    public void addCompetitor(QuizCompetitor c) {
        competitors.add(c);
    }

    /**
     * Returns the list of all competitors.
     *
     * @return list of QuizCompetitor objects
     */
    public List<QuizCompetitor> getCompetitors() {
        return competitors;
    }

    /**
     * Finds and returns the top-performing competitor
     * based on the highest overall score.
     *
     * @return the QuizCompetitor with the highest score,
     *         or null if the list is empty
     */
    public QuizCompetitor getTopPerformer() {
        if (competitors.isEmpty()) return null;

        QuizCompetitor top = competitors.get(0);
        for (QuizCompetitor c : competitors) {
            if (c.getOverallScore() > top.getOverallScore()) {
                top = c;
            }
        }
        return top;
    }

    /**
     * Calculates the frequency of individual scores (0–5)
     * across all competitors and all quiz questions.
     *
     * @return an array where index represents the score
     *         and value represents its frequency
     */
    public int[] getScoreFrequencies() {
        int[] freq = new int[6]; // Index represents scores 0–5

        for (QuizCompetitor c : competitors) {
            int[] scores = c.getScoreArray();
            for (int s : scores) {
                if (s >= 0 && s <= 5) {
                    freq[s]++;
                }
            }
        }
        return freq;
    }

    /**
     * Displays the leaderboard in the console.
     * Useful for debugging or console-based output.
     */
    public void displayLeaderboard() {
        System.out.println("ID | Name | Level | Country | Score1 | Score2 | Score3 | Score4 | Score5 | OverallScore");

        for (QuizCompetitor c : competitors) {
            int[] s = c.getScoreArray();
            System.out.printf(
                    "%d | %s | %s | %s | %d | %d | %d | %d | %d | %.2f\n",
                    c.getCompetitorID(),
                    c.getName().getFullName(),
                    c.getLevel(),
                    c.getCountry(),
                    s[0], s[1], s[2], s[3], s[4],
                    c.getOverallScore()
            );
        }
    }

    /**
     * Displays the frequency table of scores in the console.
     * Shows how many times each score (0–5) occurred.
     */
    public void displayScoreFrequencies() {
        int[] freq = getScoreFrequencies();

        System.out.println("\nScore Frequencies:");
        System.out.println("Score: 0 1 2 3 4 5");
        System.out.print("Count: ");

        for (int i = 0; i <= 5; i++) {
            System.out.print(freq[i] + " ");
        }
        System.out.println();
    }
}
