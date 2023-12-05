import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import courses.Course;
import files.FileInfoReader;
import roles.Student;
import roles.Admin;
import roles.Professor;
import roles.User; 

/**
 * The main controller class for the Student Management System.
 * It initializes the system, reads data from files, and handles user interactions.
 * 
 * @author Michelle Chen
 * @author Evelyn Li
 * @author Gloria Chen
 */
public class Controller {

	private static Student validStudent = null; // Static variable to hold the currently logged-in student
	
	private static Professor validProfessor = null; // Static variable to hold the currently logged-in professor
	
	private static Admin validAdmin = null; // Static variable to hold the currently logged-in admin
	
	public static void main(String[] args) {
		String studentFile = "studentInfo.txt"; // File name for student data

		String professorFile = "profInfo.txt"; // File name for professor data

		String adminFile = "adminInfo.txt"; // File name for admin data

		String courseFile = "courseInfo.txt"; // File name for course data
		
		try {
			// Reading data from files and populating respective lists
			FileInfoReader.readStudentInfo(studentFile);
			FileInfoReader.readProfessorInfo(professorFile); 
			FileInfoReader.readAdminInfo(adminFile); 
			FileInfoReader.readCourseInfo(courseFile); 
		} catch (IOException e) {
			e.printStackTrace(); // Print stack trace in case of an IOException
		} 
		
		Scanner scanner = new Scanner(System.in); // Scanner for reading user input
		
		boolean runProgram = true; // Flag to control the program's main loop
		
		// Retrieving lists of students, professors, admins, and courses from FileInfoReader
		ArrayList<Student> studentList = FileInfoReader.getStudentList(); 
		ArrayList<Professor> professorList = FileInfoReader.getProfessorList(); 
		ArrayList<Admin> adminList = FileInfoReader.getAdminList();
		ArrayList<Course> courseList = FileInfoReader.getCourseList(); 
		
		// Main program loop
		while(runProgram) {
			// Displaying the main menu
			System.out.println("--------------------------"); 
			System.out.println("Students Management System");
			System.out.println("--------------------------"); 
			System.out.println("1 -- Login as a student"); 
			System.out.println("2 -- Login as a professor"); 
			System.out.println("3 -- Login as a admin"); 
			System.out.println("4 -- Quit the system"); 
			System.out.println(); 

			// Get and validate the user's menu option selection
			int option = validIntegerInput("Please enter your option, eg. '1'.", 1, 4, scanner); 
			System.out.println();

			// Handle user's menu option selection
			if(option == 1) {	
				 // If option 1 is selected, validate student login
				if (validateLogin(studentList, scanner) != "q") {
					 // If login is successful, show the student interface
					studentInterface(validStudent, courseList, scanner); 
				} else {
					// If login is not successful, print an empty line
					System.out.println(); 
				}
			} else if (option == 2) {
				// If option 2 is selected, validate professor login
				if (validateLogin(professorList, scanner) != "q") {
					// If login is successful, show the professor interface
					professorInterface(validProfessor, courseList, scanner); 
				} else {
					// If login is not successful, print an empty line
					System.out.println(); 
				}
			} else if (option == 3) {
				// If option 3 is selected, validate admin login
				if(validateLogin(adminList, scanner) != "q") {
					 // If login is successful, show the admin interface
					adminInterface(validAdmin, courseList, professorList, studentList, scanner); 
				} else {
					// If login is not successful, print an empty line
					System.out.println(); 
				}
			} else if (option == 4) {
				// If option 4 is selected, set the flag to false to exit the program loop
				runProgram = false; 
			}
		}
		scanner.close(); // Close the scanner resource
	}
	
	/**
	* Requests and validates an integer input from the user within a specified range.
	*
	* @param prompt The prompt to display to the user.
	* @param min The minimum acceptable value.
	* @param max The maximum acceptable value.
	* @param scanner The Scanner object for reading user input.
	* @return A validated integer within the specified range.
	*/
	public static int validIntegerInput(String prompt, int min, int max, Scanner scanner) {
		boolean valid = false; // Flag to indicate whether the input is valid

		int input = 0; // Variable to store the user input
		
		while (!valid) { // Loop until a valid input is received
			System.out.println(prompt); // Display the prompt to the user
			
			if (scanner.hasNextInt()) { // Check if the next input is an integer
				input = scanner.nextInt(); // Read the integer input
				
				if (input >= min && input <= max) { // Check if the input is within the specified range
					valid = true; // Set the valid flag to true as input is within range
				} else {
					System.out.println("Invalid input. Please enter a number between " + min + " and " + max + "."); // Print an error message if the input is out of range
					System.out.println(); // Print a blank line for better readability
				}
			} else {
				System.out.println("Invalid input. Please enter a number!"); // Print an error message if the input is not an integer
				System.out.println(); // Print a blank line for better readability

				scanner.next(); // Consume the invalid input to avoid an infinite loop
			}
		}
		return input; // Return the valid input
	}

