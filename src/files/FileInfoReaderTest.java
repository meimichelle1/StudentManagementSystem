package files;

// Import statements.
import static org.junit.jupiter.api.Assertions.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Course;
import roles.Admin;
import roles.Professor;
import roles.Student;

/**
 * Test class for reading various information files.
 */
class FileInfoReaderTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/**
     * Tests the reading of student information from a file.
     */
	@Test
	void testReadStudentInfo() {
		// Defining file path
	    String fileName = "studentInfo.txt"; 
	    
	    // Initialize empty list
	    ArrayList<Student> studentList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            // Read until reaches end of the file
            while ((line = bufferedReader.readLine()) != null) {
            	
            	// Split line into array
            	String[] studentInfo = line.trim().split("; ");
            
            	// Extract information from array
                int id = Integer.parseInt(studentInfo[0].trim());
                String name = studentInfo[1].trim();
                String username = studentInfo[2].trim();
                String password = studentInfo[3].trim();
                
                // Create Student object with the extracted information
                Student student = new Student(id);
                student.setName(name);
                student.setUsername(username);
                student.setPassword(password);

                // Add each student to list
                studentList.add(student);
            }

            // Checking if list is empty
            assertFalse(studentList.isEmpty(), "Student list is empty");

            // Checking the first student from the list
            Student testStudent = studentList.get(0);
            assertEquals(001, testStudent.getID(), "Student ID does not match");
            assertEquals("StudentName1", testStudent.getName(), "Student name does not match");
            assertEquals("testStudent01", testStudent.getUsername(), "Student username does not match");
            assertEquals("password590", testStudent.getPassword(), "Student password does not match");
            
            // Checking the second student from the list
            Student testStudent2 = studentList.get(1);
            assertEquals(002, testStudent2.getID(), "Student ID does not match");
            assertEquals("StudentName2", testStudent2.getName(), "Student name does not match");
            assertEquals("testStudent02", testStudent2.getUsername(), "Student username does not match");
            assertEquals("password590", testStudent2.getPassword(), "Student password does not match");
            

        } catch (IOException e) {
        	// Fail the test when IOException occurs
            fail("IOException occurred: " + fileName + ": " + e.getMessage());
        }

    }
	
	

	/**
     * Tests the reading of professor information from a file.
     */
	@Test
	void testReadProfessorInfo() {
		// Defining file path
		String fileName = "profInfo.txt";
		
		// Initialize empty list
        ArrayList<Professor> professorList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            // Read until end of the file
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // Split the line into an array
                String[] professorInfo = line.trim().split(";");

                // Extract information from array 
                String name = professorInfo[0].trim();
                int id = Integer.parseInt(professorInfo[1].trim());
                String username = professorInfo[2].trim();
                String password = professorInfo[3].trim();
                
                // Create object with the extracted information
                Professor professor = new Professor(id);
                professor.setName(name);
                professor.setUsername(username);
                professor.setPassword(password);

                // Add professor to list
                professorList.add(professor);
            }

            assertFalse(professorList.isEmpty(), "Professor list is empty");

            // Checking the first professor from the list
            Professor testProfessor = professorList.get(0);
            assertEquals("Clayton Greenberg", testProfessor.getName(), "Professor name does not match");
            assertEquals(001, testProfessor.getID(), "Professor ID does not match");
            assertEquals("Greenberg", testProfessor.getUsername(), "Professor username does not match");
            assertEquals("password590", testProfessor.getPassword(), "Professor password does not match");
            
            // Checking another professor from the list
            Professor testProfessor2 = professorList.get(4);
            assertEquals("Val Breazu Tannen", testProfessor2.getName(), "Professor name does not match");
            assertEquals(005, testProfessor2.getID(), "Professor ID does not match");
            assertEquals("Tannen", testProfessor2.getUsername(), "Professor username does not match");
            assertEquals("password590", testProfessor2.getPassword(), "Professor password does not match");
            
            // Checking another professor from the list
            Professor testProfessor3 = professorList.get(6);
            assertEquals("Thomas Joseph Farmer", testProfessor3.getName(), "Professor name does not match");
            assertEquals(007, testProfessor3.getID(), "Professor ID does not match");
            assertEquals("Farmer", testProfessor3.getUsername(), "Professor username does not match");
            assertEquals("password590", testProfessor3.getPassword(), "Professor password does not match");

        } catch (IOException e) {
        	// Fail the test when IOException occurs
            fail("IOException occurred: " + fileName + ": " + e.getMessage());
        }
    }

	
	/**
     * Tests the reading of admin information from a file.
     */
	@Test
	void testReadAdminInfo() {
		// Defining file path
		String fileName = "adminInfo.txt";
		// Initialize empty list
        ArrayList<Admin> adminList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            // Read until end of the file
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Split the line into an array
                String[] adminInfo = line.trim().split(";");

                // Extract information from array 
                int id = Integer.parseInt(adminInfo[0].trim());
                String name = adminInfo[1].trim();
                String username = adminInfo[2].trim();
                String password = adminInfo[3].trim();
                // Create object with the extracted information
                Admin admin = new Admin(id);
                admin.setName(name);
                admin.setUsername(username);
                admin.setPassword(password);

                // Add each admin to list
                adminList.add(admin);
            }

            assertFalse(adminList.isEmpty(), "Admin list is empty");

            // Checking the first admin from the list
            Admin testAdmin = adminList.get(0);
            assertEquals("admin", testAdmin.getName(), "Admin name does not match");
            assertEquals(001, testAdmin.getID(), "AdminID does not match");
            assertEquals("admin01", testAdmin.getUsername(), "Admin username does not match");
            assertEquals("password590", testAdmin.getPassword(), "Admin password does not match");
            
            
            // Checking the second admin from the list
            Admin testAdmin2 = adminList.get(1);
            assertEquals("admin", testAdmin2.getName(), "Admin name does not match");
            assertEquals(002, testAdmin2.getID(), "Admin ID does not match");
            assertEquals("admin02", testAdmin2.getUsername(), "Admin username does not match");
            assertEquals("password590", testAdmin2.getPassword(), "Admin password does not match");
            
            // Checking the third admin from the list
            Admin testAdmin3 = adminList.get(2);
            assertEquals("admin", testAdmin3.getName(), "Admin name does not match");
            assertEquals(003, testAdmin3.getID(), "Admin ID does not match");
            assertEquals("admin03", testAdmin3.getUsername(), "Admin username does not match");
            assertEquals("password590", testAdmin3.getPassword(), "Admin password does not match");
            
           
        } catch (IOException e) {// Fail the test when IOException occurs
            fail("IOException occurred: " + fileName + ": " + e.getMessage());
        }
	}
	
	/**
     * Tests the reading of course information from a file.
     */
	@Test
	void testReadCourseInfo() {
		// Defining file path
		 String fileName = "courseInfo.txt";
		 	// Initialize empty list
	        ArrayList<Course> courseList = new ArrayList<>();
	        ArrayList<Professor> professorList = new ArrayList<>(); 

	        try (FileReader fileReader = new FileReader(fileName);
	             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

	            String line;
	            // Read until end of the file
	            while ((line = bufferedReader.readLine()) != null) {
	                if (line.trim().isEmpty()) {
	                    continue;
	                }

	                // Split the line into an array
	                String[] courseInfo = line.trim().split(";");
	                
	                // Extract information from array 
	                Course course = new Course(courseInfo[0].trim());
	                course.setCourseName(courseInfo[1].trim());
	                course.setLecturer(courseInfo[2].trim());
	                course.setCourseStart(courseInfo[4].trim());
	                
	                String capacityString = courseInfo[6].trim();
	                int capacityTest = Integer.parseInt(capacityString);
	                course.setCourseCapacity(capacityTest);
	                

	                // Add course
	                courseList.add(course);

	                // Match course with lecturer
	                String lecturer = courseInfo[2].trim();
	                for (Professor p : professorList) {
	                    if (p.getName().equals(lecturer)) {
	                        p.getLectureList().add(course);
	                        break; // Assuming a professor can only teach one course in this scenario
	                    }
	                }
	            }

	            assertFalse(courseList.isEmpty(), "Course list is empty");

	            
	            
	            // Checking the first course from the list
	            Course testCourse = courseList.get(0);
	            assertEquals("Programming Languages and Techniques", testCourse.getCourseName(), "Course name does not match");
	            assertEquals("Brandon L Krakowsky", testCourse.getLecturer(), "Lecturer name does not match");
	            
	            // Checking for course start time
	            Course testCourse2 = courseList.get(1);
	            String expectedStart = "12:00";
	            String actualStart = testCourse2.getCourseStart();
	            assertEquals(expectedStart, actualStart, "Start time does not match");
	            
	            // Checking for course capacity
	            Course testCourse3 = courseList.get(2);
	            int expectedCapacity = 72;
	            int actualCapacity = testCourse3.getCourseCapacity();
	            assertEquals(expectedCapacity, actualCapacity, "Course capacity does not match");
	            

	        } catch (IOException e) {
	        	// Fail the test when IOException occurs
	            fail("IOException occurred: " + fileName + ": " + e.getMessage());
	        }
	}
	}