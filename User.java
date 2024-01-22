import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class User {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/taskmanager";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sokunthy*1297#";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        Connection connection = null;
        User user = new User();
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    } catch (SQLException sqle) {
            sqle.printStackTrace();
    }
    System.out.println("Do you have an account yet?");
    System.out.print("Enter your answer here (Yes or No): ");
    String answer = scanner.next();
    if (answer != "Yes"){
        System.out.println("Let's create an account");
        user.createAccount(scanner, connection);   
    }
    int choice=0;
	do {
		System.out.print("""
				*********************************************************************************************
				Please select an option:
				1. List all vehicle_types (Listing from Database)
				2. Add new vehicle_type (Store in Database)
				3. Remove vehicle_type by id (all vehicles associated with this vehicle_type also be removed)
				4. Quit
				*********************************************************************************************
				""");
		System.out.print("Enter your option: ");
		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
        case 1:               
            VhcT.listAllVehicleTypes(connection);
            System.out.println("+----------------------+");
            break;          
        case 2:
            VhcT.addNewVehicleType(scanner,connection);               
            break;
        case 3:   
            VhcT.removeVehicleTypeById(scanner,connection);
            break;
        case 4:
        	System.out.println("Good bye!");
            break;
        default:
			System.out.println("Invalid choice");
			break;
			}
} while(choice!=4);
    /*else if (answer == "Yes") {
    	System.out.print("Please enter your password to edit: ");
    	int pw = scanner.nextInt();
    	user.editAccount(pw, scanner, connection);
    }*?

	}
    public void createAccount( Scanner scanner , Connection connection ) {
        String query = "insert into users(password,user_name, user_type, task_preference) values(?,?,?,?);";
        try {
            System.out.print("Enter your password: ");
            int pw = scanner.nextInt();
            System.out.print("Enter your user name: ");
            String userName = scanner.nextLine();
            scanner.nextLine();
            System.out.print("Enter your position ( Student/ Teacher / Employee): ");
            String userType = scanner.nextLine();
            scanner.nextLine();
            System.out.print("Enter your task preference: ");
            String taskPreference = scanner.nextLine();
            scanner.nextLine();
            try {
                
                System.out.println(" User added successfully!");
              
            } catch (Exception e) {
                System.out.println(e);
            }
            PreparedStatement preparedStm = connection.prepareStatement(query);
            preparedStm.setInt(1,pw );
            preparedStm.setString(2, userName);
            preparedStm.setString(3, userType);
            preparedStm.setString(4, taskPreference);     
            preparedStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listallAccounts(Connection connection ) {
    String query = "select * from users;";
    try {
        Statement stm = connection.createStatement();
        ResultSet resultSet = stm.executeQuery(query);
        System.out.println("List of all accouts:");
        System.out.println("+------------+-----------------+-------------------+-------------------+");
        System.out.println("| Passwords  |    User Names   |    Positions      |  Task Preference  |");
        System.out.println("+------------+-----------------+-------------------+-------------------+");

        while (resultSet.next()) {
            int pw = resultSet.getInt("password");
            String name = resultSet.getString("user_user");
            String position =resultSet.getString("user_type");
            String taskPref = resultSet.getString("task_preference");
            
            System.out.printf("| %-6d | %-12s | %-16s | %-18s | \n",pw, name, position, taskPref );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public void editAccount(int pw, Scanner scanner, Connection connection){
        String query = "update users set user_name=?, user_type=?, user_preference where password =?) ;";
        try {

            System.out.print("Enter your new user name: ");
            String nameUpdate = scanner.next();
            System.out.print("Enter your new position (Student/ Teacher / Employee): ");
            String positionUpdate = scanner.nextLine();
            System.out.print("Enter your new task preference: ");
            String taskPreUpdate = scanner.next();
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameUpdate);
            preparedStatement.setString(2, positionUpdate);
            preparedStatement.setString(3, taskPreUpdate);
            preparedStatement.setInt(9, pw);

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Your accout edited successfully");
            } else {
                System.out.println("No account found!!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    

}
