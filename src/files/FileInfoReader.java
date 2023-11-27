package files;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import courses.Course;
import roles.Admin;
import roles.Professor;
import roles.Student;

/**
 * Handles reading of different types of information (students, professors, admins, courses) from files.
 * 
 * @author Michelle Chen
 * @author Evelyn Li
 * @author Gloria Chen
 */
public class FileInfoReader {
	
	private static ArrayList<Course> courseList = new ArrayList<Course>(); // Static list to store courses
	
	private static ArrayList<Professor> professorList = new ArrayList<Professor>(); // Static list to store professors
	
	private static ArrayList<Student> studentList = new ArrayList<Student>(); // Static list to store students
	
	private static ArrayList<Admin> adminList = new ArrayList<Admin>(); // Static list to store admins
	
	/**
     * Reads student information from a file and populates the studentList.
     *
     * @param fileName The name of the file to read from.
     * @throws IOException If an I/O error occurs.
     */
	public static void readStudentInfo(String fileName) throws IOException{
		// Create file object 
		File file = new File(fileName); 
					
		// Identify file reader
		FileReader fileReader = new FileReader(fileName); 
						
		// Identify buffered reader 
		BufferedReader bufferedReader = new BufferedReader(fileReader);
						
		String line; // Variable to hold each line from the file
		
		while ((line = bufferedReader.readLine()) != null) { // Reading the file line by line
			if (line.trim().isEmpty()) { // Checking if the line is empty or contains only whitespace
				continue; // Skipping empty lines
			}
			
			String[] studentInfo = line.trim().split("; "); // Splitting the line into parts
			
			Student student = new Student(Integer.parseInt(studentInfo[0].trim())); // Creating a new student with the parsed ID
			
			studentList.add(student); // Adding the student to the student list

			// Setting properties of the student
			student.setName(studentInfo[1].trim());
			student.setUsername(studentInfo[2].trim());
			student.setPassword(studentInfo[3].trim());
			
			// Parsing and setting the student's transcript
			HashMap<String, String> transcript = student.getTranscript(); // Getting the student's transcript map

			String[] transc = studentInfo[4].trim().split(", "); // Splitting the transcript info into individual courses
			
			for (int i = 0; i < transc.length; i++) { // Iterating over each course-grade pair
				String[] courseGrade = transc[i].split(": "); // Splitting the course-grade pair into course and grade

				transcript.put(courseGrade[0].trim(), courseGrade[1].trim()); // Adding the course and grade to the transcript
			}
		}

		// Closing resources
        bufferedReader.close(); // Closing the BufferedReader
        fileReader.close(); // Closing the FileReader
	}
	
	/**
 	* Reads professor information from a specified file and populates the professorList.
 	*
	* @param fileName The name of the file to read from.
	* @throws IOException If an I/O error occurs during file reading.
	*/
	public static void readProfessorInfo(String fileName) throws IOException{
		// Create file object 
		File file = new File(fileName); 
							
		// Identify file reader
		FileReader fileReader = new FileReader(fileName); 
								
		// Identify buffered reader 
		BufferedReader bufferedReader = new BufferedReader(fileReader);
								
		String line; // Variable to hold each line read from the file
		
		while((line = bufferedReader.readLine()) != null) { // Iterating over each line in the file until end of file
			if (line.trim().isEmpty()) { // Checking if the line is empty or contains only whitespace
				continue; // Skipping empty lines
			}
			
			String[] professorInfo = line.trim().split("; "); // Splitting the line into parts based on "; "
			
			Professor professor = new Professor(Integer.parseInt(professorInfo[1].trim())); // Creating a new professor object with ID parsed from the second part
			
			professorList.add(professor); // Adding the created professor to the professor list

			professor.setName(professorInfo[0].trim()); // Setting the professor's name from the first part

			professor.setUsername(professorInfo[2].trim()); // Setting the professor's username from the third part

			professor.setPassword(professorInfo[3].trim()); // Setting the professor's password from the fourth part
		}

		// Closing resources
        bufferedReader.close(); // Closing the BufferedReader
        fileReader.close(); // Closing the FileReader
	}
	
