package courses;

import java.util.ArrayList;

import roles.Professor;
import roles.Student;

public class Course {

	private String course; 
	
	private String courseName; 
	
	private String lecturer; 
	
	private String courseStart; 
	
	private String courseEnd; 
	
	private String lectureDate; 
	
	private int startHR;
	
	private int startMin; 
	
	private int courseCapacity; 
	
	private int courseStudent; 
	
	private ArrayList<Student> studentInCourse = new ArrayList<Student>(); 
	
	
	public Course(String course) {
		this.course = course; 
		
		courseStudent = 0; 
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName; 
	}
	
	public void setLecturer(String lecturer) {
		this.lecturer = lecturer; 
	}
	
	public boolean checkIfCourseInProfListAndConflict(Professor prof, Course course) {

		ArrayList<Course> lectureList = prof.getLectureList(); 
		
		if(lectureList.contains(course)) {
			System.out.print("The course is already in Professor " + prof.getName() + "'s list.");
			return false; 
		}
				
		course.setLecturer(prof.getName());
				
		boolean conflict = prof.checkIfCourseConflict(course, lectureList); 
				
		if (conflict) {		
			System.out.println("The new course has time conflict with course: " + prof.getConflictCourse().toPrint()); 
			System.out.println("Unable to add new course: " + course.toPrint()); 
			return false; 
		}
		lectureList.add(course); 
		return true;
	}
	
	public void setCourseStart(String courseStart) {
		this.courseStart = courseStart; 
	}
	
	public void setCourseEnd(String courseEnd) {

		this.courseEnd = courseEnd; 
	}
	
	public boolean validateStartTime(String courseStart) {
		if (courseStart.contains(":")) {
			String[] starts = courseStart.split(":");
			if (starts.length == 2 && !starts[0].isEmpty() && !starts[1].isEmpty()) {
				try {
					int hr = Integer.parseInt(starts[0]); 
					int min = Integer.parseInt(starts[1]); 
					
					if (hr < 0 || hr > 23 || min < 0 || min > 59) {
						System.out.println("Please eneter a valid start time within 00:00 to 23:59."); 
						return false; 
					} else {
						setCourseStart(courseStart);  
						this.startHR = hr; 
						this.startMin = min; 
						return true; 
					}
				} catch (NumberFormatException e) {
					System.out.println("Please enter a valid start time with numbers in the format HH:MM."); 
					return false; 
				} 
			} else {
				System.out.println("Please eneter a valid start time in the format HH:MM."); 
				return false; 
			}
			
		} else {
			System.out.println("Please enter a valid start time with \":\" in format HH:MM."); 
		}	
		return false; 
	}
	
	public boolean validateEndTime(String courseEnd) {
		if (courseEnd.contains(":")) {
			String[] ends = courseEnd.split(":");
			if (ends.length == 2 && !ends[0].isEmpty() && !ends[1].isEmpty()) {
				try {
					int hr = Integer.parseInt(ends[0]); 
					int min = Integer.parseInt(ends[1]); 
					
					if (hr < 0 || hr > 23 || min < 0 || min > 59) {
						System.out.println("Please eneter a valid end time within 00:00 to 23:59."); 
						return false; 
					} else {
						if (hr < startHR || (hr == startHR && min <= startMin)) {
							System.out.println("End time cannot be earlier than the start time."); 
							return false; 
						} else {
							setCourseEnd(courseEnd); 
							return true; 
						}
					}
				} catch (NumberFormatException e) {
					System.out.println("Please enter a valid end time with numbers in the format HH:MM."); 
					return false; 
				} 
			} else {
				System.out.println("Please eneter a valid end time in the format HH:MM."); 
				return false; 
			}
			
		} else {
			System.out.println("Please enter a valid end time with \":\" in format HH:MM."); 
		}
		return false; 
	}
	
	public void setLectureDate(String lectureDate) {
		this.lectureDate = lectureDate; 
	}
	
	public boolean validateLectureDate(String dates) {
		String orderedDates = "MTWRF"; 
		if (dates.length() <= 5) {
			String upperD = dates.toUpperCase(); 
			ArrayList<Character> sortDates = new ArrayList<Character>(); 
			
			for (int i = 0; i < upperD.length(); i++) {
				if (orderedDates.indexOf(upperD.charAt(i)) == -1) {
					System.out.println("At least one of the entered date is invalid");
					return false; 
				} else {
					sortDates.add(upperD.charAt(i)); 
				}
			}
			// Sort based on the index in orderedDates
			sortDates.sort((l1, l2) -> orderedDates.indexOf(l1) - orderedDates.indexOf(l2));
			
			String input = "";
			for (char c: sortDates) {
				input += c; 
			}
			setLectureDate(input);
			return true; 
		} else {
			System.out.println("Lecture date cannot exceed 5 characters."); 
		}
		return false; 
	}
	
	public void setCourseCapacity(int capacity) {
		this.courseCapacity = capacity; 
	}
	
	public boolean validateCapacity(String number) {
		try {
			int cap = Integer.parseInt(number); 
			
			if (cap < 0) {
				System.out.println("Capacity has to be above 0."); 
			} else {
				setCourseCapacity(cap); 
				return true; 
			}
		} catch (NumberFormatException e) {
			System.out.println("Please enter a valid number."); 
		}
		return false; 
	}
	
	public void setCourseStudent() {
		this.courseStudent++; 
	}
	
	public void deductCourseStudent() {
		this.courseStudent--; 
	}
	
	public String getCourse() {
		return this.course; 
	}
	
	public String getCourseName() {
		return this.courseName; 
	}
	
	public String getCourseStart() {
		return this.courseStart; 
	}
	
	public String getCourseEnd()
	{	
		return this.courseEnd; 
	}
	
	public String getLecturer() {
		return this.lecturer; 
	}
	
	public int getCourseStudent() {
		return this.courseStudent; 
	}
	
	public int getCourseCapacity() {
		return this.courseCapacity; 
	}
	
	public ArrayList<Student> getStudentInCourseList(){
		return this.studentInCourse; 
	}
	
	public String toPrint() {
		return course + "|" + courseName + ", " + courseStart+ "-" + courseEnd + " on " + lectureDate + ", with course capacity: " + courseCapacity + ", students: " + courseStudent + ", lecturer: " + lecturer; 
	}
	
	public void printAllCourses(ArrayList<String> courses){
		for (String course : courses) {
			System.out.println(course); 
		}
	}

}