	/**
	* Validates login for a user, applicable to students, professors, and admins.
	*
	* @param userList The list of users (students, professors, or admins).
	* @param scanner Scanner object for reading user input.
	* @param <T> The type of user (Student, Professor, or Admin).
	* @return The username if login is successful, "q" to quit, or null if login fails.
	*/
	public static <T extends User> String validateLogin(ArrayList<T> userList, Scanner scanner) {
		boolean login = false; // Flag to keep track of successful login

		String username = null; // Variable to store entered username

		String password = null; // Variable to store entered password

		while (!login) { // Loop until login is successful
			System.out.println("Please enter your username, or type 'q' to quit."); // Prompt for username

			username = scanner.next(); // Read the username from user input

			if (username.equalsIgnoreCase("q")) { // Check if user wants to quit
				return "q"; // Return "q" to indicate the user chose to quit
			}

			T validUser = findUserByUsername(userList, username); // Find user by username in the list

			if (validUser != null) { // Check if a user with the entered username exists
				System.out.println("Please enter your password, or type 'q' to quit."); // Prompt for password

				password = scanner.next(); // Read the password from user input

				if (password.equalsIgnoreCase("q")) { // Check if user wants to quit during password entry
					return "q"; // Return "q" to indicate the user chose to quit
				}

				if (validUser.getPassword().equals(password)) { // Validate the entered password
					login = true; // Set login flag to true if password matches

					setCurrentUser(validUser); // Set the current user to the validUser
				} else {
					System.out.println("Invalid password, try again."); // Notify user of invalid password
				}
			} else {
				System.out.println("Invalid username, try again."); // Notify user of invalid username
			}
		}
		return username; // Return the successfully logged-in user's username
	}

	/**
	* Finds a user by their username in a given list of users.
	* This method is generic and can be used for any user type that extends the User class.
	*
	* @param userList The list of users to search through.
	* @param username The username to search for.
	* @param <T> The type of user, which extends the User class.
	* @return The user if found, otherwise null.
	*/
	private static <T extends User> T findUserByUsername(ArrayList<T> userList, String username) {
		for (T user : userList) { // Iterate through each user in the list
			if (user.getUsername().equals(username)) { // Check if the current user's username matches the given username
				return user; // Return the user if a match is found
			}
		}
		return null; // Return null if no matching user is found
	}

	/**
	* Sets the current user based on the user type. This method updates the static
	* variables validStudent, validProfessor, or validAdmin based on the type of user passed.
	*
	* @param user The user object, which can be of type Student, Professor, or Admin.
	* @param <T> The type of user, extending from the User class.
	*/
	private static <T extends User> void setCurrentUser(T user) {
		if (user instanceof Student) { // Check if the user is an instance of Student
			validStudent = (Student) user; // Cast the user to Student and set it as the current valid student
		} else if (user instanceof Professor) { // Check if the user is an instance of Professor
			validProfessor = (Professor) user; // Cast the user to Professor and set it as the current valid professor
		} else if (user instanceof Admin) { // Check if the user is an instance of Admin
			validAdmin = (Admin) user; // Cast the user to Admin and set it as the current valid admin
		}
	}
	
	/**
	* Displays the main menu for student users.
	* The menu includes options to view all courses, add or drop courses, view enrolled courses,
	* view grades, and return to the previous menu.
	*
	* @param student The student for whom the menu is being displayed.
	*/
	private static void displayStudentMenu(Student student) {
		System.out.println("--------------------------");
		System.out.println("  Welcome, " + student.getName()); // Greet the student with their name
		System.out.println("--------------------------");
		System.out.println("1 -- View all courses");
		System.out.println("2 -- Add courses to your list");
		System.out.println("3 -- View enrolled courses");
		System.out.println("4 -- Drop courses in your list");
		System.out.println("5 -- View grades");
		System.out.println("6 -- Return to previous menu");
		System.out.println();
	}

