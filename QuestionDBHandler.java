package Coursework1;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class QuestionDBHandler {

    private static final String URL = "jdbc:mysql://localhost:3306/CompetitionDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // ✅ NEW METHOD: Get questions by level and shuffle
    public static Question[] getQuestionsByLevel(String level) {

        ArrayList<Question> list = new ArrayList<>();

        String sql = "SELECT * FROM Questions WHERE level = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, level);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                String[] options = {
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4")
                };

                int correct = rs.getInt("correctOption");

                list.add(new Question(
                        rs.getString("questionText"),
                        options,
                        correct
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // ✅ Shuffle questions every time
        Collections.shuffle(list);

        return list.toArray(new Question[0]);
    }
}
