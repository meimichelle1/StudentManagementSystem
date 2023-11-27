package roles;

import java.util.ArrayList;
import courses.Course;

/**
 * Represents an administrator in the student management system. 
 * Inherites from the User class. 
 * 
 * @author Michelle Chen
 * @author Evelyn Li
 * @author Gloria Chen
 */
public class Admin extends User {

    /**
     * Constructor for Admin class. 
     *
     * @param ID The unique identifier for the Admin. 
     */
	public Admin(int ID) {
		super(ID); // Call the constructor of the superclass (User)
	}
}