// Name: Daniel Nguyen
// Date: April 9, 2025
// Purpose: 

public class Tasks {
    private String taskName;
    private String taskDescription;
    private String dueDate;
    private String taskStatus;
    private String taskPriority;

    public Tasks(String taskName, String taskDescription, String dueDate, String taskStatus, String taskPriority) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.dueDate = dueDate;
        this.taskStatus = taskStatus;
        this.taskPriority = taskPriority;
    }

    // Getters
    public String getTaskName() {
        return taskName;
    }
    public String getTaskDescription() {
        return taskDescription;
    }
    public String getTaskDueDate() {
        return dueDate;
    }
    public String getTaskStatus() {
        return taskStatus;
    }
    public String getTaskPriority() {
        return taskPriority;
    }

    // Overridden equals method for duplicate checking
    @Override
    public boolean equals(Object obj) { // Object can provide toString() and equals() methods
        if (obj == null) return false; // Null check
        if (!(obj instanceof Tasks)) return false; // Check if the object is an instance of tasks
        Tasks task = (Tasks) obj; // obj to tasks
        return this.taskName.equals(task.taskName) &&
            this.taskDescription.equals(task.taskDescription) &&
            this.dueDate.equals(task.dueDate) &&
            this.taskStatus.equals(task.taskStatus) &&
            this.taskPriority.equals(task.taskPriority);
    }
    
    // Overridden toString method for CSV format
    @Override
    public String toString() {
        return taskName + "," + taskDescription + "," + dueDate + "," + taskStatus + "," + taskPriority;
    }
    // Method to display task details
    public String displayTaskDetails() {
        return "Task Name: " + taskName + "\n" +
               "Description: " + taskDescription + "\n" +
               "Due Date: " + dueDate + "\n" +
               "Status: " + taskStatus + "\n" +
               "Priority: " + taskPriority;
    }

    // Method to update task status
    public void updateTaskStatus(String newStatus) {
        this.taskStatus = newStatus;
    }
    // Method to update task priority
    public void updateTaskPriority(String newPriority) {
        this.taskPriority = newPriority;
    }
    // Method to update task due date
    public void updateTaskDueDate(String newDueDate) {
        this.dueDate = newDueDate;
    }
    // Method to update task description
    public void updateTaskDescription(String newDescription) {
        this.taskDescription = newDescription;
    }
    // Method to update task name
    public void updateTaskName(String newName) {
        this.taskName = newName;
    }

    // Method to check if the task is overdue
    public boolean isOverdue(String currentDate) {
        // Compare the task's due date with the current date
        // Assuming the date format is "YYYY-MM-DD"
        return dueDate.compareTo(currentDate) < 0;
    }
    // Method to check if the task is completed
    public boolean isCompleted() {
        return taskStatus.equalsIgnoreCase("Completed");
    }
    // Method to check if the task is pending
    public boolean isPending() {
        return taskStatus.equalsIgnoreCase("Pending");
    }
    // Method to check if the task is high priority
    public boolean isHighPriority() {
        return taskPriority.equalsIgnoreCase("High");
    }
    // Method to check if the task is medium priority
    public boolean isMediumPriority() {
        return taskPriority.equalsIgnoreCase("Medium");
    }
    // Method to check if the task is low priority
    public boolean isLowPriority() {
        return taskPriority.equalsIgnoreCase("Low");
    }

    // Method to check if the task is valid
    public boolean isValid() {
        // Check if all fields are filled
        return !taskName.trim().isEmpty() && 
               !taskDescription.trim().isEmpty() && 
               !dueDate.trim().isEmpty() && 
               !taskStatus.trim().isEmpty() && 
               !taskPriority.trim().isEmpty();
    }
    // Method to check if the task name is valid
    public boolean isTaskNameValid() {
        // Check if the task name is not empty
        return !taskName.trim().isEmpty();
    }
    // Method to check if the task description is valid
    public boolean isTaskDescriptionValid() {
        // Check if the task description is not empty
        return !taskDescription.trim().isEmpty();
    }
    // Method to check if the task due date is valid
    public boolean isTaskDueDateValid() {
        // Check if the task due date is not empty
        return !dueDate.trim().isEmpty();
    }
    // Method to check if the task status is valid
    public boolean isTaskStatusValid() {
        // Check if the task status is not empty
        return !taskStatus.trim().isEmpty();
    }
    // Method to check if the task priority is valid
    public boolean isTaskPriorityValid() {
        // Check if the task priority is not empty
        return !taskPriority.trim().isEmpty();
    }
    // Method to check if the task is valid
    public boolean isTaskValid() {
        // Check if all fields are filled
        return isTaskNameValid() && 
               isTaskDescriptionValid() && 
               isTaskDueDateValid() && 
               isTaskStatusValid() && 
               isTaskPriorityValid();
    }
}
