package Coursework1;

/**
 * Represents a single quiz question.
 * Each question contains the question text,
 * four possible answer options, and the index
 * of the correct option.
 */
public class Question {

    // The text of the quiz question
    private String questionText;

    // Array storing the four answer options
    private String[] options;

    // Index of the correct option from (0–3)
    private int correctOption;

    /**
     * Constructor for creating a Question object.
     *
     * @param questionText  the question text
     * @param options       an array of four answer options
     * @param correctOption the index (0–3) of the correct answer
     */
    public Question(String questionText, String[] options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }

    /**
     * Returns the question text.
     *
     * @return question text as a String
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Returns the available answer options.
     *
     * @return array of answer options
     */
    public String[] getOptions() {
        return options;
    }

    /**
     * Returns the index of the correct option.
     *
     * @return correct option index (0–3)
     */
    public int getCorrectOption() {
        return correctOption;
    }
}
