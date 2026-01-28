package Coursework1;


import java.util.*;

// This class manages a list of QuizCompetitor objects
public class CompetitorList {

    private List<QuizCompetitor> competitors;

    // Constructor - initially empty list
    public CompetitorList() {
        competitors = new ArrayList<>();
    }

    // Constructor - initialize from array
    public CompetitorList(QuizCompetitor[] array) {
        competitors = new ArrayList<>();
        for (QuizCompetitor c : array) {
            competitors.add(c);
        }
    }

    // Add a competitor to the list
    public void addCompetitor(QuizCompetitor c) {
        competitors.add(c);
    }

    // Get all competitors
    public List<QuizCompetitor> getCompetitors() {
        return competitors;
    }

    // Get top performer based on overall score
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

    // Get frequency of individual scores (0–5) across all competitors
    public int[] getScoreFrequencies() {
        int[] freq = new int[6]; // index 0–5

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

    // Display leaderboard (full details table)
    public void displayLeaderboard() {
        System.out.println("ID | Name | Level | Country | Score1 | Score2 | Score3 | Score4 | Score5 | OverallScore");
        System.out.println("-------------------------------------------------------------------------------");

        for (QuizCompetitor c : competitors) {
            int[] s = c.getScoreArray();
            System.out.printf("%d | %s | %s | %s | %d | %d | %d | %d | %d | %.2f\n",
                    c.getCompetitorID(),
                    c.getName().getFullName(),
                    c.getLevel(),
                    c.getCountry(),
                    s[0], s[1], s[2], s[3], s[4],
                    c.getOverallScore());
        }
    }

    // Display frequency table
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
