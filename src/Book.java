// Name: Daniel Nguyen
// Date: December 6, 2024
// Purpose: This program contains the class Book that contains data fields for user input for book title, author name, and isbn
//          Getters for retrieving user input and override method to chck if user input book already exists in the CSV file
//          And contains another override method to return user input all to string

public class Book {
    // Data fields
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private String isbn;

    // Constructor
    public Book(String title, String authorFirstName, String authorLastName, String isbn) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.isbn = isbn;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public String getIsbn() {
        return isbn;
    }

    // Overridden equals method for duplicate checking
    @Override
    public boolean equals(Object obj) { // Object can provide toString() and equals() mehthods
        if (obj == null) return false; // Null check
        if (!(obj instanceof Book)) return false; // Check if the object is an instance of Book
        Book book = (Book) obj; // obj to Book
        return this.title.equals(book.title) &&
            this.authorFirstName.equals(book.authorFirstName) &&
            this.authorLastName.equals(book.authorLastName) &&
            this.isbn.equals(book.isbn);
    }

    // Overridden toString method for CSV format
    @Override
    public String toString() {
        return title + "," + authorFirstName + "," + authorLastName + "," + isbn;
    }
}
