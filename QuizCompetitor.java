package Coursework1;

public class QuizCompetitor {

    private int competitorID;
    private Name name;
    private String level;
    private String country;
    private int[] scores;

    public QuizCompetitor(int competitorID, Name name, String level, String country, int[] scores) {
        this.competitorID = competitorID;
        this.name = name;
        this.level = level;
        this.country = country;
        this.scores = scores;
    }

    public int getCompetitorID() {
        return competitorID;
    }

    // ✅ ADDED (needed for DB auto-increment sync)
    public void setCompetitorID(int competitorID) {
        this.competitorID = competitorID;
    }

    public Name getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public String getCountry() {
        return country;
    }

    public int[] getScoreArray() {
        return scores;
    }

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

    public String getFullDetails() {
        return "Competitor number " + competitorID +
                ", name " + name.getFullName() +
                ", country " + country + ".\n" +
                name.getFirstName() + " is a " + level +
                " and has an overall score of " + getOverallScore() + ".";
    }

    public String getShortDetails() {
        return "CN " + competitorID +
                " (" + name.getInitials() + ") has overall score " +
                getOverallScore() + ".";
    }
}
