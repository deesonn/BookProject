// Name: Daniel Nguyen
// Date: December 6, 2024
// Purpose: This program manages the array of books. It has a maximum size of 10 books total. The program contains a method to check and load 
//          the file Books.csv if there is one. If there isn't, it will write a new file. The program reads the file line by line and splits
//          each book's description by its delimeter using the comma. The program has a method saveBooks that writes the user input books
//          into the csv file. The program checks for any duplicate books and provides a report of all the books into an array of strings.  

import java.io.*;
import java.util.Scanner;

public class BookManager {
    // Data fields 
    private Book[] books; // Array to store books
    private int bookCount; // Keeps track of the number of books

    // Constructor
    public BookManager() {
        books = new Book[10]; // Initial size of 10
        bookCount = 0;
        loadBooks(); // Load books from Books.csv
    }

    // Load books from Books.csv method
    public void loadBooks() {
        // Check for file name
        File file = new File("Books.csv");
        if (!file.exists()) {
            return; // If file doesn't exist, skip loading
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) { // Read next line
                // Save read line to variable line
                String line = scanner.nextLine();
                // Split each part of the line by searching for its delimiter ","
                String[] parts = line.split(","); 
                if (parts.length == 4) { 
                    addBook(new Book(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Books.csv not found.");
        }
    }

    // Save books to Books.csv
    public void saveBooks() {
        // Write into Books.csv file using PrintWriter
        // Check if the file exists or not before writing new file or appending to existing
        try (PrintWriter writer = new PrintWriter(new FileWriter("Books.csv", false))) { 
            // Loop through each book's parts and conver them to string and write into file 
            for (int i = 0; i < bookCount; i++) {
                writer.println(books[i].toString());
            }
        } catch (IOException e) {
            // Display error message. 
            System.out.println("Error: Unable to save books to file.");
        }
    }

    // Add a book to the array
    public boolean addBook(Book book) {
        // Check if the book is a duplicate
        if (isDuplicate(book)) {
            return false;
        }

        // Check if the array is full
        if (bookCount == books.length) {
            // Display ful message 
            System.out.println("The book array is full. Cannot add more books.");
            return false; // Return false if there’s no more space
        }

        // Add the book to the array and increment the count
        books[bookCount++] = book;
        return true;
    }

    // Check if the book is a duplicate
    private boolean isDuplicate(Book book) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].equals(book)) {
                return true;
            }
        }
        return false;
    }

    // Get a report list of books
    public String[] getReportList() {
        String[] report = new String[bookCount]; // Create an array to store string books 
        for (int i = 0; i < bookCount; i++) {
            report[i] = books[i].toString(); // Convert books to strings
        }
        return report;
    }
}