	/**
	* Provides the interface for student interactions.
	* It displays a menu with options like viewing and managing courses, and viewing grades.
	* The user can continue to interact with this menu until they choose to exit.
	*
	* @param student The student who is currently logged in and interacting with the interface.
	* @param courseList A list of all available courses.
	* @param scanner Scanner object for reading user input.
	*/
	public static void studentInterface(Student student, ArrayList<Course> courseList, Scanner scanner) {
		boolean run = true; // Flag to control the loop for the interface

		ArrayList<Course> studentCourseList = student.getStudentCourseList(); // Retrieve the current student's course list
		
		while (run) { // Loop to keep the interface running until the user decides to exit
			displayStudentMenu(student); // Display the menu options specific to the student

			int option = validIntegerInput("Please enter your option, eg. '1'.", 1, 6, scanner); // Get the user's choice
			System.out.println(); 

			switch (option){ // Handle the chosen option
				case 1: 
					student.printCourseList(courseList); // Print the list of all available courses
					break; 
				case 2: 
					handleCourseAddition(student, courseList, studentCourseList, scanner); // Handle adding a course to the student's list
					break;
				case 3:
					System.out.println("The courses in your list:");
					student.printCourseList(studentCourseList); // Print the list of courses the student is enrolled in
					break;
				case 4:
					handleCourseDropping(student, courseList, studentCourseList, scanner); // Handle dropping a course from the student's list
					break;
				case 5:
					System.out.println("Here are the courses you already taken, with your grade in a letter format"); 
					student.printGrades(courseList); // Print the student's grades for completed courses
					System.out.println(); 
					break;
				case 6:
					run = false; // Exit the interface loop
					break;
			}
		}
	}
	
	/**
	* Handles the process of adding a course to a student's course list.
	* It prompts the user to enter a course ID to add. The user can exit this process by typing 'q'.
	*
	* @param student The student who is adding courses to their list.
	* @param courseList The list of all available courses.
	* @param studentCourseList The current list of courses the student is enrolled in.
	* @param scanner Scanner object for reading user input.
	*/
	private static void handleCourseAddition(Student student, ArrayList<Course> courseList, ArrayList<Course> studentCourseList, Scanner scanner) {
		System.out.println("The courses in your list:");
		student.printCourseList(studentCourseList); // Display the current list of courses the student is enrolled in

		String courseToAdd = null; // Variable to store the course ID to be added

		while (true) { // Infinite loop to handle course addition until the user decides to quit
			System.out.println("Please select the course ID you want to add to your list, eg. 'CIT590'.");
			System.out.println("Or enter 'q' to return to the previous menu."); // Instructions for the user

			if (scanner.hasNext()) { // Check if there is an input from the user
				courseToAdd = scanner.next(); // Read the input course ID

				if (courseToAdd.equals("q")) { // Check if the user wants to quit the process
					break; // Exit the loop if user enters 'q'
				}

				studentCourseList = student.addCourse(courseToAdd, courseList, studentCourseList); // Attempt to add the chosen course to the student's list
			}
		}
	}

	/**
	* Handles the process of dropping a course from a student's course list.
	* It prompts the user to enter a course ID to drop. The user can exit this process by typing 'q'.
	*
	* @param student The student who is dropping courses from their list.
	* @param courseList The list of all available courses (unused in the current implementation but may be useful for future enhancements).
	* @param studentCourseList The current list of courses the student is enrolled in.
	* @param scanner Scanner object for reading user input.
	*/
	private static void handleCourseDropping(Student student, ArrayList<Course> courseList, ArrayList<Course> studentCourseList, Scanner scanner) {
		String courseToDrop = null; // Variable to store the course ID to be dropped

		while (true) { // Infinite loop to handle course dropping until the user decides to quit
			System.out.println("The courses in your list:");
			student.printCourseList(studentCourseList); // Display the current list of courses the student is enrolled in

			System.out.println("Please enter the ID of the course which you want to drop, eg. 'CIT591'.");
			System.out.println("Or enter 'q' to return to the previous menu"); // Instructions for the user

			if (scanner.hasNext()) { // Check if there is an input from the user
				courseToDrop = scanner.next(); // Read the input course ID

				if (courseToDrop.equals("q")) { // Check if the user wants to quit the process
					break; // Exit the loop if user enters 'q'
				}

				studentCourseList = student.dropCourse(courseToDrop, courseList, studentCourseList); // Attempt to drop the chosen course from the student's list
			}
		}
	}

