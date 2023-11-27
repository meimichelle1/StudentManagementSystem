package courses;

import java.util.ArrayList;

import roles.Professor;
import roles.Student;

/**
 * Represents a Course with specific attributes.
 * 
 * @author Michelle Chen
 * @author Evelyn Li
 * @author Gloria Chen
 */
public class Course {

// Attributes defining a Course

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
	
	private ArrayList<Student> studentInCourse = new ArrayList<Student>(); // List of students enrolled in the course
	
     /**
     * Constructor to initialize a Course object with a course identifier.
     * @param course Unique course identifier
     */
	public Course(String course) {
		this.course = course; 
		
		courseStudent = 0; 
	}
    /**
     * Sets the name of the course.
     * @param courseName Name of the course to be set
     */
	public void setCourseName(String courseName) {
		this.courseName = courseName; 
	}
	/**
     * Sets the lecturer for the course.
     * @param lecturer Name of the lecturer to be set
     */
	public void setLecturer(String lecturer) {
		this.lecturer = lecturer; 
	}

	/**
     * Checks if the course is in the professor's list and avoids schedule conflicts.
     * @param prof   Professor to check against
     * @param course Course to be checked
     * @return True if the course can be added, false otherwise
     */
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

	/**
     * Sets the start time for the course.
     * @param courseStart Start time of the course to be set
     */
	public void setCourseStart(String courseStart) {
		this.courseStart = courseStart; 
	}
	/**
     * Sets the end time of the course.
     * @param courseEnd The end time to be set for the course
     */
	public void setCourseEnd(String courseEnd) {

		this.courseEnd = courseEnd; 
	}

	/**
     * Validates the format and range of the start time for the course.
     * @param courseStart Start time to be validated in HH:MM format
     * @return True if the start time is valid, false otherwise
     */
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
	
    /**
     * Validates the provided end time for the course.
     * @param courseEnd The end time to be validated
     * @return boolean indicating whether the end time is valid 
     */
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
	/**
     * Sets the lecture date for the course.
     * @param lectureDate The date of the lecture to be set
     */
	public void setLectureDate(String lectureDate) {
		this.lectureDate = lectureDate; 
	}

	/**
     * Validates the provided lecture dates.
     * @param dates The dates to be validated
     * @return boolean indicating whether the dates are valid or not
     */
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
					sortDates.add(upperD.charAt(i)); // Add valid dates to the list
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
	
    /**
     * Sets the capacity of the course.
     * @param capacity The capacity of the course to be set
     */
	public void setCourseCapacity(int capacity) {
		this.courseCapacity = capacity; 
	}
	
    /**
     * Validates provided capacity number.
     * @param number The number to be validated as capacity
     * @return boolean indicating whether the capacity is valid or not
     */
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

	/**
     * Increases the count of students in the course by 1.
     */
	public void setCourseStudent() {
		this.courseStudent++; 
	}
	
    /**
     * Decreases the count of students in the course by 1 if students are present.
     * Ensures the count doesn't go below zero.
     */
    public void deductCourseStudent() {
    if (this.courseStudent > 0) {
        this.courseStudent--; // Deduct only if students are present
    } else {
        this.courseStudent = 0; 
    }
}

    /**
     * Returns the course code.
     * @return The course code
     */
	public String getCourse() {
		return this.course; 
	}
	
    /**
     * Returns the course name.
     * @return The course name
     */
	public String getCourseName() {
		return this.courseName; 
	}
	
    /**
     * Returns the start time of the course.
     * @return The start time of the course
     */
	public String getCourseStart() {
		return this.courseStart; 
	}

	/**
     * Returns the end time of the course.
     * @return The end time of the course
     */
	public String getCourseEnd()
	{	
		return this.courseEnd; 
	}
	
    /**
     * Returns the lecturer of the course.
     * @return The lecturer of the course
     */
	public String getLecturer() {
		return this.lecturer; 
	}
	
    /**
     * Returns the count of students in the course.
     * @return The count of students in the course
     */
	public int getCourseStudent() {
		return this.courseStudent; 
	}
	
    /**
     * Returns the capacity of the course.
     * @return The capacity of the course
     */
	public int getCourseCapacity() {
		return this.courseCapacity; 
	}
	
    /**
     * Returns the list of students enrolled in the course.
     * @return The list of students enrolled in the course
     */
	public ArrayList<Student> getStudentInCourseList(){
		return this.studentInCourse; 
	}
	
    /**
     * Creates a formatted string representation of the course details.
     * @return A formatted string representation of the course
     */
	public String toPrint() {
		return course + "|" + courseName + ", " + courseStart+ "-" + courseEnd + " on " + lectureDate + ", with course capacity: " + courseCapacity + ", students: " + courseStudent + ", lecturer: " + lecturer; 
	}
	
    /**
     * Prints all courses from the provided list of courses.
     * @param courses The list of courses to be printed
     */
	public void printAllCourses(ArrayList<String> courses){
		for (String course : courses) {
			System.out.println(course); 
		}
	}

}