	/**
	* Reads admin information from a specified file and populates the adminList.
	*
	* @param fileName The name of the file to read from.
	* @throws IOException If an I/O error occurs during file reading.
	*/
	public static void readAdminInfo(String fileName) throws IOException{
		// Create file object 
		File file = new File(fileName); 
							
		// Identify file reader
		FileReader fileReader = new FileReader(fileName); 
								
		// Identify buffered reader 
		BufferedReader bufferedReader = new BufferedReader(fileReader);
								
		String line; // Variable to hold each line read from the file
		
		while((line = bufferedReader.readLine()) != null) { // Iterating over each line in the file until end of file
			if (line.trim().isEmpty()) { // Checking if the line is empty or contains only whitespace
				continue; // Skipping empty lines
			}
			
			String[] adminInfo = line.trim().split("; "); // Splitting the line into parts based on "; "

			Admin admin = new Admin(Integer.parseInt(adminInfo[0].trim())); // Creating a new admin object with ID parsed from the first part

			adminList.add(admin); // Adding the created admin to the admin list

			admin.setName(adminInfo[1].trim()); // Setting the admin's name from the second part

			admin.setUsername(adminInfo[2].trim()); // Setting the admin's username from the third part

			admin.setPassword(adminInfo[3].trim()); // Setting the admin's password from the fourth part
		}

		// Closing resources
        bufferedReader.close(); // Closing the BufferedReader
        fileReader.close(); // Closing the FileReader
	}
	
	/**
	* Reads course information from a specified file and populates the courseList.
	*
	* @param fileName The name of the file to read from.
	* @throws IOException If an I/O error occurs during file reading.
	*/
	public static void readCourseInfo(String fileName) throws IOException {
		// Create file object 
		File file = new File(fileName); 
				
		// Identify file reader
		FileReader fileReader = new FileReader(fileName); 
				
		// Identify buffered reader 
		BufferedReader bufferedReader = new BufferedReader(fileReader);
				
		String line; // Variable to hold each line read from the file
		
		while ((line = bufferedReader.readLine()) != null) { // Iterating over each line in the file until end of file
			if (line.trim().isEmpty()) { // Checking if the line is empty or contains only whitespace
				continue; // Skipping empty lines
			}
						
			String[] courseInfo = line.trim().split("; "); // Splitting the line into parts based on "; "
			
			Course course = new Course(courseInfo[0].trim()); // Creating a new course object with the course identifier from the first part
			
			courseList.add(course); // Adding the created course to the course list
			
			course.setCourseName(courseInfo[1].trim()); // Setting the course name from the second part
			
			String lecturer = courseInfo[2].trim(); // Extracting the lecturer's name from the third part

			course.setLecturer(lecturer); // Setting the lecturer for the course

			for (Professor p : professorList) { // Iterating through the list of professors
				if (p.getName().equals(lecturer)) { // Checking if the professor's name matches the lecturer
					p.getLectureList().add(course); // Adding the course to the professor's lecture list
				}
			}
			
			course.setLectureDate(courseInfo[3].trim()); // Setting the lecture date from the fourth part
			
			course.setCourseStart(courseInfo[4].trim()); // Setting the course start time from the fifth part
			
			course.setCourseEnd(courseInfo[5].trim()); // Setting the course end time from the sixth part
			 
			course.setCourseCapacity(Integer.parseInt(courseInfo[6].trim())); // Setting the course capacity from the seventh part
		}

		// Closing resources
        bufferedReader.close(); // Closing the BufferedReader
        fileReader.close(); // Closing the FileReader
	}
	
	/**
	* Provides access to the list of courses read from the file.
	* This method returns a static list containing all the courses that have been read.
	*
	* @return An ArrayList containing Course objects.
	*/
	public static ArrayList<Course> getCourseList(){
		return courseList; // Returning the static list containing Course objects
	}
	
	/**
	* Provides access to the list of professors read from the file.
	* This method returns a static list containing all the professors that have been read.
	*
	* @return An ArrayList containing Professor objects.
	*/
	public static ArrayList<Professor> getProfessorList(){
		return professorList; // Returning the static list containing Professor objects
	}
	
	/**
	* Provides access to the list of students read from the file.
	* This method returns a static list containing all the students that have been read and processed.
	*
	* @return An ArrayList containing Student objects.
	*/
	public static ArrayList<Student> getStudentList(){
		return studentList; // Returning the static list containing Student objects
	}
	
	/**
	* Provides access to the list of admins read from the file.
	* This method returns a static list containing all the admins that have been read and processed.
	*
	* @return An ArrayList containing Admin objects.
	*/
	public static ArrayList<Admin> getAdminList(){
		return adminList; // Returning the static list containing Admin objects
	}
}