package roles;

import java.util.ArrayList;

import courses.Course;

public abstract class User {

	private String name; 
	
	private String username; 
	
	private String password; 
	
	private int ID; 
	
	Course conflictCourse; 
	
	
	public User(int ID) {
		this.ID = ID; 
	}
	
	public void setName(String name) {
		this.name = name; 
	}
	
	public void setUsername(String username) {
		this.username = username; 
	}
	
	public void setPassword(String password) {
		this.password = password; 
	}
	
	public String getName() {
		return this.name; 
	}
	
	public int getID() {
		return this.ID; 
	}
	
	public String getUsername() {
		return this.username; 
	}
	
	public String getPassword() {
		return this.password; 
	}
	
	public Course getConflictCourse() {
		return conflictCourse;
	}
	
	public int convertToMinute(String time) {
		String[] times = time.split(":"); 
		int hours = Integer.parseInt(times[0]); 
		int minutes = Integer.parseInt(times[1]); 
		return hours * 60 + minutes; 
	}
	
	//can use in student add class and professor add course 
	public boolean checkIfCourseConflict(Course course1, ArrayList<Course> courseList) {
		int targetStart = convertToMinute(course1.getCourseStart()) ; 
		int targetEnd = convertToMinute(course1.getCourseEnd()); 
		
		for (Course c : courseList) {
			int courseStart = convertToMinute(c.getCourseStart()); 
			int courseEnd = convertToMinute(c.getCourseEnd()); 
			
			//Check for time overlap
			if ((targetStart >= courseStart && targetStart < courseEnd) ||
					(targetEnd > courseStart && targetEnd <= courseEnd) ||
					(targetStart <= courseStart && targetEnd >= courseEnd)) {
				this.conflictCourse = c; 
				return true; 
			} 
		}
		return false; 
	}
	
	public ArrayList<Course> addCourse(String course, ArrayList<Course> courseList, ArrayList<Course> studentCourseList) {
		boolean courseFound = false; 
		for (Course c : courseList) {
			if (c.getCourse().equals(course)) {
				courseFound = true; 
				
				if (studentCourseList.contains(c)) {
					System.out.println("The course you selected is already in your list");
					System.out.println(); 
					return studentCourseList; 
				}
				
				if (checkIfCourseConflict(c, studentCourseList)) {
					System.out.println("The course you selected has time conflict with " + conflictCourse.getCourse() + " " + conflictCourse.getCourseName() + "."); 
					System.out.println(); 
					return studentCourseList; 
				}
				
				if (c.getCourseStudent() + 1 > c.getCourseCapacity()) {
					System.out.println("The course has reached the maximum, no more space to add.");
					System.out.println(); 
					return studentCourseList; 
				}
							
				studentCourseList.add(c); 
				c.setCourseStudent();
				c.getStudentInCourseList().add((Student) this); 
				System.out.println("Course added successfully");  
				System.out.println(); 
				return studentCourseList; 
			}
		}
		if (!courseFound) {
			System.out.println("The course is not existed in the course list."); 
			System.out.println(); 
		}
		
		return studentCourseList; 
	}
	
	public ArrayList<Course> dropCourse(String course, ArrayList<Course> courseList, ArrayList<Course> studentCourseList) {
		boolean courseFound = false; 
		for (Course c: courseList) {
			if (c.getCourse().equals(course)) {
				courseFound = true; 
				
				if (!studentCourseList.contains(c)) {
					System.out.println("The course isn't in your schedule."); 
					System.out.println(); 
					return studentCourseList; 
				}
				
				studentCourseList.remove(c); 
				c.deductCourseStudent();
				c.getStudentInCourseList().remove((Student) this); 
				System.out.println("Course dropped successfully "); 
				System.out.println(); 
				return studentCourseList; 
			}
		}
		if (!courseFound) {
			System.out.println("The course is not existed in the course list."); 
			System.out.println(); 
		}
		return studentCourseList; 
	}
	
	public void viewStudentInCourse(String course, ArrayList<Course> courseList, ArrayList<Course> lectureList) {
		for (Course c: courseList) {
			if (c.getCourse().equals(course)) {
				
				if (!lectureList.contains(c)) {
					System.out.println("This is not your course, you don't have permission to view.");
					System.out.println(); 
					return;
				}
				
				System.out.println("Students in your course " + c.getCourse() + " " + c.getCourseName() + ": "); 
				for (Student s: c.getStudentInCourseList()) {
					System.out.println(s.getID() + " " + s.getName()); 
				}
				System.out.println(); 
				return;
			}
		}
		
		System.out.println("The course is not existed in the course list."); 
		System.out.println(); 

	}
	
	public void printCourseList(ArrayList<Course> list) {
		for (Course c : list) {
			System.out.println(c.toPrint()); 
		}
		System.out.println();
	}
	
	public boolean checkIfCourseExisted(String course, ArrayList<Course> courseList){
		for (Course c: courseList) {
			if (c.getCourse().equals(course)) {
				System.out.println("The course already exist"); 
				return true; 
			}
		}
		
		return false;  
	}
	
	public boolean validateID(String ID) {
		if (ID.length() != 3) {
			System.out.println("Invalid ID input, it has to be between 001 to 999");
			return false; 
		}
		
		try {
			Integer.parseInt(ID); 
			
			return true; 
			
		} catch(NumberFormatException e) {
			System.out.println("Invalid ID input, it has to be number between 001 to 999"); 
		}
		return false; 
	}
	
	public Professor checkIfProfessorExisted(String ID, ArrayList<Professor> professorList) {
		int id = Integer.parseInt(ID);
		
		for (Professor p : professorList) {
			if(p.getID() == id) {
				return p; 
			}
		}
		return null;		 
	}
	
	public boolean checkIfProfUsernameExisted(String username, ArrayList<Professor> professorList) {
		for (Professor p: professorList) {
			if(p.getUsername().equals(username)) {
				System.out.println("The username you entered is not available."); 
				return true; 
			}
		}
		return false;
	}
	
	public Student checkIfStudentExisted(String ID, ArrayList<Student> studentList) {
		int id = Integer.parseInt(ID);
		for (Student s: studentList) {
			if (s.getID() == id) {
				return s; 
			}
		}
		return null; 
	}
	
	public boolean checkIfStudentUsernameExsited(String username, ArrayList<Student> studentList) {
		for (Student s: studentList) {
			if(s.getUsername().equals(username)) {
				System.out.println("The username you entered is not available."); 
				return true; 
			}
		}
		return false;
	}
}
