package roles;

import java.util.ArrayList;
import java.util.HashMap;

import courses.Course;

public class Student extends User {
	
	HashMap<String, String> transcript = new HashMap<String, String>();
	
	private ArrayList<Course> studentCourseList = new ArrayList<Course>(); 
	
	public Student(int ID) {
		super(ID);
	}
	
	public void printGrades(ArrayList<Course> courseList) {
		for (String key : transcript.keySet()) {
			String courseName = null; 
			for (Course course : courseList) {
				if (course.getCourse().equals(key)) {
					courseName = course.getCourseName(); 
				}
			}
			System.out.println("Grade of " + key + " " + courseName + ": " + transcript.get(key)); 
		}
	}
	
	public HashMap<String, String> getTranscript(){
		return this.transcript; 
	}
	
	public ArrayList<Course> getStudentCourseList(){
		return this.studentCourseList; 
	}
	
}
