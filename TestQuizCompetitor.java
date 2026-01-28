package Coursework1;

// TestQuizCompetitor.java
// This class is for testing Part One and Database (Part Two)

public class TestQuizCompetitor {

    public static void main(String[] args) {

        // Create Name objects
        Name name1 = new Name("Prasuna", "Mishra");
        Name name2 = new Name("Kushum", "Neupane");
        Name name3 = new Name("Manasvi", "Khanal");

        // Create score arrays
        int[] scores1 = {4, 3, 5, 2, 4};
        int[] scores2 = {5, 5, 4, 4, 5};
        int[] scores3 = {3, 2, 4, 3, 3};

        // Create QuizCompetitor objects
        QuizCompetitor c1 = new QuizCompetitor(100, name1, "Beginner", "UK", scores1);
        QuizCompetitor c2 = new QuizCompetitor(101, name2, "Intermediate", "USA", scores2);
        QuizCompetitor c3 = new QuizCompetitor(102, name3, "Advanced", "Canada", scores3);

        // Test getFullDetails()
        System.out.println("FULL DETAILS:\n");
        System.out.println(c1.getFullDetails());
        System.out.println();
        System.out.println(c2.getFullDetails());
        System.out.println();
        System.out.println(c3.getFullDetails());

        // Test getShortDetails()
        System.out.println("\nSHORT DETAILS:\n");
        System.out.println(c1.getShortDetails());
        System.out.println(c2.getShortDetails());
        System.out.println(c3.getShortDetails());

      
        // DATABASE TEST (NEW)
     

        // Save competitors to database
        DatabaseHandler.saveCompetitor(c1);
        DatabaseHandler.saveCompetitor(c2);
        DatabaseHandler.saveCompetitor(c3);

        // Retrieve all competitors from DB
        QuizCompetitor[] all = DatabaseHandler.getAllCompetitors();
        System.out.println("\nCOMPETITORS FROM DATABASE:\n");
        for (QuizCompetitor c : all) {
            System.out.println(c.getFullDetails());
        }
    }

}
