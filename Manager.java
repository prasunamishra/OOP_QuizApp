package Coursework1;

//Manager.java
//This class manages the quiz competition reports

public class Manager {

 private CompetitorList competitorList;

 // Constructor - retrieve all competitors from database
 public Manager() {
     // Get competitors from DB
     QuizCompetitor[] all = DatabaseHandler.getAllCompetitors();
     competitorList = new CompetitorList(all);
 }

 // Display full leaderboard
 public void showLeaderboard() {
     System.out.println("\n===== LEADERBOARD =====");
     competitorList.displayLeaderboard();
     competitorList.displayScoreFrequencies();
 }

 // Display top performer
 public void showTopPerformer() {
     QuizCompetitor top = competitorList.getTopPerformer();
     if (top != null) {
         System.out.println("\n===== TOP PERFORMER =====");
         System.out.println(top.getFullDetails());
     } else {
         System.out.println("No competitors found.");
     }
 }

 // Display competitor short details by ID
 public void showCompetitorByID(int id) {
     for (QuizCompetitor c : competitorList.getCompetitors()) {
         if (c.getCompetitorID() == id) {
             System.out.println(c.getShortDetails());
             return;
         }
     }
     System.out.println("Competitor ID " + id + " not found.");
 }

 // Get competitor list (for further use, e.g., GUI)
 public CompetitorList getCompetitorList() {
     return competitorList;
 }

 // For testing purposes
 public static void main(String[] args) {
     Manager m = new Manager();

     // Show leaderboard
     m.showLeaderboard();

     // Show top performer
     m.showTopPerformer();

     // Show a competitor by ID (example: 101)
     m.showCompetitorByID(101);
 }
}
