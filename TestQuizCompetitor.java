package Coursework1;

/**
 * TestQuizCompetitor is a simple test class for Part One and Part Two of the Quiz System.
 * This class demonstrates creating QuizCompetitor objects, 
 * printing their details, and saving/retrieving them from the database.
 */
public class TestQuizCompetitor {

    /**
     * Main method to run the test.
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {

        //  CREATE NAMES 
        // Create Name objects for three competitors
        Name name1 = new Name("Prasuna", "Mishra");
        Name name2 = new Name("Kushum", "Neupane");
        Name name3 = new Name("Manasvi", "Khanal");

        // CREATE SCORES 
        // Create arrays representing the scores for 5 rounds
        int[] scores1 = {4, 3, 5, 2, 4};
        int[] scores2 = {5, 5, 4, 4, 5};
        int[] scores3 = {3, 2, 4, 3, 3};

        // CREATE QUIZ COMPETITORS 
        // Construct QuizCompetitor objects with ID, Name, Level, Country, and scores
        QuizCompetitor c1 = new QuizCompetitor(100, name1, "Beginner", "UK", scores1);
        QuizCompetitor c2 = new QuizCompetitor(101, name2, "Intermediate", "USA", scores2);
        QuizCompetitor c3 = new QuizCompetitor(102, name3, "Advanced", "Canada", scores3);

        // TEST FULL DETAILS 
        // Print all details of each competitor
        System.out.println("FULL DETAILS:\n");
        System.out.println(c1.getFullDetails());
        System.out.println();
        System.out.println(c2.getFullDetails());
        System.out.println();
        System.out.println(c3.getFullDetails());

        //  TEST SHORT DETAILS
        // Print brief details (ID, Name, Level, Overall Score) for each competitor
        System.out.println("\nSHORT DETAILS:\n");
        System.out.println(c1.getShortDetails());
        System.out.println(c2.getShortDetails());
        System.out.println(c3.getShortDetails());

        // DATABASE TEST
        // Save competitors to the database
        DatabaseHandler.saveCompetitor(c1);
        DatabaseHandler.saveCompetitor(c2);
        DatabaseHandler.saveCompetitor(c3);

        // Retrieve all competitors from the database
        QuizCompetitor[] all = DatabaseHandler.getAllCompetitors();
        System.out.println("\nCOMPETITORS FROM DATABASE:\n");
        for (QuizCompetitor c : all) {
            System.out.println(c.getFullDetails());
        }
    }
}
