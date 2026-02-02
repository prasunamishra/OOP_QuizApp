package Coursework1;

import javax.swing.*;
import java.awt.*;

public class QuizApp {

    private JFrame frame;
    private JPanel panel;
    private QuizCompetitor currentUser;

    private Question[] questions;
    private int[] answers;
    private int currentQuestion = 0;

    public QuizApp() {
<<<<<<< HEAD
        showLoginWindow();   // Do NOT load questions here
    }

    //  Load questions AFTER login, based on level
    private void loadQuestionsFromDB(String level) {

        questions = QuestionDBHandler.getQuestionsByLevel(level);

        if (questions.length != 25) {
            JOptionPane.showMessageDialog(
                    null,
                    "ERROR: Exactly 25 questions required for level "
                            + level + ". Found: " + questions.length
            );
            System.exit(0);
        }

        answers = new int[questions.length];
    }

    // ================= LOGIN WINDOW =================
=======
        
        showLoginWindow();
    }

    // 1. LOGIN WINDOW
  
>>>>>>> 7614aa90571b065a38862d7343329bf41aa0e527
    private void showLoginWindow() {
    	
        frame = new JFrame("Quiz App");
        frame.setSize(350, 220);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nameField = new JTextField();
        JTextField countryField = new JTextField();
        JComboBox<String> levelBox =
                new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"});

        JButton loginBtn = new JButton("Login");

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Country:"));
        panel.add(countryField);
        panel.add(new JLabel("Level:"));
        panel.add(levelBox);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(loginBtn, BorderLayout.SOUTH);

        frame.setVisible(true);

        loginBtn.addActionListener(e -> {
        	
            String fullName = nameField.getText().trim();
            String country = countryField.getText().trim();
            String level = (String) levelBox.getSelectedItem();

            if (fullName.isEmpty() || country.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }

            String[] parts = fullName.split(" ");
            String first = parts[0];
            String last = parts.length >= 2 ? parts[parts.length - 1] : "";

            Name name = new Name(first, last);
            currentUser = new QuizCompetitor(0, name, level, country, new int[5]);

<<<<<<< HEAD
            // ✅ IMPORTANT
            loadQuestionsFromDB(level);
=======
            currentUser = new QuizCompetitor(0, name, level, country, scores);
         // LOAD QUESTIONS BASED ON LEVEL
            questions = QuestionDBHandler.getQuestionsByLevel(level);
>>>>>>> 7614aa90571b065a38862d7343329bf41aa0e527

            if (questions.length != 25) {
                JOptionPane.showMessageDialog(
                    null,
                    "ERROR: 25 questions required for " + level
                );
                System.exit(0);
            }

            answers = new int[questions.length];
            frame.dispose();
            showMainMenu();
        });
        
    }

    // ================= MAIN MENU =================
    private void showMainMenu() {
        frame = new JFrame("Quiz App");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel welcome = new JLabel(
                "Welcome " + currentUser.getName().getFirstName(),
                SwingConstants.CENTER
        );

        JButton startBtn = new JButton("Start");
        JButton leaderboardBtn = new JButton("Leaderboard");
        JButton detailsBtn = new JButton("Details");
        JButton quitBtn = new JButton("Quit");

        panel.add(welcome);
        panel.add(startBtn);
        panel.add(leaderboardBtn);
        panel.add(detailsBtn);
        panel.add(quitBtn);

        frame.add(panel);
        frame.setVisible(true);

        startBtn.addActionListener(e -> {
            currentQuestion = 0;
            answers = new int[questions.length];
            frame.dispose();
            showQuizWindow();
        });

        leaderboardBtn.addActionListener(e -> showLeaderboardWindow());

        detailsBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, currentUser.getFullDetails())
        );

        quitBtn.addActionListener(e -> System.exit(0));
    }

    // ================= QUIZ WINDOW =================
    private void showQuizWindow() {
        frame = new JFrame("Quiz App - Quiz");
        panel = new JPanel(new BorderLayout());

        Question q = questions[currentQuestion];

        JLabel qLabel = new JLabel("Q" + (currentQuestion + 1) + ": " + q.getQuestionText());
        panel.add(qLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        ButtonGroup group = new ButtonGroup();
        JRadioButton[] optionButtons = new JRadioButton[4];

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton(q.getOptions()[i]);
            group.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }

        panel.add(optionsPanel, BorderLayout.CENTER);

        JButton nextBtn = new JButton(
                currentQuestion == questions.length - 1 ? "Submit" : "Next"
        );
        panel.add(nextBtn, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setSize(600, 400);
        frame.setVisible(true);

        nextBtn.addActionListener(e -> {
            int selected = -1;
            for (int i = 0; i < 4; i++) {
                if (optionButtons[i].isSelected()) {
                    selected = i;
                    break;
                }
            }

            if (selected == -1) {
                JOptionPane.showMessageDialog(frame, "Please select an option!");
                return;
            }

            answers[currentQuestion] = selected;

            currentQuestion++;
            frame.dispose();

            if (currentQuestion < questions.length) {
                showQuizWindow();
            } else {
                calculateScores();
                JOptionPane.showMessageDialog(null, "Quiz Submitted!");
                showMainMenu();
            }
        });
    }

    // ================= SCORE CALCULATION =================
    private void calculateScores() {
        int[] finalScores = new int[5];

        for (int i = 0; i < 5; i++) {
            int correctCount = 0;
            for (int j = 0; j < 5; j++) {
                int idx = i * 5 + j;
                if (answers[idx] == questions[idx].getCorrectOption()) {
                    correctCount++;
                }
            }
            finalScores[i] = correctCount;
        }

        currentUser = new QuizCompetitor(
                0,
                currentUser.getName(),
                currentUser.getLevel(),
                currentUser.getCountry(),
                finalScores
        );

        DatabaseHandler.saveCompetitor(currentUser);
    }

    // ================= LEADERBOARD WINDOW (your original, fixed) =================
    private void showLeaderboardWindow() {

        QuizCompetitor[] all = DatabaseHandler.getAllCompetitors();
        CompetitorList list = new CompetitorList(all);

        // ================= TABLE =================
        String[] columns = {
                "ID", "Name", "Level", "Country",
                "Score1", "Score2", "Score3", "Score4", "Score5", "Overall"
        };

        Object[][] data = new Object[all.length][10];

        for (int i = 0; i < all.length; i++) {
            QuizCompetitor c = all[i];
            int[] s = c.getScoreArray();

            data[i][0] = c.getCompetitorID();
            data[i][1] = c.getName().getFullName();
            data[i][2] = c.getLevel();
            data[i][3] = c.getCountry();
            data[i][4] = s[0];
            data[i][5] = s[1];
            data[i][6] = s[2];
            data[i][7] = s[3];
            data[i][8] = s[4];
            data[i][9] = String.format("%.2f", c.getOverallScore());
        }

        JTable table = new JTable(data, columns);
        table.setFillsViewportHeight(true);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(850, 280));

        // ✅ CLICK ROW TO SEE DETAILS
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    QuizCompetitor selected = all[row];
                    JOptionPane.showMessageDialog(frame,
                            selected.getFullDetails(),
                            "Competitor Details",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // ================= FREQUENCY TABLE =================
        int[] freq = list.getScoreFrequencies();

        JPanel freqGrid = new JPanel(new GridLayout(2, 7, 8, 4));
        freqGrid.add(new JLabel("Score"));
        for (int i = 0; i <= 5; i++) {
            freqGrid.add(new JLabel(String.valueOf(i), SwingConstants.CENTER));
        }

        freqGrid.add(new JLabel("Count"));
        for (int i = 0; i <= 5; i++) {
            freqGrid.add(new JLabel(String.valueOf(freq[i]), SwingConstants.CENTER));
        }

        JPanel freqWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        freqWrapper.setBorder(BorderFactory.createTitledBorder("Score Frequency"));
        freqWrapper.add(freqGrid);

        // ================= TOP PANEL =================
        JButton backBtn = new JButton("Back");

        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(backBtn, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);

        // ================= MAIN PANEL =================
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tableScroll, BorderLayout.CENTER);
        mainPanel.add(freqWrapper, BorderLayout.SOUTH);

        // ================= DIALOG =================
        JDialog dialog = new JDialog(frame, "Leaderboard", true);
        dialog.setContentPane(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);

<<<<<<< HEAD
=======
        //  Back button logic
>>>>>>> 7614aa90571b065a38862d7343329bf41aa0e527
        backBtn.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizApp::new);
    }
}