	/**
	* Displays the menu options available to the professor.
	*
	* @param professor The professor for whom the menu is being displayed.
	*/
	private static void displayProfessorMenu(Professor professor) {
		System.out.println("--------------------------");
		System.out.println("  Welcome, " + professor.getName()); // Greet the professor with their name
		System.out.println("--------------------------");
		System.out.println("1 -- View given courses");
		System.out.println("2 -- View student list of the given course");
		System.out.println("3 -- Return to the previous menu");
		System.out.println();
	}

	/**
	* Provides the interface for professor interactions.
	* Allows the professor to view their courses, view student lists for each course, or return to the previous menu.
	*
	* @param professor The professor who is currently logged in and interacting with the interface.
	* @param courseList A list of all available courses.
	* @param scanner Scanner object for reading user input.
	*/
	public static void professorInterface(Professor professor, ArrayList<Course> courseList, Scanner scanner) {
		boolean run = true; // Flag to control the loop for the interface

		ArrayList<Course> lectureList = professor.getLectureList(); // Retrieve the list of courses that the professor is currently teaching
		
		while (run) { // Loop to keep the interface running until the professor decides to exit
			displayProfessorMenu(professor); // Display the menu options specific to professors

			int option = validIntegerInput("Please enter your option, eg. '1'.", 1, 3, scanner); // Get the professor's choice from the menu
			System.out.println(); 

			switch (option) { // Handle the chosen menu option
				case 1:
					System.out.println("------------The course list------------"); // Print the heading for the course list
					professor.printCourseList(lectureList); // Call the method to print the list of courses the professor is teaching
					break;
				case 2:
					handleViewingStudents(professor, courseList, lectureList, scanner); // Handle the option for viewing the student list for a selected course
					break;
				case 3:
					run = false; // Set the flag to false to exit the interface loop
					break;
			}
		}
	}

	/**
	* Handles viewing the list of students enrolled in a selected course.
	*
	* @param professor The professor viewing the student list.
	* @param courseList List of all courses.
	* @param lectureList The list of courses the professor is giving.
	* @param scanner Scanner object for reading user input.
	*/
	private static void handleViewingStudents(Professor professor, ArrayList<Course> courseList, ArrayList<Course> lectureList, Scanner scanner) {
		String courseToView = null; // Variable to store the ID of the course to view

		while (true) { // Infinite loop to handle the process of viewing students
			System.out.println("Please enter the course ID, eg. 'CIS519'.");
			System.out.println("Or type 'q' to quit."); // Instructions for the user

			if (scanner.hasNext()) { // Check if there is an input from the user
				courseToView = scanner.next(); // Read the course ID

				if (courseToView.equals("q")) { // Check if the user wants to quit the process
					break; // Exit the loop if user enters 'q'
				}

				professor.viewStudentInCourse(courseToView, courseList, lectureList); // Call the method to display the list of students enrolled in the selected course
			}
		}
	}
	
	/**
	 *  This is used to display admin menu 
	 *  
	 */
	private static void displayAdminMenu(Admin admin) {
		System.out.println("--------------------------");
		System.out.println("  Welcome, " + admin.getName());
		System.out.println("--------------------------");
		System.out.println("1 -- View all courses");
		System.out.println("2 -- Add new courses");
		System.out.println("3 -- Delete courses");
		System.out.println("4 -- Add new professor");
		System.out.println("5 -- Delete professor");
		System.out.println("6 -- Add new Student");
		System.out.println("7 -- Delete student");
		System.out.println("8 -- return to previous menu");
		System.out.println(); 
	}
	
