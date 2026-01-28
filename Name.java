package Coursework1;

public class Name {

    private String firstName;
    private String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName == null ? "" : firstName;
        this.lastName = lastName == null ? "" : lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    // ✅ SAFETY FIX ONLY
    public String getInitials() {
        char f = firstName.isEmpty() ? '?' : firstName.charAt(0);
        char l = lastName.isEmpty() ? '?' : lastName.charAt(0);
        return "" + f + l;
    }

    public String getFullName() {
        return (firstName + " " + lastName).trim();
    }
}
