package roles;

import java.util.ArrayList;
import courses.Course;

/**
 * Represents a professor in a student management system. 
 * Inherites from the User class and manage a list of the course they lecture.
 * 
 * @author Michelle Chen
 * @author Evelyn Li
 * @author Gloria Chen
 */
public class Professor extends User {
    // List of courses that the professor lectures
	ArrayList<Course> lectureList = new ArrayList<Course>(); 
	
    /**
     * Constructor for Professor class. 
     *
     * @param ID The unique identifier for the Professor. 
     */
	public Professor(int ID) {
		super(ID); // Call the constructor of the superclass (User)
	}
	
    /**
     * Retrieves the list of courses lectured by the professor. 
     *
     * @return An arrayList of Course objects.
     */
	public ArrayList<Course> getLectureList(){
		return this.lectureList;  // Return the list of courses
	}
}