package roles;

import java.util.ArrayList;
import courses.Course;

/**
 * Abstract class User representing a general user in a student management system.
 * Includes common properties and functionalities for all user types. 
 * 
 * @author Michelle Chen
 * @author Evelyn Li
 * @author Gloria Chen
 */
public abstract class User {
    /*
     * Name of the User. 
     */
	private String name; 
	
    /*
     * Username for the user account. 
     */
	private String username; 
	
    /*
     * Password for the user account. 
     */
	private String password; 
	
    /*
     * Unique identifier for the user. 
     */
	private int ID; 
	
    /*
     * Course that conflicts with the user's schedule
     */
	Course conflictCourse; 
	
	/*
     * Constructor for User.
     *
     * @param ID Unique identifier for the user.
     */
	public User(int ID) {
		this.ID = ID; // Set the ID of the user
	}
	
    /*
     * Set the user's name.
     */
	public void setName(String name) {
		this.name = name; // Assigns the provided name to user
	}
	
    /* 
     * Sets the user's username.
     */
	public void setUsername(String username) {
		this.username = username; // Assigns the provided username to user 
	}
	
    /*
     * Sets the user's password
     */
	public void setPassword(String password) {
		this.password = password; // Assigns the provided password to user 
	}
	
    /*
     * Get the user's name.
     *
     * @return the user's name 
     */
	public String getName() {
		return this.name; // Retrieves the name of the user 
	}
	
    /*
     * Get the user's ID.
     *
     * @return the user's ID 
     */
	public int getID() {
		return this.ID; // Retrieves the ID of the user 
	}
	
    /*
     * Get the user's username.
     *
     * @return the user's username 
     */
	public String getUsername() {
		return this.username; // Retrieves the username of the user 
	}
	
    /*
     * Get the user's password.
     *
     * @return the user's password 
     */
	public String getPassword() {
		return this.password; // Retrieves the password of the user 
	}
	
    /*
     * Get the user's conflicting courses.
     *
     * @return the conflicting course, if any. 
     */
	public Course getConflictCourse() {
		return conflictCourse; // Retrieves the course that conflicts with the user's schedule 
	}
	
    /* 
     * Converts a time string in the format "HH:MM" to the total number of minutes.
    * 
    * @param time The time string to convert.
    * @return The total number of minutes as an integer.
     */
	public int convertToMinute(String time) {
		String[] times = time.split(":"); // Split the time string into an array 

		int hours = Integer.parseInt(times[0]); // Parse the hours part of the time string to an integer 

		int minutes = Integer.parseInt(times[1]); // Parse the minutes part of the time string to an inetger

		return hours * 60 + minutes; // Calculate total minutes (hours converted to minute + minute)
	}
	
	/**
    * Checks if a given course has a time conflict with any course in a provided list of courses.
    * A conflict occurs if the time of the given course overlaps with any course in the list.
    *
    * @param course1 The course to check for conflicts.
    * @param courseList The list of courses to compare against for conflicts.
    * @return True if there is a conflict; False otherwise.
    */
	public boolean checkIfCourseConflict(Course course1, ArrayList<Course> courseList) {
		int targetStart = convertToMinute(course1.getCourseStart()) ; // Convert start time of course to minute 

		int targetEnd = convertToMinute(course1.getCourseEnd()); // Convert end time of course to minute 
		
		for (Course c : courseList) { // Iterate through each course in the course list 
			int courseStart = convertToMinute(c.getCourseStart()); // Convert start time of the current course to minute 
			
            int courseEnd = convertToMinute(c.getCourseEnd()); // Convert end time of the current course to minute 
			
			// Check for time overlap between course and current course 
			if ((targetStart >= courseStart && targetStart < courseEnd) || // Start of course is during the current course time 
					(targetEnd > courseStart && targetEnd <= courseEnd) || // End of course is during the current course time 
					(targetStart <= courseStart && targetEnd >= courseEnd)) { // Course completely overlaps the current course 
				this.conflictCourse = c; // Assign conflicting course to conflictingCourse 
				
                return true; // Return true indicating a conflict existed 
			} 
		}
		return false; // Return false as no conflict were found
	}
	
