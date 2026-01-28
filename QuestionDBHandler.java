package Coursework1;

import java.sql.*;
import java.util.ArrayList;

public class QuestionDBHandler {

    private static final String URL = "jdbc:mysql://localhost:3306/CompetitionDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Question[] getAllQuestions() {

        ArrayList<Question> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Questions")) {

            while (rs.next()) {

                String[] options = {
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4")
                };

             // CORRECT: DB already stores 0–3
                int correct = rs.getInt("correctOption") ;

                list.add(new Question(
                        rs.getString("questionText"),
                        options,
                        correct
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list.toArray(new Question[0]);
    }
}
