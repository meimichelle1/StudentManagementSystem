package roles;

import java.util.ArrayList;

import courses.Course;

public class Professor extends User {
	ArrayList<Course> lectureList = new ArrayList<Course>(); 
	
	public Professor(int ID) {
		super(ID);
	}
	
	public ArrayList<Course> getLectureList(){
		return this.lectureList; 
	}
}
