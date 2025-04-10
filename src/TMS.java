// Name: Daniel Nguyen
// Date: April 9, 2025
// Purpose:

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TMS extends Application {
    private TaskManager taskManager; // Instance of TaskManager
    private ArrayList<Tasks> taskArray; // Array to store tasks
    private TextArea displayArea; // Used for displaying the list of tasks
    private DatePicker dueDatePicker; // DatePicker for selecting due date
    private TextField taskNameField, taskDescriptionField, taskStatusField, taskPriorityField; // Input fields for tasks

    @Override
    public void start(Stage primaryStage) {
        
        // Create object TaskManager 
        taskManager = new TaskManager();

        // Load task data into an array
        taskArray = taskManager.getTasksList();

        // Display area for tasks
        displayArea = new TextArea();
        displayArea.setEditable(false); // Make the TextArea read-only

        // Populate displayArea with taskArray data
        refreshDisplayArea();

        // Input fields for adding a task
        taskNameField = new TextField();
        taskDescriptionField = new TextField();
        taskStatusField = new TextField();
        taskPriorityField = new TextField();

        // Add Button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            // Handle adding a task
            String taskName = taskNameField.getText();
            taskNameField.clear(); // Clear the input field after adding
            String taskDescription = taskDescriptionField.getText();
            taskDescriptionField.clear(); // Clear the input field after adding
            dueDatePicker.setValue(null); 
            String taskStatus = taskStatusField.getText();
            taskStatusField.clear(); // Clear the input field after adding
            String taskPriority = taskPriorityField.getText();
            taskPriorityField.clear(); // Clear the input field after adding

            Tasks newTask = new Tasks( taskName, taskDescription, 
            dueDatePicker.getValue() != null ? dueDatePicker.getValue().toString() : "", 
            taskStatus, taskPriority);
            if (!taskArray.contains(newTask)) {
                taskManager.addTask(newTask);
                refreshDisplayArea(); // Refresh the display area
                taskManager.saveTasks(); // Save tasks to file
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Duplicate Task! Task already exists.");
                alert.showAndWait();
            }
        });

        // Quit Button
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> {
            // Close the application when the Quit button is clicked
            System.exit(0);
        });

        // Layout for the input form
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));
        form.add(new Label("Task Name:"), 0, 0);
        form.add(taskNameField, 1, 0);
        form.add(new Label("Description:"), 0, 1);
        form.add(taskDescriptionField, 1, 1);
    
        dueDatePicker = new DatePicker();
        form.add(new Label("Due Date:"), 0, 2);
        form.add(dueDatePicker, 1, 2);
        form.add(new Label("Status:"), 0, 3);
        form.add(taskStatusField, 1, 3);
        form.add(new Label("Priority:"), 0, 4);
        form.add(taskPriorityField, 1, 4);
        form.add(addButton, 0, 6);
        form.add(quitButton, 1, 6);

        // Main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(displayArea, form);

        // Scene setup
        Scene scene = new Scene(mainLayout, 400, 400);
        primaryStage.setTitle("Task Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
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
        StringBuilder allTasks = new StringBuilder();
        for (Tasks task : taskArray) {
            allTasks.append(task.displayTaskDetails()).append("\n");
        }
        return allTasks.toString();
    }
    // Method to refresh the display area
    public void refreshDisplayArea() {
        displayArea.clear(); // Clear the display area
        for (Tasks task : taskArray) {
            displayArea.appendText(task.displayTaskDetails() + "\n"); // Append each task's details
        }
    }
    // Method to handle adding a task
public void handleAddTask() {
    String taskName = taskNameField.getText();
    String taskDescription = taskDescriptionField.getText();
    String taskDueDate = dueDatePicker.getValue() != null ? dueDatePicker.getValue().toString() : "";
    String taskStatus = taskStatusField.getText();
    String taskPriority = taskPriorityField.getText();

    // Validate that all fields are filled
    if (taskName.isEmpty() || taskDescription.isEmpty() || taskDueDate.isEmpty() || taskStatus.isEmpty() || taskPriority.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must be filled!");
        alert.showAndWait();
        return;
    }

    // Create a new task
    Tasks newTask = new Tasks(taskName, taskDescription, 
    dueDatePicker.getValue() != null ? dueDatePicker.getValue().toString() : "", 
    taskStatus, taskPriority);

    // Add the task if it's not a duplicate
    if (!taskArray.contains(newTask)) {
        taskManager.addTask(newTask);
        refreshDisplayArea(); // Refresh the display area
        taskManager.saveTasks(); // Save tasks to file
    } else {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Duplicate Task! Task already exists.");
        alert.showAndWait();
    }

    // Clear input fields
    taskNameField.clear();
    taskDescriptionField.clear();
    dueDatePicker.setValue(null); // Clear the DatePicker
    taskStatusField.clear();
    taskPriorityField.clear();
}
    // Method to handle quitting the application
    public void handleQuit() {
        taskManager.saveTasks(); // Save tasks to file before quitting
        System.exit(0);
}
    // Method to handle deleting a task
    public void handleDeleteTask(Tasks task) {
        if (taskManager.deleteTask(task)) {
            refreshDisplayArea(); // Refresh the display area
            taskManager.saveTasks(); // Save tasks to file
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Task not found!");
            alert.showAndWait();
        }
    }
    // Method to handle updating a task
    public void handleUpdateTask(Tasks oldTask, Tasks newTask) {
        if (taskManager.updateTask(oldTask, newTask)) {
            refreshDisplayArea(); // Refresh the display area
            taskManager.saveTasks(); // Save tasks to file
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Task not found!");
            alert.showAndWait();
        }
    }
    // Method to handle clearing all tasks
    public void handleClearTasks() {
        taskManager.clearTasks(); // Clear all tasks
        refreshDisplayArea(); // Refresh the display area
        taskManager.saveTasks(); // Save tasks to file
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