    /**
    * Adds a course to the student's course list if it meets certain conditions.
    * The method checks for course existence, duplication, conflicts, and capacity before adding.
     *
    * @param course The course identifier to be added.
    * @param courseList The complete list of available courses.
    * @param studentCourseList The current list of courses the student is enrolled in.
    * @return Updated student course list after attempting to add the course.
    */
	public ArrayList<Course> addCourse(String course, ArrayList<Course> courseList, ArrayList<Course> studentCourseList) {
		boolean courseFound = false; // Flag to check if the course is found in the course list 

		for (Course c : courseList) { // Iterate through all course in the course list 
			if (c.getCourse().equals(course)) { // Check if the course identifier matches 
				courseFound = true; // Set course found flag to true
				
				if (studentCourseList.contains(c)) { // Check if student is already enrolled in the course 
					System.out.println("The course you selected is already in your list"); 
					System.out.println(); 

					return studentCourseList; // Return the current course list without modification 
				}
				
				if (checkIfCourseConflict(c, studentCourseList)) { // Check for scheduling conflicts with existing courses
					System.out.println("The course you selected has time conflict with " + conflictCourse.getCourse() + " " + conflictCourse.getCourseName() + "."); 
                    System.out.println(); 

					return studentCourseList; // Return the current course list without modification 
				}
				
				if (c.getCourseStudent() + 1 > c.getCourseCapacity()) { // Check if the student exceeds course capacity 
					System.out.println("The course has reached the maximum, no more space to add.");
					System.out.println(); 

					return studentCourseList; // Return the current course list without modification 
				}
							
                // If none conditions above meet
				studentCourseList.add(c); // Add the course to the student's course list 

				c.setCourseStudent(); // Update the course's student count 

				c.getStudentInCourseList().add((Student) this); // Add this student to the course's student list 

				System.out.println("Course added successfully"); // Print out the successfully message for user 
				System.out.println(); 

				return studentCourseList; // Return the updated course list 
			}
		}
		if (!courseFound) { // Check if the course is not found in the course list 
			System.out.println("The course is not existed in the course list."); 
			System.out.println(); 
		}
		
		return studentCourseList; // Return the student's current course list 
	}
	
    /**
    * Removes a course from the student's course list if the course is found and currently enrolled.
    *
    * @param course The course identifier to be dropped.
    * @param courseList The complete list of available courses.
    * @param studentCourseList The current list of courses the student is enrolled in.
    * @return Updated student course list after attempting to drop the course.
    */
	public ArrayList<Course> dropCourse(String course, ArrayList<Course> courseList, ArrayList<Course> studentCourseList) {
		boolean courseFound = false; // Flag to track if the course is found in the course list

		for (Course c: courseList) { // Iterate through each course in the course list
			if (c.getCourse().equals(course)) { // Check if the course identifier matches
				courseFound = true; // Set course found flag to true
				
				if (!studentCourseList.contains(c)) { // Check if the student is not enrolled in the course
					System.out.println("The course isn't in your schedule."); 
					System.out.println(); 

					return studentCourseList; // Return the current course list without modification
				}
				
				studentCourseList.remove(c); // Remove the course from the student's course list

				c.deductCourseStudent(); // Deduct one student from the course's enrollment count

				c.getStudentInCourseList().remove((Student) this); // Remove this student from the course's student list

				System.out.println("Course dropped successfully "); // Print out the drop successfully message 
				System.out.println(); 

				return studentCourseList; // Return the updated course list
			}
		}
		if (!courseFound) { // Check if the course was not found in the course list
			System.out.println("The course is not existed in the course list."); 
			System.out.println(); 
		}

		return studentCourseList; // Return the current course list, as no changes were made
	}
	
    /**
    * Displays the students enrolled in a specified course.
    * 
    * @param course       The name or identifier of the course to search for.
    * @param courseList   An ArrayList of Course objects representing all available courses.
    * @param lectureList  An ArrayList of Course objects representing courses the user has access to.
    */
	public void viewStudentInCourse(String course, ArrayList<Course> courseList, ArrayList<Course> lectureList) {
		for (Course c: courseList) { // Loop through each course in the course list
			if (c.getCourse().equals(course)) { // Check if the current course matches the specified course
				
				if (!lectureList.contains(c)) { // Check if the user does not have access to the course
					System.out.println("This is not your course, you don't have permission to view.");
					System.out.println(); 
					return; // Exit the method
				}
				
				System.out.println("Students in your course " + c.getCourse() + " " + c.getCourseName() + ": "); // Print the course information and the list of enrolled students
				
                for (Student s: c.getStudentInCourseList()) { // Loop through each student in the course
					System.out.println(s.getID() + " " + s.getName());  // Print student ID and name
				}
				System.out.println(); 
				return; // Exit the method after displaying the information
			}
		}
		
		System.out.println("The course is not existed in the course list."); // If the course was not found in the course list, inform the user
		System.out.println(); 

	}
	
