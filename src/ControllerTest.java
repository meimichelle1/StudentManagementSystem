import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import roles.Student;
import roles.Admin;
import roles.Professor;

/**
 * This is the unit test for controller 
 *
 * @author Michelle Chen
 * @author Evelyn Li
 * @author Gloria Chen
 */
class ControllerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * Test method for {@link Controller#validIntegerInput(java.lang.String, int, int, java.util.Scanner)}.
	 */
	@Test
	void testValidIntegerInput() {
		// valid input within range 
		// Input: 5 (assuming the valid range is 1 to 10)
		// Expected Output: 5
		Scanner scanner = new Scanner("5");
		int result = Controller.validIntegerInput("Enter a number between 1 and 10:", 1, 10, scanner);
		assertEquals(5, result);
		
		
		//invalid input (non-integer)
		// Expected output: 7
		Scanner scanner1 = new Scanner("abc\n7");
		int result1 = Controller.validIntegerInput("Enter a number between 1 and 10:", 1, 10, scanner1);
		assertEquals(7, result1); // Retry with a valid input after an invalid one
		
		// invalid output (out of range)
		// error message for trying again 
		Scanner scanner2 = new Scanner("15\n3");
		int result2 = Controller.validIntegerInput("Enter a number between 1 and 10:", 1, 10, scanner2);
		assertEquals(3, result2); // Retry with a valid input after an invalid one
	}
	
	
	
	/**
	 * Test method for {@link Controller#validateStudentLogin(java.util.ArrayList, java.util.Scanner)}.
	 */
	@Test
	void testValidateStudentLogin() {
		ArrayList<Student> studentList = new ArrayList<>();
    	// Extract information from array
        int id = 1;
        String name = "user123";
        String username = "user123";
        String password = "pass123";
        
        // Create Student object 
        Student student = new Student(id);
        student.setName(name);
        student.setUsername(username);
        student.setPassword(password);

        // Add each student to list
        studentList.add(student);
        
        // test with the correct user login
		Scanner scanner = new Scanner("user123\npass123");
		String result = Controller.validateLogin(studentList, scanner);
		assertEquals("user123", result);
		
		// test with incorrect user login
		Scanner scanner2 = new Scanner("invalidUser\nuser123\npass123");
		String result2 = Controller.validateLogin(studentList, scanner2);
		assertEquals("user123", result2); // Retry with a valid username after an invalid one		
	}
	


	/**
	 * Test method for {@link Controller#validateProfessorLogin(java.util.ArrayList, java.util.Scanner)}.
	 */
	@Test
	void testValidateProfessorLogin() {
        // Arrange
        ArrayList<Professor> professorList = new ArrayList<>();
        String name = "validProfessor";
        int id = 1;
        String username = "validProfessor";
        String password = "password";
        
        // Create object
        Professor professor = new Professor(id);
        professor.setName(name);
        professor.setUsername(username);
        professor.setPassword(password);

        // Add professor to list
        professorList.add(professor);
        
        // test valid professor login 
        String input = "validProfessor\npassword\n";
        String result = Controller.validateLogin(professorList, new Scanner(input));
        assertEquals("validProfessor", result);
        
        // test invalid professor login 
        String input1 = "invalidProfessor\nwrongPassword\nq\n";
        String result1 = Controller.validateLogin(professorList, new Scanner(input1));
        assertEquals("q", result1);
	}

	/**
	 * Test method for {@link Controller#validateAdminLogin(java.util.ArrayList, java.util.Scanner)}.
	 */
	@Test
	void testValidateAdminLogin() {
        // initiate admin values 
        ArrayList<Admin> adminList = new ArrayList<>();
        String name = "validAdmin";
        int id = 1;
        String username = "validAdmin";
        String password = "password";
        
        // Create object 
        Admin admin = new Admin(id);
        admin.setName(name);
        admin.setUsername(username);
        admin.setPassword(password);

        // Add admin to list
        adminList.add(admin);
        
        // test valid admin login 
        String input = "validAdmin\npassword\n";
        String result = Controller.validateLogin(adminList, new Scanner(input));
        assertEquals("validAdmin", result);
        
        // test invalid admin login 
        String input1 = "invalidAdmin\nwrongPassword\nq\n";
        String result1 = Controller.validateLogin(adminList, new Scanner(input1));
        assertEquals("q", result1);
	}

}
