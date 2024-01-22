import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class TaskMananger {
        private static final String DB_URL = "jdbc:mysql://localhost:3306/taskmanager";
        private static final String DB_USER = "root";
        private static final String DB_PASSWORD = "Sokunthy*1297#";

        public static void main(String[] args) throws IOException {
                Scanner scanner = new Scanner(System.in);
                Connection connection = null;
                TaskService taskService = new TaskService();
                try {
                        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                } catch (SQLException sqle) {
                        sqle.printStackTrace();
                }

                System.out.println("\t\t*----*----*----*----*----*----*----*----*");
                System.out.println("\t\t|**---*---*---*---*---*---*---*---*---**|");
                System.out.println("\t\t||| ====> # OOP FINAL PROJECT # <==== |||");
                System.out.println("\t\t|**---*---*---*---*---*---*---*---*---**|");
                System.out.println("\t\t*----*----*----*----*----*----*----*----*");

                System.out.println("""
                                    ________________________________________________________________
                                    |   GROUP 2 MEMBERS                         ID                  |
                                    | 1. SROEM SOKUNTHY                      e20211557              |
                                    | 2. TAING THAITHEANG                    e20210872              |
                                    | 3. THONG OUSAPHEA                      e20200643              |
                                    | 4. VENG MENGSOKLIN                     e20210095              |
                                    |                                                               |
                                    | Lecturer: Mr. NA STANDA                                       |
                                    | Group : TP-D                                                  |
                                    | Academic year : 2023-2024                                     |
                                    |_______________________________________________________________|

                                """);

                System.out.println("""

                                    \t\t*---*---*---*---*---*---*---*---*---*
                                    \t\t|>>>>>>>| This is our topic |<<<<<<<|
                                    \t\t*---*---*---*---*---*---*---*---*---*

                                """);

                System.out.println("***************************************************************");
                System.out.println("**\\\\|||||||||||||||||||||||||||||||||||||||||||||||||||||//**");
                System.out.println("**|\\\\ ************************************************* //|**");
                System.out.println("**||\\\\  *                                           *  //||**");
                System.out.println("|||||\\\\  *  WELCOME TO OUR TASK MANAGEMENT SYSTEM  *  //|||||");
                System.out.println("**|||// *                                           * \\\\|||**");
                System.out.println("**||// *********************************************** \\\\||**");
                System.out.println("**|//|||||||||||||||||||||||||||||||||||||||||||||||||||\\\\|**");
                System.out.println("***************************************************************");

                System.out.println(
                                """
                                                _______________________________________________________________________
                                                |  __________________________________________________________________  |
                                                | |                         _________________                        | |
                                                                           |                 |
                                                | |                        | Our Objectives  |                       | |
                                                                           |_________________|
                                                | |                                                                  | |

                                                | |     1/. High Productivity                                        | |
                                                        2/. Better Organization
                                                | |     3/. Improve Time Management                                  | |
                                                        4/. Develop Self-discipline
                                                | |     5/. Work-life Balance                                        | |

                                                | |                                                                  | |

                                                | |__________________________________________________________________| |                                                                  | |
                                                |______________________________________________________________________|

                                                """);
                int choice = 0;
                do {
                        System.out.println(
                                        """
                                                        _________________________________________________________________________________
                                                        |    \t\t\t ______                  ______                         |
                                                        |    \t\t\t| *****************************|                        |
                                                        |    \t\t\t|    What do you want to do?   |                        |
                                                        |    \t\t\t|______________________________|                        |
                                                        |                                                                               |
                                                        |    Please enter your choice:                                                  |
                                                        |     1/. Add your tasks to the list                                            |
                                                        |     2/. List all your tasks                                                   |
                                                        |     3/. Delete your tasks from the list                                       |
                                                        |     4/. Edit your tasks in the list                                           |
                                                        |     5/. Prioritize your tasks                                                 |
                                                        |	  6/. List tasks by priority                                                |
                                                        |     7/. List over due tasks                                                   |
                                                        |     8/. Quit the system                                                       |
                                                        |_______________________________________________________________________________|

                                                        """);
                        System.out.print("Enter your option: ");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        switch (choice) {
                                case 1:
                                    taskService.addTask(scanner, connection);
                                        break;
                                case 2:
                                    taskService.listAllTasks(connection);
                                    System.out.println(
                                                        "+----------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                                        break;
                                case 3:
                                    System.out.print("Please Enter task index to delete: ");
                                    int index1 = scanner.nextInt();
                                    scanner.nextLine();
                                    taskService.deleteTask(index1, connection);
                                        break;
                                case 4:
                                    System.out.print("Please Enter task index to edit: ");
                                    int index2 = scanner.nextInt();
                                    taskService.editTask(index2, scanner, connection);
                                        break; 
                                case 5:
                                	System.out.print("Please Enter task index to prioritize: ");
                                	int index3 = scanner.nextInt();
                                	System.out.print("Please Enter new task priority(1 for high, 2 for medium, 3 for low): ");
                                	int newPriority=scanner.nextInt();
                                	taskService.prioritizeTask(index3, newPriority, connection);
                                		break;
                                case 6:
                                	System.out.print("Please Enter priority to list down: ");
                                	int priority=scanner.nextInt();
                                	taskService.listTasksByPriority(priority, connection);		
                                		break;
                                case 7:
                                	taskService.listOverdueTasks(connection);
                                	System.out.println(
                                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                                case 8:
                                        System.out.println("Good bye!!!Bonne Chance!!!");
                                        break;
                                default:
                                        System.out.println("Invalid choice");
                                        break;
                        }
                } while (choice != 8);

        }
}