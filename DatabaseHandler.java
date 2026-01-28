package Coursework1;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    private static final String URL = "jdbc:mysql://localhost:3306/CompetitionDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void saveCompetitor(QuizCompetitor c) {

        String sql = """
            INSERT INTO competitors
            (firstName, lastName, level, country, score1, score2, score3, score4, score5)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, c.getName().getFirstName());
            pstmt.setString(2, c.getName().getLastName());
            pstmt.setString(3, c.getLevel());
            pstmt.setString(4, c.getCountry());

            int[] scores = c.getScoreArray();
            for (int i = 0; i < 5; i++) {
                pstmt.setInt(5 + i, scores[i]);
            }

            pstmt.executeUpdate();

            // ✅ ONLY ADDITION
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                c.setCompetitorID(keys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static QuizCompetitor[] getAllCompetitors() {

        ArrayList<QuizCompetitor> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM competitors")) {

            while (rs.next()) {

                Name n = new Name(
                        rs.getString("firstName"),
                        rs.getString("lastName")
                );

                int[] scores = new int[5];
                for (int i = 0; i < 5; i++) {
                    scores[i] = rs.getInt("score" + (i + 1));
                }

                list.add(new QuizCompetitor(
                        rs.getInt("competitorID"),
                        n,
                        rs.getString("level"),
                        rs.getString("country"),
                        scores
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list.toArray(new QuizCompetitor[0]);
    }
}
