import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskService {
    Connection connection = null;
    Scanner scanner = new Scanner(System.in);
    public void listAllTasks(Connection connection) {
        String query = "select * from task;";
        try {
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery(query);
            System.out.println("List of task:");
            System.out.println("+-------+------------------+----------------------------------------+--------------+------------+------------+-------------+----------+------------------+-------------+");
            System.out.println("|Index  |     Task Name    |               Descriptions             |  To-Do Date  | Start-Time |  End-Time  |   Due Date  | Priority |     Category     |     Status  |");
            System.out.println("+-------+------------------+----------------------------------------+--------------+------------+------------+-------------+----------+------------------+-------------+");

            while (resultSet.next()) {
                int index = resultSet.getInt("task_index");
                String taskName = resultSet.getString("task_name");
                String description =resultSet.getString("description");
                String todoDate = resultSet.getString("todo_date");
                String startTime = resultSet.getString("start_time");
                String endTime = resultSet.getString("end_time");
                String dueDate = resultSet.getString("due_date");
                int taskPriority = resultSet.getInt("priority");
                String category =resultSet.getString("category");
                String status = resultSet.getString("status");
       
                System.out.printf("| %-5d | %-16s | %-38s | %-12s | %-10s | %-10s | %-11s | %-8d | %-16s | %-11s | \n",index,taskName,description, todoDate, 
                		startTime, endTime, dueDate, taskPriority,category,status );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void addTask(Scanner scanner, Connection connection) {
        String query = "insert into task(task_index,task_name, description, todo_date, start_time, end_time, due_date, priority, category, status) values(?,?,?,?,?,?,?,?,?,?);";
        try {
            System.out.print("Enter task index: ");
            int index = scanner.nextInt();
            System.out.print("Enter task name: ");
            String taskName = scanner.next();
            System.out.print("Enter task description: ");
            String description = scanner.next();
            System.out.print("Enter to-do date: ");
            String todoDate = scanner.next();
            System.out.print("Enter time to start: ");
            String startTime= scanner.next();
            System.out.print("Enter time to finish: ");
            String endTime = scanner.next();
            System.out.print("Enter due date: ");
            String dueDate = scanner.next();
            System.out.print("Enter task priority (1 for high, 2 for medium, 3 for low): ");
            int taskPriority = scanner.nextInt();
            System.out.print("Enter task category: ");
            String category = scanner.next();
            System.out.print("Enter task status: ");
            String status = scanner.next();

            try {
                validateTaskDate(index, taskName, description,todoDate, startTime, endTime,dueDate, taskPriority, category,status );

                System.out.println(" Task added successfully!");
                //saveTasksToFile();
            } catch (Exception e) {
                System.out.println(e);
            }
            PreparedStatement preparedStm = connection.prepareStatement(query);
            preparedStm.setInt(1, index);
            preparedStm.setString(2, taskName);
            preparedStm.setString(3, description);
            preparedStm.setString(4, todoDate);
            preparedStm.setString(5, startTime);
            preparedStm.setString(6, endTime);
            preparedStm.setString(7, dueDate);
            preparedStm.setInt(8, taskPriority);
            preparedStm.setString(9, category);
            preparedStm.setString(10, status);
            preparedStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void validateTaskDate(int index,String taskName, String description, String todoDate, String startTime, String endTime, String dueDate, int taskPriority,String category, String status) {
        // Validate date of task
        if (!isValidDateOfTask(todoDate)) {
            // if false, throw new exception here
            throw new IllegalArgumentException(
                    "Invalid to-do date format! Please input the date in format (YYYY-MM-DD)");
        }else if (!isValidDateOfTask(dueDate)) {
        	throw new IllegalArgumentException(
                    "Invalid due date format! Please input the date in format (YYYY-MM-DD)");
        }
    }

    // Validate date of task format (YYYY-MM-DD)
    private static boolean isValidDateOfTask(String date) {
        String datePattern = "\\d{4}-\\d{2}-\\d{2}";
        return date.matches(datePattern);
    }

    public void deleteTask(int index , Connection connection) {
        String query = "delete from task where task_index = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, index);
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Task deleted successfully!");
            } else {
                System.out.println("No task found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editTask(int index, Scanner scanner, Connection connection) {
        String query = "update task set task_name=?,description=?, todo_date=?, start_time=?, end_time=?, due_date=?, priority=?, category=? ,status=? where task_index =? ;";
        try {

            System.out.print("Enter new task name: ");
            String taskNameUpdate = scanner.next();
            System.out.print("Enter new task description: ");
            String descriptionUpdate = scanner.next();
            System.out.print("Enter new to-do date: ");
            String todoDateUpdate = scanner.next();
            System.out.print("Enter new time to start: ");
            String startTimeUpdate= scanner.next();
            System.out.print("Enter new time to finish: ");
            String endTimeUpdate = scanner.next();
            System.out.print("Enter new due date: ");
            String dueDateUpdate = scanner.next();
            System.out.print("Enter new task priority (1 for high, 2 for medium, 3 for low): ");
            int taskPriorityUpdate = scanner.nextInt();
            System.out.print("Enter new task category: ");
            String categoryUpdate = scanner.next();
            System.out.print("Enter new task status: ");
            String statusUpdate = scanner.next();
            try {
                validateTaskDate(index,taskNameUpdate, descriptionUpdate, todoDateUpdate, startTimeUpdate, endTimeUpdate, dueDateUpdate,taskPriorityUpdate, categoryUpdate,statusUpdate);

            } catch (Exception e) {
                System.out.println(e);
            }
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, taskNameUpdate);
            preparedStatement.setString(2, descriptionUpdate);
            preparedStatement.setString(3, todoDateUpdate);
            preparedStatement.setString(4, startTimeUpdate);
            preparedStatement.setString(5, endTimeUpdate);
            preparedStatement.setString(6, dueDateUpdate);
            preparedStatement.setInt(7, taskPriorityUpdate);
            preparedStatement.setString(8, categoryUpdate);
            preparedStatement.setString(9, statusUpdate);
            preparedStatement.setInt(10, index);

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Task updated successfully");
            } else {
                System.out.println("No task found!!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void prioritizeTask(int index, int newPriority, Connection connection) {
        String query = "update task set priority=? where task_index=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newPriority);
            preparedStatement.setInt(2, index);
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Task prioritized successfully!");
            } else {
                System.out.println("No task found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listTasksByPriority(int priority, Connection connection) {
        String query = "select * from task where priority = ? and status!='Completed'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, priority);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("List of tasks with priority " + priority + ":");
           
            System.out.println("+-------+------------------+----------------------------------------+--------------+------------+------------+-------------+----------+------------------+-------------+");
            System.out.println("|Index  |     Task Name    |               Descriptions             |  To-Do Date  | Start-Time |  End-Time  |   Due Date  | Priority |     Category     |     Status  |");
            System.out.println("+-------+------------------+----------------------------------------+--------------+------------+------------+-------------+----------+------------------+-------------+");

            while (resultSet.next()) {
                int index = resultSet.getInt("task_index");
                String taskName = resultSet.getString("task_name");
                String description =resultSet.getString("description");
                String todoDate = resultSet.getString("todo_date");
                String startTime = resultSet.getString("start_time");
                String endTime = resultSet.getString("end_time");
                String dueDate = resultSet.getString("due_date");
                int taskPriority = resultSet.getInt("priority");
                String category =resultSet.getString("category");
                String status = resultSet.getString("status");
       
                System.out.printf("| %-5d | %-16s | %-38s | %-12s | %-10s | %-10s | %-11s | %-8d | %-16s | %-11s | \n",index,taskName,description, todoDate, 
                		startTime, endTime, dueDate, taskPriority,category,status );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listOverdueTasks(Connection connection) {
        String query = "select * from task where due_date < current_date and status!='Completed'";
        //current_date is a function in SQL that returns the current date according to the system date of the database server. It represents the current date at the time the SQL query is executed.
        try {
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery(query);
            System.out.println("List of overdue tasks:");
           
            System.out.println("+-------+------------------+----------------------------------------+--------------+------------+------------+-------------+----------+------------------+-------------+");
            System.out.println("|Index  |     Task Name    |               Descriptions             |  To-Do Date  | Start-Time |  End-Time  |   Due Date  | Priority |     Category     |     Status  |");
            System.out.println("+-------+------------------+----------------------------------------+--------------+------------+------------+-------------+----------+------------------+-------------+");

            while (resultSet.next()) {
                int index = resultSet.getInt("task_index");
                String taskName = resultSet.getString("task_name");
                String description =resultSet.getString("description");
                String todoDate = resultSet.getString("todo_date");
                String startTime = resultSet.getString("start_time");
                String endTime = resultSet.getString("end_time");
                String dueDate = resultSet.getString("due_date");
                int taskPriority = resultSet.getInt("priority");
                String category =resultSet.getString("category");
                String status = resultSet.getString("status");
       
                System.out.printf("| %-5d | %-16s | %-38s | %-12s | %-10s | %-10s | %-11s | %-8d | %-16s | %-11s | \n",index,taskName,description, todoDate, 
                		startTime, endTime, dueDate, taskPriority,category,status );
        } 
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   

}
    