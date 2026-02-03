package Coursework1;
import java.sql.*;
import java.util.ArrayList;

/**
 * Handles all database operations for the Quiz Competition System.
 * This class is responsible for saving competitors and
 * retrieving competitor records from the database.
 */
public class DatabaseHandler {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/CompetitionDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Saves a QuizCompetitor object into the database.
     * Also retrieves and assigns the auto-generated competitor ID.
     *
     * @param c the QuizCompetitor to be saved
     */
    public static void saveCompetitor(QuizCompetitor c) {

        // SQL query to insert competitor details into the database
        String sql = """
            INSERT INTO competitors
            (firstName, lastName, level, country, score1, score2, score3, score4, score5)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        // Try-with-resources ensures the connection is closed automatically
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Setting personal details
            pstmt.setString(1, c.getName().getFirstName());
            pstmt.setString(2, c.getName().getLastName());
            pstmt.setString(3, c.getLevel());
            pstmt.setString(4, c.getCountry());

            // Setting quiz scores
            int[] scores = c.getScoreArray();
            for (int i = 0; i < 5; i++) {
                pstmt.setInt(5 + i, scores[i]);
            }

            // Executing insert operation
            pstmt.executeUpdate();

            // Retrieving auto-generated competitor ID and assign it to the object
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                c.setCompetitorID(keys.getInt(1));
            }

        } catch (SQLException e) {
            // TO Print SQL error details 
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all competitors stored in the database.
     *
     * @return an array of QuizCompetitor objects
     */
    public static QuizCompetitor[] getAllCompetitors() {

        // List to store competitors fetched from the database
        ArrayList<QuizCompetitor> list = new ArrayList<>();

        // Making connection and execute SELECT query
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM competitors")) {

            // Processing each record returned from the database
            while (rs.next()) {

                // Creating Name object using database values
                Name n = new Name(
                        rs.getString("firstName"),
                        rs.getString("lastName")
                );

                // Retrieving individual quiz scores
                int[] scores = new int[5];
                for (int i = 0; i < 5; i++) {
                    scores[i] = rs.getInt("score" + (i + 1));
                }

                // Creating QuizCompetitor object and adding it to the list
                list.add(new QuizCompetitor(
                        rs.getInt("competitorID"),
                        n,
                        rs.getString("level"),
                        rs.getString("country"),
                        scores
                ));
            }

        } catch (SQLException e) {
            // Handling database access errors
            e.printStackTrace();
        }

        // Converting ArrayList to array before returning
        return list.toArray(new QuizCompetitor[0]);
    }
}