	/**
	 * Creates an admin interface for managing courses, professors, and students.
	 *
	 * @param admin    handling the interface.
	 * @param courseList   List of courses.
	 * @param professorList List of professors.
	 * @param studentList  List of students.
	 * @param scanner      Scanner object for user input.
	 */
	public static void adminInterface(Admin admin, ArrayList<Course> courseList, ArrayList<Professor> professorList, ArrayList<Student> studentList, Scanner scanner) {
	    boolean run = true; // boolean value for the loop

	    while (run) {
	    	// displays the admin menu 
	        displayAdminMenu(admin);

	        // validate the input for menu selection 
	        int option = validIntegerInput("Please enter your option, eg. '1'.", 1, 8, scanner);
	        System.out.println();

	        switch (option) {
	            case 1:
	                admin.printCourseList(courseList);
	                break;
	            case 2:
	                handleCourseCreation(admin, courseList, professorList, scanner);
	                break;
	            case 3:
	                handleCourseDeletion(admin, courseList, scanner);
	                break;
	            case 4:
	                handleProfessorAddition(admin, professorList, scanner);
	                break;
	            case 5:
	                handleProfessorDeletion(admin, professorList, scanner);
	                break;
	            case 6:
	                handleStudentAddition(admin, studentList, scanner);
	                break;
	            case 7:
	                handleStudentDeletion(admin, studentList, scanner);
	                break;
	            case 8:
	                run = false;
	                break;
	        }
	    }
	}