    /**
    * Prints the details of each course in a provided list.
    * 
    * @param list  An ArrayList of Course objects to be printed.
    */
	public void printCourseList(ArrayList<Course> list) {
		for (Course c : list) { // Loop through each Course object in the provided list
			System.out.println(c.toPrint()); 
		}
		System.out.println(); // Print a blank line for better formatting after listing all courses
	}
	
    /**
    * Checks if a specified course exists in a given list of courses.
    * 
    * @param course      The name or identifier of the course to search for.
    * @param courseList  An ArrayList of Course objects representing all available courses.
    * @return            True if the course exists in the list, false otherwise.
    */
	public boolean checkIfCourseExisted(String course, ArrayList<Course> courseList){
		for (Course c: courseList) { // Loop through each Course object in the provided course list
			if (c.getCourse().equals(course)) { // Check if the current course's identifier matches the specified course
				System.out.println("The course already exist"); // Inform the user that the course already exists

				return true; // Return true indicating the course is found
			}
		}
		
		return false; // Return false if the course is not found in the course list
	}
	
    /**
    * Validates if the given ID is a three-digit number.
    * 
    * @param ID  The ID string to be validated.
    * @return    True if the ID is a three-digit number, false otherwise.
    */
	public boolean validateID(String ID) {
		if (ID.length() != 3) {  // Check if the length of the ID is not exactly 3 characters
			System.out.println("Invalid ID input, it has to be between 001 to 999"); // Print an error message indicating the ID format is incorrect

			return false;  // Return false as the ID is not valid
		}
		
		try {
			Integer.parseInt(ID); // Attempt to parse the ID as an integer to ensure it's a number
			
			return true; // Return true if the ID is successfully parsed as an integer
			
		} catch(NumberFormatException e) {
			System.out.println("Invalid ID input, it has to be number between 001 to 999"); // Catch the exception if ID is not a number and print an error message
		}
		return false;  // Return false as the ID is not valid
	}
	
    /**
    * Checks if a professor with the specified ID exists in the given list.
    * 
    * @param ID             The ID of the professor to search for.
    * @param professorList  An ArrayList of Professor objects.
    * @return               The Professor object if found, null otherwise.
    */
	public Professor checkIfProfessorExisted(String ID, ArrayList<Professor> professorList) {
		int id = Integer.parseInt(ID); // Convert the string ID to an integer
		
		for (Professor p : professorList) { // Loop through each Professor object in the provided list
			if(p.getID() == id) { // Check if the current professor's ID matches the specified ID
				return p; // Return the Professor object if a match is found
			}
		}
		return null; // Return null if no matching Professor is found in the list		 
	}
	
    /**
    * Checks if a given username already exists in the professor list.
    * 
    * @param username       The username to check.
    * @param professorList  An ArrayList of Professor objects.
    * @return               True if the username exists, false otherwise.
    */
	public boolean checkIfProfUsernameExisted(String username, ArrayList<Professor> professorList) {
		for (Professor p: professorList) { // Loop through each Professor object in the provided list
			if(p.getUsername().equals(username)) { // Check if the current professor's username matches the provided username
				System.out.println("The username you entered is not available.");  // Print a message indicating the username is not available

				return true;  // Return true as the username already exists
			}
		}
		return false; // Return false if no matching username is found in the list
	}
	
    /**
    * Checks if a student with the given ID exists in the student list.
    * 
    * @param ID           The ID of the student to search for.
    * @param studentList  An ArrayList of Student objects.
    * @return             The Student object if found, null otherwise.
    */
	public Student checkIfStudentExisted(String ID, ArrayList<Student> studentList) {
		int id = Integer.parseInt(ID); // Convert the string ID to an integer

		for (Student s: studentList) { // Loop through each Student object in the provided list
			if (s.getID() == id) { // Check if the current student's ID matches the specified ID
				return s; // Return the Student object if a match is found
			}
		}
		return null; // Return null if no matching Student is found in the list
	}
	
    /**
    * Checks if a specific username already exists in the list of students.
    * 
    * @param username     The username to be checked.
    * @param studentList  An ArrayList of Student objects.
    * @return             True if the username exists, false otherwise.
    */
	public boolean checkIfStudentUsernameExsited(String username, ArrayList<Student> studentList) {
		for (Student s: studentList) { // Loop through each Student object in the provided list
			if(s.getUsername().equals(username)) { // Check if the current student's username matches the provided username
				System.out.println("The username you entered is not available."); // Print a message indicating the username is not available

				return true; // Return true, indicating the username already exists
			}
		}
		return false; // Return false if no matching username is found in the list
	}
}