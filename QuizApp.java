package Coursework1;

/**
 * Controls the quiz flow for a single user.
 * This class manages login, question progression,
 * answer submission, score calculation, and saving
 * the final result to the database.
 */
public class QuizApp {

    // The currently logged-in competitor
    private QuizCompetitor currentUser;

    // Array of questions loaded for the selected level
    private Question[] questions;

    // Stores the user's selected answers (by index)
    private int[] answers;

    // Tracks the current question number (0-based)
    private int currentIndex;

    //  LOGIN

    /**
     * Logs a user into the quiz and initialises the quiz state.
     *
     * @param name    competitor's name
     * @param level   selected quiz difficulty level
     * @param country competitor's country
     */
    public void login(Name name, String level, String country) {

        // Create a new competitor with empty scores
        currentUser = new QuizCompetitor(
                0,
                name,
                level,
                country,
                new int[5]
        );

        // Load questions from the database based on level
        questions = QuestionDBHandler.getQuestionsByLevel(level);

        /*
         * Fallback mechanism:
         * If the database returns fewer than 25 questions,
         * placeholder questions are generated to ensure
         * the quiz and GUI continue functioning.
         */
        if (questions == null || questions.length < 25) {
            questions = generateFallbackQuestions(25);
            System.out.println("Warning: Using fallback questions due to insufficient DB data.");
        }

        // Enforce fixed quiz length
        if (questions.length != 25) {
            throw new IllegalStateException(
                    "Exactly 25 questions required for level " + level
            );
        }

        // Prepare answer storage and reset progress
        answers = new int[questions.length];
        currentIndex = 0;
    }

    /**
     * Generates placeholder questions if database data is unavailable.
     *
     * @param count number of fallback questions to create
     * @return array of fallback Question objects
     */
    private Question[] generateFallbackQuestions(int count) {

        Question[] fallback = new Question[count];

        for (int i = 0; i < count; i++) {
            String[] opts = {"Option A", "Option B", "Option C", "Option D"};

            fallback[i] = new Question(
                "Fallback Question " + (i + 1) + ": This is a placeholder question.",
                opts,
                0 // Option A is always correct for fallback questions
            );
        }

        return fallback;
    }

//Quiz

    /**
     * Returns the current question to be displayed.
     *
     * @return current Question object
     */
    public Question getCurrentQuestion() {
        return questions[currentIndex];
    }

    /**
     * Returns the current question number (1-based).
     *
     * @return question number
     */
    public int getQuestionNumber() {
        return currentIndex + 1;
    }

    /**
     * Returns the total number of questions in the quiz.
     *
     * @return total question count
     */
    public int getTotalQuestions() {
        return questions.length;
    }

    /**
     * Stores the user's answer and advances to the next question.
     *
     * @param answer selected option index (0–3)
     * @return true if more questions remain, false if quiz is complete
     */
    public boolean submitAnswer(int answer) {
        answers[currentIndex] = answer;
        currentIndex++;
        return currentIndex < questions.length;
    }

    // FINISH 

    /**
     * Finalises the quiz by calculating scores
     * and attempting to save the competitor to the database.
     */
    public void finishQuiz() {

        // Calculate section-wise scores
        calculateScores();

        // Save competitor data (failure does not crash application)
        try {
            DatabaseHandler.saveCompetitor(currentUser);
        } catch (Exception e) {
            System.err.println("Failed to save competitor: " + e.getMessage());
        }
    }

    /**
     * Calculates scores for five sections,
     * each containing five questions.
     */
    private void calculateScores() {

        int[] finalScores = new int[5];

        // Loop through each section
        for (int i = 0; i < 5; i++) {
            int correct = 0;

            // Each section contains five questions
            for (int j = 0; j < 5; j++) {
                int idx = i * 5 + j;

                // Safety check to prevent index errors
                if (idx < questions.length && idx < answers.length) {
                    if (answers[idx] == questions[idx].getCorrectOption()) {
                        correct++;
                    }
                }
            }
            finalScores[i] = correct;
        }

        // Update competitor with final scores
        currentUser = new QuizCompetitor(
                0,
                currentUser.getName(),
                currentUser.getLevel(),
                currentUser.getCountry(),
                finalScores
        );
    }

    // GETTERS 

    /**
     * Returns the currently logged-in competitor.
     *
     * @return QuizCompetitor object
     */
    public QuizCompetitor getCurrentUser() {
        return currentUser;
    }
}