	/**
	 * This method handles the course creation
	 * @param admin of this action
	 * @param courseList that system currently has
	 * @param professorList that system currently has
	 * @param scanner object for user input
	 */
	private static void handleCourseCreation(Admin admin, ArrayList<Course> courseList, ArrayList<Professor> professorList, Scanner scanner) {
	    
		boolean toMain = false;
	    String course = null;
	    String courseName = null;
	    String startTime = null;
	    String endTime = null;
	    String lectureDate = null;
	    String capacity = null;
	    Course newAdd = null;
	    toMain = false; 
	    
		while (!toMain) {
			System.out.println("Please enter the course ID, or type 'q' to end."); // Print instructions
			
             // Add a new course with user input
			if(scanner.hasNext()) {
				course = scanner.next().trim(); 
				
                // If user decided to end 
				if (course.equalsIgnoreCase("q")) {
					toMain = true; 
					break; 
				}
				
                // Check for course existence, and only add if course not already on the list
				if(!admin.checkIfCourseExisted(course, courseList)) {
					newAdd = new Course(course); 
					courseList.add(newAdd); 
					break; 
				}
			}
		}
		
		if(!toMain) {
			while(true) {
				System.out.println("Please enter the course name, or type 'q' to end."); // Print instructions
				if(scanner.hasNextLine()) {
					scanner.nextLine(); // Take any remaining newline characters
				}
				
				if (scanner.hasNextLine()) {
					courseName = scanner.nextLine().trim(); // Read user-entered course name
					
                    // If user decided to end 
					if (courseName.equalsIgnoreCase("q")) {
						toMain = true; 
						break; 
					}
					
					newAdd.setCourseName(courseName); // Set the course name for the new course added
					break; // break loop
				}
			}
		}
		
		if(!toMain) {
			while(true) {
				System.out.println("Please enter the course start time, or type 'q' to end. eg. '19:00'"); // Print instructions
							
				if(scanner.hasNext()) {
					startTime = scanner.next().trim();  // Read the start time that the user entered

                    // If user decided to end 		
					if (startTime.equalsIgnoreCase("q")) {
						toMain = true; 
						break; 
					}
								
					if(newAdd.validateStartTime(startTime)) { // Validate entered start time
						break; // break loop
					}
				}
				
			}
		}
		
		if(!toMain) {
			while(true) {
				System.out.println("Please enter the course end time, or type 'q' to end. eg. '20:00'"); // print instructions
					
				if(scanner.hasNext()) {
					endTime = scanner.next();  // Read the end time entered by users
					
                    // If user decided to end
					if (endTime.equalsIgnoreCase("q")) {
						toMain = true; 
						break; 
					}
					
					if(newAdd.validateEndTime(endTime)) { // Validate end time 
						break; // break loop 
					}
				}
			}
		}
		
		if(!toMain) {
			while(true) {
				System.out.println("Please enter the course date, or type 'q' to end. eg. 'MW'"); // print instructions
					
				if(scanner.hasNext()) {
					lectureDate = scanner.next(); // Read lecture date entered by users
					
                    // If user decided to end
					if (lectureDate.equalsIgnoreCase("q")) {
						toMain = true;
						break; 
					}
					
					if (newAdd.validateLectureDate(lectureDate)) { // Validate the lecture date entered by the users
						break; // break loop
					}
				}
			}
		}
		
		if(!toMain) {
			while(true) {
				System.out.println("Please enter the course capacity, or type 'q' to end. eg. 'MW'"); // print instructions
					
				if (scanner.hasNext()) {
					capacity = scanner.next(); // read the capacity entered by users
					
                    // if users decided to end 
					if (capacity.equalsIgnoreCase("q")) {
						toMain = true; 
						break; 
					}
					
					if(newAdd.validateCapacity(capacity)) { // validate entered capacity
						break; // break loop
					}
				}
			}
		}
		
		if(!toMain) {
			while(true) {
				String input; 
				String profID; 
				String profName; 
				String profUsername; 
				Professor newProf = null;
				
				System.out.println("Please enter the course lecturer's id, or type 'q' to end. eg. '001'"); // prof name input prompt
					
				if (scanner.hasNext()) {
					input = scanner.next(); // read user input
					
                    // user decides to end, break loop
					if (input.equalsIgnoreCase("q")) {
						toMain = true; 
						break;
					}
						
					if(admin.validateID(input)) { // validate entered ID
						Professor prof = null; 
						prof = admin.checkIfProfessorExisted(input, professorList); // check if professor already exists
						
						if(prof != null) {
							if(newAdd.checkIfCourseInProfListAndConflict(prof, newAdd)) { // check if the course conflicts with the professor's schedule
								System.out.print("Successfully added the course: "); // display success message if added 
								System.out.println(newAdd.toPrint()); // print details
								System.out.println();
								toMain = true; // set flag to exit loop
								break; // break loop
							}
						} else {
							System.out.println("The professor isn't in the system, please add this professor first"); // add professor to list prompt
							while(true) {
								System.out.println("Please enter the professor's ID, or type 'q' to end"); // prompt for the missing professor's ID
								
								if (scanner.hasNext()) {
									profID = scanner.next(); // read prof ID input
									
                                    // if user decided to end
									if (profID.equalsIgnoreCase("q")) {
										toMain = true; 
										break; // break loop
									}
									
									if(admin.validateID(profID)) { // validate the entered professor's ID
										newProf = admin.checkIfProfessorExisted(profID, professorList); // check if the professor already exists
										if(newProf == null) {
											newProf = new Professor(Integer.parseInt(profID)); // create a new professor object
											professorList.add(newProf); // add the new professor to the list
											break; // break loop
										}
										
									}
								}
							}
							
							if(!toMain) {
								while(true) {
									System.out.println("Please enter professor's name, or type 'q' to end"); // prof name input prompt
									if(scanner.hasNextLine()) {
										scanner.nextLine(); // clear buffer
									}
									if (scanner.hasNextLine()) {
										profName = scanner.nextLine().trim(); // Read the professor's name input
										
                                        // if user decides to end
										if(profName.equalsIgnoreCase("q")) {
											toMain = true; 
											break; 
										}
										
										newProf.setName(profName); // Set the professor's name
										newAdd.setLecturer(profName); // Set the lecturer for the course
										break; // break loop
									}
								}
								
								
								while (true) {
									System.out.println("Please enter a username"); // Username prompt 
									if(scanner.hasNext()) {
										profUsername = scanner.next(); // Read the username input
										
										if(admin.checkIfProfUsernameExisted(profUsername, professorList)) {
											continue; // If the username already exists, continue to prompt for another username
										} else {
											newProf.setUsername(profUsername);// Set the username for the new professor
											break; // break loop
										}
									}
								}
								
								while (true) {
									System.out.println("Please enter a password"); // password prompt
									
									if(scanner.hasNext()) {
										newProf.setPassword(scanner.next()); // set password for new prof
										break;  // break loop
									}
								}
								
								if(!toMain) {
									System.out.println("Successfully added the new professor: " + newProf.getID() + " " + newProf.getName());  // Confirm successful addition of prof
									System.out.print("Successfully added the course: "); // Confirm successful addition of course
									System.out.println(newAdd.toPrint()); 
									System.out.println();
									toMain = true; // Set the flag toMain to true
									break;
								}
							}
						}
					}
				}
			}
		}
	}


