// Name: Daniel Nguyen
// Date: December 6, 2024
// Purpose: This program uses Java FX to display GUI window for user input and calls methods to check for user input validation.
//          The program checks user input validation -> If user does enter all fields, display error message.
//          The program checks to see if there are any hanging empty string characters and trims them off before adding to the CSV file
//          The program checks for duplicates and displays an error message

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LMS extends Application {
    private BookManager bookManager;
    private String[] bookArray;
    private TextArea displayArea; // Used for displaying the list of books
    private TextField titleField, authorFirstNameField, authorLastNameField, isbnField;

    @Override
    public void start(Stage primaryStage) {
        // Create object bookManager 
        bookManager = new BookManager();

        // Load book data into an array
        bookArray = bookManager.getReportList();

        // Display area for books
        displayArea = new TextArea();
        displayArea.setEditable(false); // Make the TextArea read-only

        // Populate displayArea with bookArray data
        refreshBookDisplay();

        // Input fields for adding a book
        titleField = new TextField();
        authorFirstNameField = new TextField();
        authorLastNameField = new TextField();
        isbnField = new TextField();

        // Add Button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> handleAddBook());

        // Quit Button
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> {
            // Close the application when the Quit button is clicked
            primaryStage.close();
        });

        // Layout for the input form
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));
        form.add(new Label("Title:"), 0, 0);
        form.add(titleField, 1, 0);
        form.add(new Label("Author First Name:"), 0, 1);
        form.add(authorFirstNameField, 1, 1);
        form.add(new Label("Author Last Name:"), 0, 2);
        form.add(authorLastNameField, 1, 2);
        form.add(new Label("ISBN:"), 0, 3);
        form.add(isbnField, 1, 3);

        // Add the buttons to an HBox
        HBox buttonBox = new HBox(10); // Horizontal box with spacing of 10
        buttonBox.getChildren().addAll(addButton, quitButton);

        // Add the button box to the form
        form.add(buttonBox, 1, 4); // Place it in column 1, row 4 of the GridPane

        // Main layout
        VBox layout = new VBox(10); // Vertical box with spacing of 10
        layout.getChildren().addAll(displayArea, form);
        layout.setPadding(new Insets(10));

        // Scene setup
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Refresh display area using the bookArray
    // Make sure the GUI is updated with the current saved contents of the bookArray
    private void refreshBookDisplay() {
        String content = ""; // Start with an empty string
        // Loop through the number of books in the array
        for (int i = 0; i < bookArray.length; i++) {
            if (bookArray[i] != null) { // Check if you do not have access null entries
                content += bookArray[i] + "\n"; // Concatenate each book with a newline
            }
        }
        displayArea.setText(content); // Update the display area with the content
    }

    // Handle adding a book
    private void handleAddBook() {
        // Check if user input text has any extra null chracters and if they do, trim them off
        String title = titleField.getText() != null ? titleField.getText().trim() : "";
        String authorFirstName = authorFirstNameField.getText() != null ? authorFirstNameField.getText().trim() : "";
        String authorLastName = authorLastNameField.getText() != null ? authorLastNameField.getText().trim() : "";
        String isbn = isbnField.getText() != null ? isbnField.getText().trim() : "";

        // Check if all fields are filled
        if (title.isEmpty() || authorFirstName.isEmpty() || authorLastName.isEmpty() || isbn.isEmpty()) {
            // Display error message
            showAlert(Alert.AlertType.ERROR, "All fields must be filled!");
            return; // return back to the caller so user can fill out missing text fields 
        }

    // Create a new Book object with all user input
    Book newBook = new Book(title, authorFirstName, authorLastName, isbn);

    // Attempt to add the book to the BookManager
    if (bookManager != null) {
        boolean isAdded = bookManager.addBook(newBook); // Use addBook method from BookManager class
        if (isAdded) { // If the book has been added 
            // Save the book and refresh the display
            bookManager.saveBooks();
            bookArray = bookManager.getReportList(); // Update the array
            refreshBookDisplay(); 
        } else {
            // Check if the array is full or the book is a duplicate
            if (bookManager.getReportList().length >= 10) { 
                // Display error message for array full 
                showAlert(Alert.AlertType.ERROR, "The book array is full. Cannot add more books!");
            } else { // Display error message for duplicates
                showAlert(Alert.AlertType.ERROR, "Duplicate entry!");
            }
        }
    }

        // Clear input fields
        titleField.clear();

        authorFirstNameField.clear();
        authorLastNameField.clear();
        isbnField.clear();
    }

    // Display alert error GUI message 
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.showAndWait(); // Wait for user 
    }

    public static void main(String[] args) {
        launch(args);
    }
}
