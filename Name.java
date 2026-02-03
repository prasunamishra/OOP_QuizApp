package Coursework1;

/**
 * Represents a person's name.
 * Stores first name and last name separately and
 * provides helper methods to return formatted values.
 */
public class Name {

 
    private String firstName;
    private String lastName;

    /**
     * Constructor for Name.
     * Ensures null values are safely handled.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     */
    public Name(String firstName, String lastName) {
        // Replacing null values with empty strings to avoid errors
        this.firstName = firstName == null ? "" : firstName;
        this.lastName = lastName == null ? "" : lastName;
    }

    /**
     * Returns the first name.
     *
     * @return first name as a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name.
     *
     * @return last name as a String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the initials of the name.
     * If a name is missing, '?' is used as a placeholder.
     *
     * @return two-character initials string
     */
    public String getInitials() {
        char f = firstName.isEmpty() ? '?' : firstName.charAt(0);
        char l = lastName.isEmpty() ? '?' : lastName.charAt(0);
        return "" + f + l;
    }

    /**
     * Returns the full name in "FirstName LastName" format.
     * Extra spaces are trimmed if either name is missing.
     *
     * @return full name as a String
     */
    public String getFullName() {
        return (firstName + " " + lastName).trim();
    }
}