	/**
	 * This method handles the professor addition
	 * @param admin that handles the action 
	 * @param professorList that system currently has
	 * @param scanner object for input 
	 * @return
	 */
	private static Professor handleProfessorAddition(Admin admin, ArrayList<Professor> professorList, Scanner scanner) {
	    boolean toMain = false;
	    String input;
	    String name;
	    String username;
		Professor anotherNew = null; 
		
		while(!toMain) { 
			System.out.println("Please eneter the professor's ID, or type 'q' to quit"); 
			if(scanner.hasNext()) {
				input = scanner.next(); 
				
				if (input.equalsIgnoreCase("q")) { // check if user wants to quit 
					toMain = true; 
					break; 
				}
				
				if(admin.validateID(input)) { // validate the input to see if it is acceptable id value 
					
					// check if entered professor is within the current professor list
					anotherNew = admin.checkIfProfessorExisted(input, professorList); 
					if (anotherNew != null) {
						System.out.println("The ID already exists"); 
						continue; 
					} else {
						// add professor if he/she doesn't already exist
						anotherNew = new Professor(Integer.parseInt(input)); 
						professorList.add(anotherNew); 
						break; 
					}
				}
			}
		}
		while(!toMain) { // get input for professor name 
			System.out.println("Please enter professor's name, or type 'q' to end"); 
			
			if(scanner.hasNextLine()) {
				scanner.nextLine(); 
			}
			
			if(scanner.hasNextLine()) {
				name = scanner.nextLine(); 
				
				// check if user wants to quit current process 
				if (name.equalsIgnoreCase("q")) {
					toMain = true; 
					break; 
				}
				
				// set professor name 
				anotherNew.setName(name);
				break; 
			}
		}
		
		while (true) { // process to check username 
			System.out.println("Please enter a username");
			if(scanner.hasNext()) {
				username = scanner.next(); 
				
				// check if professor username is being used by another user 
				if(admin.checkIfProfUsernameExisted(username, professorList)) {
					continue; 
				} else {
					// add the username if not already existent 
					anotherNew.setUsername(username);
					break; 
				}
			}
		}
		
		while (true) { // process to put in password 
			System.out.println("Please enter a password");
			
			if(scanner.hasNext()) {
				// set password 
				anotherNew.setPassword(scanner.next());
				toMain = true; 
				break; 
			}
		}
		
		System.out.println("You have successfully added a professor: "+ anotherNew.getName());
		return anotherNew;
	}

	/**
	 * This method is used to prompts 
	 * @param prompt that user wants to input
	 * @param scanner object for input results 
	 * @return user input 
	 */
	private static String promptForInput(String prompt, Scanner scanner) {
	    System.out.println(prompt);
	    return scanner.next().trim(); // trim response and return user input 
	}
	
	
	/**
	 * This handles the course deletion
	 * @param admin that handles the action
	 * @param courseList of what the system currently has
	 * @param scanner object for user input 
	 */
	private static void handleCourseDeletion(Admin admin, ArrayList<Course> courseList, Scanner scanner) {
	    boolean toMain = false;
	    String deCourse;

	    while (!toMain) {
	        deCourse = promptForInput("Please enter course to delete, or type 'q' to end.", scanner);

	        // check if user wants to quit 
	        if (deCourse.equalsIgnoreCase("q")) {
	            toMain = true;
	            break;
	        }

	        // check if the course exists in the current list 
	        if (admin.checkIfCourseExisted(deCourse, courseList)) {
	            System.out.println("Delete the course now...");
	            for (Course c : courseList) {
	                if (c.getCourse().equals(deCourse)) {
	                    courseList.remove(c);
	                    System.out.println("The course has successfully removed from the system.");
	                    break;
	                }
	            }
	            toMain = true;
	        } else {
	        	// if course does not exist, print below 
	            System.out.println("The course doesn't exist, please try again.");
	        }
	    }
	}

	/**
	 * This handles the professor deletion 
	 * @param admin that handles the action 
	 * @param professorList that system currently has
	 * @param scanner object for user input
	 */
	private static void handleProfessorDeletion(Admin admin, ArrayList<Professor> professorList, Scanner scanner) {
	    boolean toMain = false;
	    String deProf;

	    while (!toMain) {
	        deProf = promptForInput("Please enter professor's ID to delete, or type 'q' to quit. eg. 001", scanner);

	        // check if user wants to quit the current process 
	        if (deProf.equalsIgnoreCase("q")) {
	            toMain = true;
	            break;
	        }

	        // validate the professor id to see if it is valid id format 
	        if (admin.validateID(deProf)) {
	        	
		        // validate the professor id to see if it exists in the system 
	            Professor deleteProf = admin.checkIfProfessorExisted(deProf, professorList);
	            if (deleteProf != null) {
	            	
	            	// if pass all check, delete the professor 
	                System.out.println(deleteProf.getName() + " Professor has successfully deleted.");
	                professorList.remove(deleteProf);
	                toMain = true;
	            } else {
	            	
	            	// print below when professor is not found 
	                System.out.println("Professor not found, please try again.");
	            }
	        } else {
	        	
	        	// print below if the format for input is not valid 
	            System.out.println("Invalid professor ID, please try again.");
	        }
	    }
	}

