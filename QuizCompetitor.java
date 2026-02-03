package Coursework1;

/**
 * Represents a competitor participating in the quiz competition.
 * This class stores personal details, individual section scores,
 * and provides methods to calculate overall performance.
 */
public class QuizCompetitor {

    // Unique identifier for the competitor (database primary key)
    private int competitorID;

    // Name object storing first and last name
    private Name name;

    // Difficulty level selected by the competitor (Beginner / Intermediate / Advanced)
    private String level;

    // Country of the competitor
    private String country;

    // Array storing scores for the 5 quiz sections
    private int[] scores;

    /**
     * Constructor to create a QuizCompetitor object.
     *
     * @param competitorID unique ID of the competitor
     * @param name         Name object containing first and last name
     * @param level        quiz difficulty level
     * @param country      competitor's country
     * @param scores       array of section scores
     */
    public QuizCompetitor(int competitorID, Name name, String level, String country, int[] scores) {
        this.competitorID = competitorID;
        this.name = name;
        this.level = level;
        this.country = country;
        this.scores = scores;
    }

    /**
     * Returns the competitor ID.
     *
     * @return competitor ID
     */
    public int getCompetitorID() {
        return competitorID;
    }

    /**
     * Sets the competitor ID.
     * Used after saving to the database when the ID is auto-generated.
     *
     * @param competitorID generated database ID
     */
    public void setCompetitorID(int competitorID) {
        this.competitorID = competitorID;
    }

    /**
     * Returns the competitor's name.
     *
     * @return Name object
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the selected quiz level.
     *
     * @return level as a String
     */
    public String getLevel() {
        return level;
    }

    /**
     * Returns the competitor's country.
     *
     * @return country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the array of section scores.
     *
     * @return score array
     */
    public int[] getScoreArray() {
        return scores;
    }

    /**
     * Calculates the overall score.
     * The average score is weighted based on difficulty level.
     *
     * Beginner      70% weighting
     * Intermediate  80% weighting
     * Advanced      90% weighting
     *
     * @return weighted overall score
     */
    public double getOverallScore() {
        int total = 0;
        for (int s : scores) {
            total += s;
        }

        double average = total / 5.0;

        if (level.equalsIgnoreCase("Beginner")) return average * 0.7;
        if (level.equalsIgnoreCase("Intermediate")) return average * 0.8;
        if (level.equalsIgnoreCase("Advanced")) return average * 0.9;

        return average;
    }

    /**
     * Returns a detailed textual description of the competitor.
     * Used for full report or dialog display.
     *
     * @return full details string
     */
    public String getFullDetails() {
        return "Competitor number " + competitorID +
                ", name " + name.getFullName() +
                ", country " + country + ".\n" +
                name.getFirstName() + " is a " + level +
                " and has an overall score of " + getOverallScore() + ".";
    }

    /**
     * Returns a short summary of the competitor.
     * Used in leaderboard or brief listings.
     *
     * @return short details string
     */
    public String getShortDetails() {
        return "CN " + competitorID +
                " (" + name.getInitials() + ") has overall score " +
                getOverallScore() + ".";
    }
}
