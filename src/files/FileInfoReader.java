package files;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import courses.Course;
import roles.Admin;
import roles.Professor;
import roles.Student;


public class FileInfoReader {
	
	private static ArrayList<Course> courseList = new ArrayList<Course>(); 
	
	private static ArrayList<Professor> professorList = new ArrayList<Professor>(); 
	
	private static ArrayList<Student> studentList = new ArrayList<Student>(); 
	
	private static ArrayList<Admin> adminList = new ArrayList<Admin>(); 
	
	public static void readStudentInfo(String fileName) throws IOException{
		// Create file object 
		File file = new File(fileName); 
					
		// Identify file reader
		FileReader fileReader = new FileReader(fileName); 
						
		// Identify buffered reader 
		BufferedReader bufferedReader = new BufferedReader(fileReader);
						
		String line; 
		
		while ((line = bufferedReader.readLine()) != null) {
			// Skip empty lines or lines with only whitespace
			if (line.trim().isEmpty()) {
				continue; 
			}
			
			String[] studentInfo = line.trim().split("; ");
			Student student = new Student(Integer.parseInt(studentInfo[0].trim())); 
			studentList.add(student); 
			student.setName(studentInfo[1].trim());
			student.setUsername(studentInfo[2].trim());
			student.setPassword(studentInfo[3].trim());
			
			HashMap<String, String> transcript = student.getTranscript(); 
			String[] transc = studentInfo[4].trim().split(", "); 
			for (int i = 0; i < transc.length; i++) {
				String[] courseGrade = transc[i].split(": "); 

				transcript.put(courseGrade[0].trim(), courseGrade[1].trim()); 
			}
		}
	}
	
	public static void readProfessorInfo(String fileName) throws IOException{
		// Create file object 
		File file = new File(fileName); 
							
		// Identify file reader
		FileReader fileReader = new FileReader(fileName); 
								
		// Identify buffered reader 
		BufferedReader bufferedReader = new BufferedReader(fileReader);
								
		String line; 
		
		while((line = bufferedReader.readLine()) != null) {
			// Skip empty lines or lines with only whitespace
			if (line.trim().isEmpty()) {
				continue; 
			}
			
			String[] professorInfo = line.trim().split("; ");
			Professor professor = new Professor(Integer.parseInt(professorInfo[1].trim())); 
			professorList.add(professor); 
			professor.setName(professorInfo[0].trim());
			professor.setUsername(professorInfo[2].trim());
			professor.setPassword(professorInfo[3].trim());
		}
	}
	
	public static void readAdminInfo(String fileName) throws IOException{
		// Create file object 
		File file = new File(fileName); 
							
		// Identify file reader
		FileReader fileReader = new FileReader(fileName); 
								
		// Identify buffered reader 
		BufferedReader bufferedReader = new BufferedReader(fileReader);
								
		String line; 
		
		while((line = bufferedReader.readLine()) != null) {
			// Skip empty lines or lines with only whitespace
			if (line.trim().isEmpty()) {
				continue; 
			}
			
			String[] adminInfo = line.trim().split("; ");
			Admin admin = new Admin(Integer.parseInt(adminInfo[0].trim())); 
			adminList.add(admin); 
			admin.setName(adminInfo[1].trim());
			admin.setUsername(adminInfo[2].trim());
			admin.setPassword(adminInfo[3].trim());
		}
	}
	
	public static void readCourseInfo(String fileName) throws IOException {
		// Create file object 
		File file = new File(fileName); 
				
		// Identify file reader
		FileReader fileReader = new FileReader(fileName); 
				
		// Identify buffered reader 
		BufferedReader bufferedReader = new BufferedReader(fileReader);
				
		String line; 
		
		while ((line = bufferedReader.readLine()) != null) {
			// Skip empty lines or lines with only whitespace
			if (line.trim().isEmpty()) {
				continue; 
			}
						
			String[] courseInfo = line.trim().split("; "); 
			
			Course course = new Course(courseInfo[0].trim()); 
			
			courseList.add(course); 
			
			course.setCourseName(courseInfo[1].trim());
			
			String lecturer = courseInfo[2].trim(); 
			course.setLecturer(lecturer);
			for (Professor p : professorList) {
				if (p.getName().equals(lecturer)) {
					p.getLectureList().add(course); 
				}
			}
			
			course.setLectureDate(courseInfo[3].trim());
			
			course.setCourseStart(courseInfo[4].trim());
			
			course.setCourseEnd(courseInfo[5].trim());
			
			course.setCourseCapacity(Integer.parseInt(courseInfo[6].trim()));
		}
	}
		
	public static ArrayList<Course> getCourseList(){
		return courseList; 
	}
	
	public static ArrayList<Professor> getProfessorList(){
		return professorList; 
	}
	
	public static ArrayList<Student> getStudentList(){
		return studentList; 
	}
	
	public static ArrayList<Admin> getAdminList(){
		return adminList; 
	}
}