	/**
	 * This handles the student addition
	 * @param admin that handles the action 
	 * @param studentList that system currently has 
	 * @param scanner object for user input
	 */
	private static void handleStudentAddition(Admin admin, ArrayList<Student> studentList, Scanner scanner) {
		boolean toMain = false; 
		String input;
		String name; 
		String username; 
		Student newStudent = null; 
		
		while(!toMain) { 
			System.out.println("Please eneter the student's ID, or type 'q' to quit"); 
			if(scanner.hasNext()) {
				input = scanner.next(); 
				
		        // check if user wants to quit the current process 				
				if (input.equalsIgnoreCase("q")) {
					toMain = true; 
					break; 
				}
				
		        // validate the student id to see if it is valid id format 				
				if(admin.validateID(input)) {
					newStudent = admin.checkIfStudentExisted(input, studentList); // check to see if student already exists 
					if (newStudent != null) { // if it does then return below output
						System.out.println("The ID already exists"); 
						continue; 
					} else {
						// add student if it does not already exists 
						newStudent = new Student(Integer.parseInt(input)); 
						studentList.add(newStudent); 
						break; 
					}
				} else {
					continue; 
				}
			}
		}
		while(!toMain) { // process to check student name 
			System.out.println("Please enter student's name, or type 'q' to end"); 
			
			if(scanner.hasNextLine()) {
				scanner.nextLine(); 
			}
			
			if(scanner.hasNextLine()) {
				name = scanner.nextLine(); 
		
				// check to see if user wants to quit the process 
				if (name.equalsIgnoreCase("q")) {
					toMain = true; 
					break; 
				}
				
				// set student name 
				newStudent.setName(name);
				break; 
			}
		}
		
		while (true) { // process to check student username
			System.out.println("Please enter a username");
			if(scanner.hasNext()) {
				username = scanner.next(); 
				
				// check to see if student user name already exists 
				if(admin.checkIfStudentUsernameExsited(username, studentList)) {
					continue; 
				} else {
					
					// set username 
					newStudent.setUsername(username);
					break; 
				}
			}
		}
		
		while (true) { // process to check student password 
			System.out.println("Please enter a password");
			
			if(scanner.hasNext()) {
				// set student password 
				newStudent.setPassword(scanner.next());
				toMain = true; 
				break; 
			}
		}
		
		// print successful message for adding the new student
		System.out.println("You have successfully added student: "+newStudent.getName());
	}

	/**
	 * This handles the student deletion 
	 * @param admin that handles the action 
	 * @param studentList that system currently has 
	 * @param scanner object for user input
	 */
	private static void handleStudentDeletion(Admin admin, ArrayList<Student> studentList, Scanner scanner) {
	    boolean toMain = false;
	    String deStud;

	    while (!toMain) {
	        deStud = promptForInput("Please enter student's ID to delete, or type 'q' to quit. eg. 001", scanner);

	        // check if user wants to quit the current process 
	        if (deStud.equalsIgnoreCase("q")) {
	            toMain = true;
	            break;
	        }

	        if (admin.validateID(deStud)) { // validate student id input 
	        	
	        	// check to see if student already exists 
	            Student deleteStudent = admin.checkIfStudentExisted(deStud, studentList);
	            if (deleteStudent != null) {
	            	
	            	// delete student and print a successful message 
	                System.out.println(deleteStudent.getName() + " Student has successfully deleted.");
	                studentList.remove(deleteStudent);
	                toMain = true;
	            } else {
	            	
	            	// print below message for when student is not found 
	                System.out.println("Student not found, please try again.");
	            }
	        } else {
	        	
	        	// print below message for when the input format id is not valid 
	            System.out.println("Invalid student ID, please try again.");
	        }
	    }
	}

}