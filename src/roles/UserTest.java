/**
 * This is used to test the User class
 */
package roles;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import courses.Course;

/**
 * This is the unit test for user 
 *
 * @author Michelle Chen
 * @author Evelyn Li
 * @author Gloria Chen
 */
class UserTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
    // MockUser class for testing purposes
    private static class MockUser extends User {
        MockUser(int ID) {
            super(ID);
        }
    }
    
 // MockUser class for testing purposes
    private static class MockUser1 extends Student {
        MockUser1(int ID) {
            super(ID);
        }
    }
    

    
    
    // Use a mock implementation of User for testing
    User user = new MockUser(1);
    
    
    User user1 = new MockUser1(1);

	@Test
    void testConvertToMinute() {
        
        assertEquals(60, user.convertToMinute("01:00")); // test 1 hour 
        assertEquals(123, user.convertToMinute("02:03")); // test hours and minutes 
        assertEquals(1440, user.convertToMinute("24:00")); // test 1 day 
    }
	
    @Test
    void testCheckIfCourseConflict() {
    	
    	// create the first course 
        Course course1 = new Course("CIS101");
        course1.setCourseStart("09:00");
        course1.setCourseEnd("10:00");

        // create the second course 
        Course course2 = new Course("CIS102");
        course2.setCourseStart("09:30");
        course2.setCourseEnd("10:30");

        // add courses to Course ArrayList
        ArrayList<Course> courseList = new ArrayList<>();
        courseList.add(course1);

        // Check course conflict, return true because it is conflicted.
        assertTrue(user.checkIfCourseConflict(course2, courseList));

        // Change the course 2 time and check if course is still conflicted 
        course2.setCourseStart("10:30");
        course2.setCourseEnd("11:30");
        assertFalse(user.checkIfCourseConflict(course2, courseList));
    }

    @Test
    void testAddCourse() {
        ArrayList<Course> courseList = new ArrayList<>();
        ArrayList<Course> studentCourseList = new ArrayList<>();

        // create first class 
        Course course1 = new Course("CIS101");
        course1.setCourseStart("09:00");
        course1.setCourseEnd("10:00");
        course1.setCourseCapacity(1);
        courseList.add(course1);

        studentCourseList = user1.addCourse("CIS101", courseList, studentCourseList);
        assertEquals(1, studentCourseList.size());

        // Trying to add the same course again
        studentCourseList = user1.addCourse("CIS101", courseList, studentCourseList);
        assertEquals(1, studentCourseList.size());

        // Trying to add a course with time conflict
        Course course2 = new Course("CIS102");
        course2.setCourseStart("09:30");
        course2.setCourseEnd("10:30");
        courseList.add(course2);

        // check to see if the course is within student course list 
        studentCourseList = user1.addCourse("CIS102", courseList, studentCourseList);
        assertEquals(1, studentCourseList.size());
    }

    @Test
    void testDropCourse() {
        ArrayList<Course> courseList = new ArrayList<>();
        ArrayList<Course> studentCourseList = new ArrayList<>();

        // create first course 
        Course course1 = new Course("CIS101");
        courseList.add(course1);
        studentCourseList.add(course1);

        // create second course 
        Course course2 = new Course("CIS102");
        courseList.add(course2);
        studentCourseList.add(course2);

        // drop a class and check the current schedule size 
        studentCourseList = user1.dropCourse("CIS101", courseList, studentCourseList);
        assertEquals(1, studentCourseList.size());

        // Trying to drop a course not in the schedule
        studentCourseList = user1.dropCourse("CIS103", courseList, studentCourseList);
        assertEquals(1, studentCourseList.size());
    }


    @Test
    void testCheckIfCourseExisted() {
        ArrayList<Course> courseList = new ArrayList<>();

        // create first course 
        Course course1 = new Course("CIS101");
        courseList.add(course1);

        // create second course 
        Course course2 = new Course("CIS102");
        courseList.add(course2);

        // check if this class is in user1
        boolean Checkflag = user1.checkIfCourseExisted("CIS101", courseList);
        assertTrue(Checkflag);
        
        
        // check if this class is not in user1
        boolean Checkflag1 = user1.checkIfCourseExisted("NonExistent", courseList);
        assertFalse(Checkflag1);
    }
    
    
    @Test
    void testValidateID() {
    	// test valid ID
        User user = new MockUser(1);
        assertTrue(user.validateID("123"));
        
        // test number string
        assertFalse(user.validateID("abc"));
        
        // test extra digit 
        assertFalse(user.validateID("1000"));
    }
    
    @Test
    void testCheckIfProfessorExisted() {
        User user = new MockUser(1);
        // create professor array list 
        ArrayList<Professor> professorList = new ArrayList<>();
        Professor existingProfessor = new Professor(123);
        
        // add professor 
        professorList.add(existingProfessor);

        // test to see if professor exists 
        Professor result = user.checkIfProfessorExisted("123", professorList);
        assertEquals(existingProfessor, result);
        
        
        // test to see if professor exists 
        Professor result1 = user.checkIfProfessorExisted("456", professorList);
        assertNull(result1);
    }

    @Test
    void testCheckIfProfUsernameExisted() {
    	
    	// test when user exists 
        User user = new MockUser(1);
        // initiate professor arraylist 
        ArrayList<Professor> professorList = new ArrayList<>();
        Professor existingProfessor = new Professor(123);
        
        // set professor name 
        existingProfessor.setUsername("existingProf");
        professorList.add(existingProfessor);

        // check to see if the username already exists 
        boolean result = user.checkIfProfUsernameExisted("existingProf", professorList);
        assertTrue(result);
        
        

        // check to see if the username already exists 
        boolean result1 = user.checkIfProfUsernameExisted("nonexistProf", professorList);
        assertFalse(result1);
        
   
    }
    
    @Test
    void testCheckIfStudentExisted() {
    	
        User user = new MockUser(1);

        // initiate student array list
        ArrayList<Student> studentList = new ArrayList<>();
        Student existingStudent = new Student(123);
        studentList.add(existingStudent);

        // check to see if the student exists
        Student result = user.checkIfStudentExisted("123", studentList);

        // assert that the student exists
        assertNotNull("this is null"+result);
        

    }
    
    @Test
    void testCheckIfStudentUsernameExsited() {
    	
        User user = new MockUser(1);

        // initiate student array list
        ArrayList<Student> studentList = new ArrayList<>();
        Student existingStudent = new Student(123);
        studentList.add(existingStudent);
        existingStudent.setUsername("Johnny");

        
        // check to see if the student exists
        boolean result = user.checkIfStudentUsernameExsited("Johnny", studentList);

        // assert that the student exists
        assertTrue(result);
        
        
        // check to see if the student does not exist 
        boolean result1 = user.checkIfStudentUsernameExsited("Random", studentList);

        assertFalse(result1);
        

    }
    

}