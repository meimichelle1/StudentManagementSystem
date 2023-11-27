package roles;

import java.util.ArrayList;
import java.util.HashMap;
import courses.Course;

/**
 * Represents a student in a student management system. 
 * Inherites from the User class and manages a list of courses and transcript. 
 * 
 * @author Michelle Chen
 * @author Evelyn Li
 * @author Gloria Chen
 */
public class Student extends User {
	// Transcript storing course IDs and corresponding grades
	HashMap<String, String> transcript = new HashMap<String, String>();
	
    // List of courses the student is enrolled in 
	private ArrayList<Course> studentCourseList = new ArrayList<Course>(); 
	
    /**
     * Constructor for Student class.
     *
     * @param ID The unique identifier for the Student. 
     */
	public Student(int ID) {
		super(ID); // Call the constructor of the superclass 
	}
	
    /**
     * Prints the grades for each course in the transcript. 
     *
     * @param courseList A list of Course objects to cross referrence course IDs with names. 
     */
	public void printGrades(ArrayList<Course> courseList) {
		for (String key : transcript.keySet()) { // Iterate over each course ID in the transcript 
			String courseName = null; // Initialize a variable to store the course name 
			
            for (Course course : courseList) { // Iterate over each course in the provided course list
				if (course.getCourse().equals(key)) { // Check if the course ID matches the key 
					courseName = course.getCourseName();  // Assign the course name if match is found 
				}
			}
            // Print the grade for the course 
			System.out.println("Grade of " + key + " " + courseName + ": " + transcript.get(key)); 
		}
	}
	
    /**
     * Retrieves the transcript of student. 
     *
     * @return A HashMap representing the student's transcript.
     */
	public HashMap<String, String> getTranscript(){
		return this.transcript; // Return the transcript HashMap
	}
	
    /**
     * Retrieves the list of courses the student is enrolled in.
     *
     * @return An ArrayList of Course object. 
     */
	public ArrayList<Course> getStudentCourseList(){
		return this.studentCourseList; //return the ArrayList of courses
	}
	
}