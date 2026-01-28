package Coursework1;

//Represents a single quiz question
public class Question {
 private String questionText;
 private String[] options; // 4 options
 private int correctOption; // 0-based index (0 to 3)

 public Question(String questionText, String[] options, int correctOption) {
     this.questionText = questionText;
     this.options = options;
     this.correctOption = correctOption;
 }

 public String getQuestionText() {
     return questionText;
 }

 public String[] getOptions() {
     return options;
 }

 public int getCorrectOption() {
     return correctOption;
 }
}
