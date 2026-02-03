package Coursework1;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Handles database operations related to quiz questions.
 * This class retrieves questions from the database
 * based on the selected difficulty level.
 */
public class QuestionDBHandler {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/CompetitionDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Retrieves all quiz questions for a given level from the database.
     *
     * @param level the difficulty level (e.g., Beginner, Intermediate, Advanced)
     * @return an array of Question objects shuffled into random order
     */
    public static Question[] getQuestionsByLevel(String level) {

        // Dynamic list to store questions retrieved from the database
        ArrayList<Question> questions = new ArrayList<>();

        // SQL query to select questions for the specified level
        String sql = "SELECT * FROM Questions WHERE level = ?";

        try (
            // Establish database connection
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prepare SQL statement with parameter
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            // Set the level parameter
            stmt.setString(1, level);

            // Execute query
            ResultSet rs = stmt.executeQuery();

            // Process each result row
            while (rs.next()) {

                // Retrieve answer options from the database
                String[] options = {
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4")
                };

                // Retrieve index of correct option (0–3)
                int correct = rs.getInt("correctOption");

                // Create Question object
                Question q = new Question(
                        rs.getString("questionText"),
                        options,
                        correct
                );

                // Add question to the list
                questions.add(q);
            }

        } catch (Exception e) {
            // Handle database or SQL errors
            System.out.println("Error loading questions");
            e.printStackTrace();
        }

        // Randomise question order before returning
        Collections.shuffle(questions);

        // Convert ArrayList to array
        return questions.toArray(new Question[0]);
    }
}
