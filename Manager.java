package Coursework1;

/**
 * Manager class
 * This class acts as the controller for the quiz competition.
 * It retrieves competitor data from the database and provides
 * methods to generate reports such as leaderboards and top performers.
 */
public class Manager {

    // Storing the list of competitors retrieved from the database
    private CompetitorList competitorList;

    /**
     * Constructor.
     * Retrieves all competitors from the database and
     * initialises the CompetitorList.
     */
    public Manager() {
        // Fetching all competitors from the database
        QuizCompetitor[] all = DatabaseHandler.getAllCompetitors();
        competitorList = new CompetitorList(all);
    }

    /**
     * Displays the complete leaderboard in the console,
     * including individual scores and score frequencies.
     */
    public void showLeaderboard() {
        System.out.println("\n LEADERBOARD ");
        competitorList.displayLeaderboard();
        competitorList.displayScoreFrequencies();
    }

    /**
     * Displays details of the top-performing competitor
     * based on the highest overall score.
     */
    public void showTopPerformer() {
        QuizCompetitor top = competitorList.getTopPerformer();
        if (top != null) {
            System.out.println("\n TOP PERFORMER");
            System.out.println(top.getFullDetails());
        } else {
            System.out.println("No competitors found.");
        }
    }

    /**
     * Displays the short details of a competitor
     * using their unique competitor ID.
     *
     * @param id the competitor ID to search for
     */
    public void showCompetitorByID(int id) {
        for (QuizCompetitor c : competitorList.getCompetitors()) {
            if (c.getCompetitorID() == id) {
                System.out.println(c.getShortDetails());
                return;
            }
        }
        System.out.println("Competitor ID " + id + " not found.");
    }

    /**
     * Returns the competitor list.
     * This method is primarily used by the GUI layer.
     *
     * @return CompetitorList object containing all competitors
     */
    public CompetitorList getCompetitorList() {
        return competitorList;
    }

    /**
     * Main method for testing the Manager class independently.
     * Demonstrates leaderboard and competitor report functionality.
     */
    public static void main(String[] args) {

        Manager m = new Manager();

        // Displaying the full leaderboard
        m.showLeaderboard();

        // Displaying the top-performing competitor
        m.showTopPerformer();

        // Displaying details of a competitor by ID (just example)
        m.showCompetitorByID(101);
    }
}
