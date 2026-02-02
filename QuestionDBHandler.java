package Coursework1;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;


public class QuestionDBHandler {
	private static final String URL = "jdbc:mysql://localhost:3306/CompetitionDB";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static Question[] getQuestionsByLevel(String level) {

<<<<<<< HEAD
    // ✅ NEW METHOD: Get questions by level and shuffle
    public static Question[] getQuestionsByLevel(String level) {
=======
	    ArrayList<Question> questions = new ArrayList<>();
>>>>>>> 7614aa90571b065a38862d7343329bf41aa0e527

	    String sql = "SELECT * FROM Questions WHERE level = ? LIMIT 25";

<<<<<<< HEAD
        String sql = "SELECT * FROM Questions WHERE level = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, level);

            ResultSet rs = pstmt.executeQuery();
=======
	    try {
	        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	        PreparedStatement stmt = conn.prepareStatement(sql);
>>>>>>> 7614aa90571b065a38862d7343329bf41aa0e527

	        stmt.setString(1, level);

	        ResultSet rs = stmt.executeQuery();

<<<<<<< HEAD
                int correct = rs.getInt("correctOption");
=======
	        while (rs.next()) {
>>>>>>> 7614aa90571b065a38862d7343329bf41aa0e527

	            String[] options = new String[4];
	            options[0] = rs.getString("option1");
	            options[1] = rs.getString("option2");
	            options[2] = rs.getString("option3");
	            options[3] = rs.getString("option4");

	            int correct = rs.getInt("correctOption");

<<<<<<< HEAD
        // ✅ Shuffle questions every time
        Collections.shuffle(list);

        return list.toArray(new Question[0]);
    }
}
=======
	            Question q = new Question(
	                    rs.getString("questionText"),
	                    options,
	                    correct
	            );

	            questions.add(q);
	        }

	        conn.close(); // close connection

	    } catch (Exception e) {
	        System.out.println("Error loading questions");
	    }

	    return questions.toArray(new Question[0]);
	}
}
>>>>>>> 7614aa90571b065a38862d7343329bf41aa0e527
