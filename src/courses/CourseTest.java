/**
 * This is the unit test for controller 
 *
 * @author Michelle Chen
 * @author Evelyn Li
 * @author Gloria Chen
 */
package courses;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


/**
 * 
 */
class CourseTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	

	/**
	 * Test method for {@link courses.Course#checkIfCourseInProfListAndConflict(roles.Professor, courses.Course)}.
	 */
    @Test
    void testValidateCapacity() {
        Course course = new Course("CIS123");
        
        // test correct capacity
        boolean result = course.validateCapacity("30");
        assertTrue(result);
        
        // test incorrect capacity
        boolean result1 = course.validateCapacity("-5");
        assertFalse(result1);
        


    }
    
	/**
	 * Test method for {@link courses.Course#validateStartTime(java.lang.String)}.
	 */
	@Test
	void testValidateStartTime() {
        // Create a new course
        Course newCourse = new Course("PHYS101");

        // Test valid start time
        assertTrue(newCourse.validateStartTime("10:00"), "Valid start time should return true");

        // Test invalid start time format
        assertFalse(newCourse.validateStartTime("10-00"), "Invalid start time format should return false");

        // Test invalid start time values
        assertFalse(newCourse.validateStartTime("25:00"), "Invalid start time values should return false");
    }


	/**
	 * Test method for {@link courses.Course#validateEndTime(java.lang.String)}.
	 */
	@Test
	void testValidateEndTime() {
		 // initiate new course
        Course course = new Course("CS101");
        
        // Test valid start time
        assertTrue(course.validateEndTime("10:00"), "Valid start time should return true");

        // Test invalid start time format
        assertFalse(course.validateEndTime("10-00"), "Invalid start time format should return false");

        // Test invalid start time values
        assertFalse(course.validateEndTime("25:00"), "Invalid start time values should return false");
  
	}

	/**
	 * Test method for {@link courses.Course#validateLectureDate(java.lang.String)}.
	 */
	@Test
	void testValidateLectureDate() {
        // Create a new course
        Course newCourse = new Course("CHEM201");

        // Test valid lecture date
        assertTrue(newCourse.validateLectureDate("MTWRF"), "Valid lecture date should return true");

        // Test invalid lecture date (contains invalid day)
        assertFalse(newCourse.validateLectureDate("MTXRF"), "Invalid lecture date should return false");

        // Test invalid lecture date (exceeds 5 characters)
        assertFalse(newCourse.validateLectureDate("MTWRFX"), "Invalid lecture date should return false");

	}


	/**
	 * Test method for {@link courses.Course#deductCourseStudent()}.
	 */
	
    @Test
	void testDeductCourseStudent() {
		// initiate course 
        Course course = new Course("CS101");
        course.setCourseStudent(); // Add a student to the course initially
        
        course.deductCourseStudent(); // Deduct a student
        assertEquals(0, course.getCourseStudent(), "One student was deducted, so the count should be zero.");
        
        // currently there is no student.
        course.deductCourseStudent(); // Deduct a student (should not change anything)
        assertEquals(0, course.getCourseStudent(), "No students were added, so the count should remain zero.");
 

	}
	
	/**
	 * Test method for {@link courses.Course#deductCourseStudent()}.
	 */	
	@Test
    void testDeductCourseStudent_DeductMultipleStudents() {
        // initiate course 
        Course course = new Course("CS101");
        course.setCourseStudent(); // Add a student to the course initially
        course.setCourseStudent(); // Add another student to the course
        
        course.deductCourseStudent(); // Deduct one student
        course.deductCourseStudent(); // Deduct another student
        
        // return 0 
        assertEquals(0, course.getCourseStudent(), "Two students were deducted, so the count should be zero.");
    }

}