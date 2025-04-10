// Name: Daniel Nguyen
// Date: April 9, 2025
// Purpose:

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class TaskManager {

    // Data fields
    private ArrayList<Tasks> tasksList; // Array to store tasks
    private int taskCount; // Keeps track of the number of tasks
    private static final String FILE_NAME = "Tasks.csv"; // File name for tasks
    private static final String DELIMITER = ","; // Delimiter for CSV file
    private static final String[] HEADERS = {"Task Name", "Description", "Due Date", "Status", "Priority"}; // Headers for CSV file 
    private static final String[] STATUSES = {"Pending", "In Progress", "Completed"}; // Possible statuses for tasks
    private static final String[] PRIORITIES = {"High", "Medium", "Low"}; // Possible priorities for tasks
    

    // Constructor
    public TaskManager() {
        tasksList = new ArrayList<>(); // Initialize the ArrayList
        taskCount = 0; // Initialize task count
        loadTasks(); // Load tasks from file
    }

    // Load tasks from file
    public void loadTasks() {
        // Check for file name
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; // If file doesn't exist, skip loading
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) { // Read next line
                // Save read line to variable line
                String line = scanner.nextLine();
                // Split each part of the line by searching for its delimiter ","
                String[] parts = line.split(DELIMITER);
                if (parts.length == HEADERS.length) { 
                    addTask(new Tasks(parts[0], parts[1], parts[2], parts[3], parts[4]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + FILE_NAME + " not found.");
        }
    }
    // Save tasks to file
    public void saveTasks() {
        // Write into Tasks.csv file using PrintWriter
        // Check if the file exists or not before writing new file or appending to existing
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, false))) { 
            // Write headers to the file
            writer.println(String.join(DELIMITER, HEADERS));
            // Loop through each task's parts and convert them to string and write into file 
            for (Tasks task : tasksList) {
                writer.println(task.toString());
            }
        } catch (IOException e) {
            // Display error message. 
            System.out.println("Error: Unable to save tasks to file.");
        }
    }
    // Add a task to the array
    public boolean addTask(Tasks task) {
        // Check if the task is a duplicate
        if (isDuplicate(task)) {
            return false;
        }
        tasksList.add(task); // Add task to the list
        taskCount++; // Increment task count
        saveTasks(); // Save tasks to file
        return true; // Return true if added successfully
    }
    // Check if the task is a duplicate
    private boolean isDuplicate(Tasks task) {
        for (Tasks t : tasksList) {
            if (t.equals(task)) {
                return true; // Return true if duplicate found
            }
        }
        return false; // Return false if no duplicates found
    }
    // Getters
    public ArrayList<Tasks> getTasksList() {
        return tasksList; // Return the list of tasks
    }
    public int getTaskCount() {
        return taskCount; // Return the number of tasks
    }
    // Getters for statuses and priorities
    public String[] getStatuses() {
        return STATUSES; // Return the array of statuses
    }
    public String[] getPriorities() {
        return PRIORITIES; // Return the array of priorities
    }
    // Getters for headers
    public String[] getHeaders() {
        return HEADERS; // Return the array of headers
    }
    // Getters for file name and delimiter
    public String getFileName() {
        return FILE_NAME; // Return the file name
    }
    public String getDelimiter() {
        return DELIMITER; // Return the delimiter
    }
    // Method to display task details
    public String displayTaskDetails(Tasks task) {
        return "Task Name: " + task.getTaskName() + "\n" +
               "Description: " + task.getTaskDescription() + "\n" +
               "Due Date: " + task.getTaskDueDate() + "\n" +
               "Status: " + task.getTaskStatus() + "\n" +
               "Priority: " + task.getTaskPriority();
    }
    // Method to display all tasks
    public String displayAllTasks() {
        StringBuilder sb = new StringBuilder(); // StringBuilder to build the string
        for (Tasks task : tasksList) {
            sb.append(displayTaskDetails(task)).append("\n"); // Append each task's details
        }
        return sb.toString(); // Return the string representation of all tasks
    }
    // Method to delete a task
    public boolean deleteTask(Tasks task) {
        if (tasksList.remove(task)) { // Remove the task from the list
            taskCount--; // Decrement task count
            saveTasks(); // Save tasks to file
            return true; // Return true if deleted successfully
        }
        return false; // Return false if not found
    }
    // Method to update a task
    public boolean updateTask(Tasks oldTask, Tasks newTask) {
        int index = tasksList.indexOf(oldTask); // Find the index of the old task
        if (index != -1) { // If found
            tasksList.set(index, newTask); // Update the task
            saveTasks(); // Save tasks to file
            return true; // Return true if updated successfully
        }
        return false; // Return false if not found
    }
    // Method to clear all tasks
    public void clearTasks() {
        tasksList.clear(); // Clear the list
        taskCount = 0; // Reset task count
        saveTasks(); // Save tasks to file
    }
}
