package Coursework1;

import java.sql.*;
import java.util.ArrayList;


public class QuestionDBHandler {
	private static final String URL = "jdbc:mysql://localhost:3306/CompetitionDB";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static Question[] getQuestionsByLevel(String level) {

	    ArrayList<Question> questions = new ArrayList<>();

	    String sql = "SELECT * FROM Questions WHERE level = ? LIMIT 25";

	    try {
	        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	        PreparedStatement stmt = conn.prepareStatement(sql);

	        stmt.setString(1, level);

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {

	            String[] options = new String[4];
	            options[0] = rs.getString("option1");
	            options[1] = rs.getString("option2");
	            options[2] = rs.getString("option3");
	            options[3] = rs.getString("option4");

	            int correct = rs.getInt("correctOption");

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