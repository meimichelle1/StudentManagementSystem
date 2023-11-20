import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import courses.Course;
import files.FileInfoReader;
import roles.Student;
import roles.Admin;
import roles.Professor;

public class Controller {

	private static Student validStudent = null; 
	
	private static Professor validProfessor = null; 
	
	private static Admin validAdmin = null; 
	
	public static void main(String[] args) {
		String studentFile = "src/studentInfo.txt"; 
		String professorFile = "src/profInfo.txt";
		String adminFile = "src/adminInfo.txt"; 
		String courseFile = "src/courseInfo.txt"; 
		
		try {
			FileInfoReader.readStudentInfo(studentFile);
			FileInfoReader.readProfessorInfo(professorFile); 
			FileInfoReader.readAdminInfo(adminFile); 
			FileInfoReader.readCourseInfo(courseFile); 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		Scanner scanner = new Scanner(System.in); 
		
		boolean runProgram = true; 
		
		ArrayList<Student> studentList = FileInfoReader.getStudentList(); 
		ArrayList<Professor> professorList = FileInfoReader.getProfessorList(); 
		ArrayList<Admin> adminList = FileInfoReader.getAdminList();
		ArrayList<Course> courseList = FileInfoReader.getCourseList(); 
		
		while(runProgram) {
			System.out.println("--------------------------"); 
			System.out.println("Students Management System");
			System.out.println("--------------------------"); 
			System.out.println("1 -- Login as a student"); 
			System.out.println("2 -- Login as a professor"); 
			System.out.println("3 -- Login as a admin"); 
			System.out.println("4 -- Quit the system"); 
			System.out.println(); 
			int option = validIntegerInput("Please enter your option, eg. '1'.", 1, 4, scanner); 
			System.out.println();
			if(option == 1) {	
				if (validateStudentLogin(studentList, scanner) != "q") {
					studentInterface(validStudent, courseList, scanner); 
				} else {
					System.out.println(); 
				}
			} else if (option == 2) {
				if (validateProfessorLogin(professorList, scanner) != "q") {
					professorInterface(validProfessor, courseList, scanner); 
				} else {
					System.out.println(); 
				}
			} else if (option == 3) {
				if(validateAdminLogin(adminList, scanner) != "q") {
					adminInterface(validAdmin, courseList, professorList, studentList, scanner); 
				} else {
					System.out.println(); 
				}
			} else if (option == 4) {
				runProgram = false; 
			}
		}
	}
	
	public static int validIntegerInput(String prompt, int min, int max, Scanner scanner) {
		boolean valid = false; 
		int input = 0; 
		
		while (!valid) {
			System.out.println(prompt); 
			
			if (scanner.hasNextInt()) {
				input = scanner.nextInt(); 
				
				if (input >= min && input <= max) {
					valid = true; 
				} else {
					System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
					System.out.println(); 
				}
			} else {
				System.out.println("Invalid input. Please enter a number!"); 
				System.out.println(); 
				scanner.next(); 
			}
		}
		return input; 
	}
	
	public static String validateStudentLogin(ArrayList<Student> studentList, Scanner scanner) {
		boolean login = false; 
		String username = null; 
		String password = null; 
		
		while (!login) {
			System.out.println("Please enter your username, or type 'q' to quit."); 
				
			if(scanner.hasNext()) {
				username = scanner.next(); 
					
				if (username.equals("q")) {
					return "q"; 
				}
					
				validStudent = null; 
				for (Student s: studentList) {
					if(s.getUsername().equals(username)) {
						validStudent = s; 
						break; 
					}
				}
			}
			
			if(validStudent != null) {
				System.out.println("Please enter your password, or type 'q' to quit."); 
				
				if(scanner.hasNext()) {
					password = scanner.next(); 
					
					if (password.equals("q")) {
						return "q"; 
					}
					
					if(validStudent.getPassword().equals(password)) {
						login = true; 
					} else {
						System.out.println("Invalid password, try again."); 
					}
				}
			} else {
				System.out.println("Invalid username, try again.");; 
			}
		}
		return username; 
	}
	
	public static String validateProfessorLogin(ArrayList<Professor> professorList, Scanner scanner) {
		boolean login = false; 
		String username = null; 
		String password = null; 
		
		while (!login) {
			System.out.println("Please enter your username, or type 'q' to quit."); 
				
			if(scanner.hasNext()) {
				username = scanner.next(); 
					
				if (username.equals("q")) {
					return "q"; 
				}
				
				validProfessor = null; 
				for (Professor p: professorList) {
					if(p.getUsername().equals(username)) {
						validProfessor = p; 
						break; 
					}
				}
			}
			
			if(validProfessor != null) {
				System.out.println("Please enter your password, or type 'q' to quit."); 
				
				if(scanner.hasNext()) {
					password = scanner.next(); 
					
					if (password.equals("q")) {
						return "q"; 
					}
					
					if(validProfessor.getPassword().equals(password)) {
						login = true; 
					} else {
						System.out.println("Invalid password, try again."); 
					}
				}
			} else {
				System.out.println("Invalid username, try again.");; 
			}
		}
		return username; 
	}
	
	public static String validateAdminLogin(ArrayList<Admin> adminList, Scanner scanner) {
		boolean login = false; 
		String username = null; 
		String password = null; 
		
		while (!login) {
			System.out.println("Please enter your username, or type 'q' to quit."); 
				
			if(scanner.hasNext()) {
				username = scanner.next(); 
					
				if (username.equalsIgnoreCase("q")) {
					return "q"; 
				}
					
				validAdmin = null; 
				for (Admin a: adminList) {
					if(a.getUsername().equals(username)) {
						validAdmin = a; 
						break; 
					}
				}
			}
			
			if(validAdmin != null) {
				System.out.println("Please enter your password, or type 'q' to quit."); 
				
				if(scanner.hasNext()) {
					password = scanner.next(); 
					
					if (password.equalsIgnoreCase("q")) {
						return "q"; 
					}
					
					if(validAdmin.getPassword().equals(password)) {
						login = true; 
					} else {
						System.out.println("Invalid password, try again."); 
					}
				}
			} else {
				System.out.println("Invalid username, try again.");; 
			}
		}
		return username; 
	}
	
	public static void studentInterface(Student student, ArrayList<Course> courseList, Scanner scanner) {
		boolean run = true; 
		ArrayList<Course> studentCourseList = student.getStudentCourseList(); 
		
		while (run) {
			System.out.println("--------------------------"); 
			System.out.println("  Welcome, " + student.getName());
			System.out.println("--------------------------"); 
			System.out.println("1 -- View all courses"); 
			System.out.println("2 -- Add courses to your list"); 
			System.out.println("3 -- View enrolled courses"); 
			System.out.println("4 -- Drop courses in your list"); 
			System.out.println("5 -- View grades"); 
			System.out.println("6 -- Return to previous menu"); 
			System.out.println(); 
			int option = validIntegerInput("Please enter your option, eg. '1'.", 1, 6, scanner); 
			System.out.println(); 
			if (option == 1) {
				student.printCourseList(studentCourseList);
			} else if (option == 2) {		
				System.out.println("The courses in your list:");
				student.printCourseList(studentCourseList);
				
				String courseToAdd = null; 
				
				while (true) {
					System.out.println("Please select the course ID you want to add to your list, eg. 'CIT590'."); 
					System.out.println("Or enter 'q' to return to the previous menu."); 
					
					if (scanner.hasNext()) {
						courseToAdd = scanner.next(); 
						
						if (courseToAdd.equals("q")) {
							break; 
						}
						
						studentCourseList = student.addCourse(courseToAdd, courseList, studentCourseList); 
					}
				}
			} else if (option == 3) {
				System.out.println("The courses in your list:");
				student.printCourseList(studentCourseList);
			} else if (option == 4){
				String courseToDrop = null; 
				
				while (true) {
					System.out.println("The courses in your list:");
					student.printCourseList(studentCourseList); 
					System.out.println("Please enter the ID of the course which you want to drop, eg. 'CIT591'."); 
					System.out.println("Or enter 'q' to return to the previous menu"); 
					
					if (scanner.hasNext()) {
						courseToDrop = scanner.next(); 
						
						if (courseToDrop.equals("q")) {
							break; 
						}
						
						studentCourseList = student.dropCourse(courseToDrop, courseList, studentCourseList); 
					}
				}
			} else if (option == 5) {
				System.out.println("Here are the courses you already taken, with your grade in a letter format"); 
				student.printGrades(courseList); 
				System.out.println(); 
			} else if (option == 6) {
				run = false; 
			}	
		}
	}
	
	public static void professorInterface(Professor professor, ArrayList<Course> courseList, Scanner scanner) {
		boolean run = true; 
		ArrayList<Course> lectureList = professor.getLectureList(); 
		
		while (run) {
			System.out.println("--------------------------"); 
			System.out.println("  Welcome, " + professor.getName());
			System.out.println("--------------------------"); 
			System.out.println("1 -- View given courses"); 
			System.out.println("2 -- View student list of the given course"); 
			System.out.println("3 -- Return to the previous menu"); 
			System.out.println(); 
			int option = validIntegerInput("Please enter your option, eg. '1'.", 1, 3, scanner); 
			System.out.println(); 
			if (option == 1) {
				System.out.println("------------The course list------------"); 
				professor.printCourseList(lectureList);
			} else if (option == 2) {
				String courseToView = null; 
				
				while(true) {
					System.out.println("Please enter the course ID, eg. 'CIS519'."); 
					System.out.println("Or type 'q' to quit."); 
					
					if (scanner.hasNext()) {
						courseToView = scanner.next(); 
						
						if (courseToView.equals("q")) {
							break; 
						}
						
						professor.viewStudentInCourse(courseToView, courseList, lectureList);
					}
				}
			} else if (option == 3) {
				run = false; 
			}
		}
	}
	
	public static void adminInterface(Admin admin, ArrayList<Course> courseList, ArrayList<Professor> professorList, ArrayList<Student> studentList, Scanner scanner) {
		boolean run = true; 
		boolean toMain = false; 
		String course = null; 
		String courseName = null; 
		String startTime = null; 
		String endTime = null;
		String lectureDate = null; 
		String capacity = null; 
		Course newAdd = null; 
		while (run) {
			System.out.println("--------------------------"); 
			System.out.println("  Welcome, " + admin.getName());
			System.out.println("--------------------------"); 
			System.out.println("1 -- View all courses"); 
			System.out.println("2 -- Add new courses"); 
			System.out.println("3 -- Delete courses"); 
			System.out.println("4 -- Add new professor"); 
			System.out.println("5 -- Delete professor"); 
			System.out.println("6 -- Add new Student"); 
			System.out.println("7 -- Delete student"); 
			System.out.println("8 -- return to previous menu"); 
			int option = validIntegerInput("Please enter your option, eg. '1'.", 1, 8, scanner); 
			System.out.println(); 
			
			if (option == 1) {
				admin.printCourseList(courseList);
			} else if (option == 2) {
				toMain = false; 
				while (!toMain) {
					System.out.println("Please enter the course ID, or type 'q' to end.");
					
					if(scanner.hasNext()) {
						course = scanner.next().trim(); 
						
						if (course.equalsIgnoreCase("q")) {
							toMain = true; 
							break; 
						}
						
						if(!admin.checkIfCourseExisted(course, courseList)) {
							newAdd = new Course(course); 
							courseList.add(newAdd); 
							break; 
						}
					}
				}
				
				if(!toMain) {
					while(true) {
						System.out.println("Please enter the course name, or type 'q' to end."); 
						if(scanner.hasNextLine()) {
							scanner.nextLine(); 
						}
						
						if (scanner.hasNextLine()) {
							courseName = scanner.nextLine().trim(); 
							
							if (courseName.equalsIgnoreCase("q")) {
								toMain = true; 
								break; 
							}
							
							newAdd.setCourseName(courseName);
							break; 
						}
					}
				}
				
				if(!toMain) {
					while(true) {
						System.out.println("Please enter the course start time, or type 'q' to end. eg. '19:00'");
									
						if(scanner.hasNext()) {
							startTime = scanner.next().trim(); 
										
							if (startTime.equalsIgnoreCase("q")) {
								toMain = true; 
								break; 
							}
										
							if(newAdd.validateStartTime(startTime)) {
								break; 
							}
						}
						
					}
				}
				
				if(!toMain) {
					while(true) {
						System.out.println("Please enter the course end time, or type 'q' to end. eg. '20:00'");
							
						if(scanner.hasNext()) {
							endTime = scanner.next(); 
								
							if (endTime.equalsIgnoreCase("q")) {
								toMain = true; 
								break; 
							}
							
							if(newAdd.validateEndTime(endTime)) {
								break; 
							}
						}
					}
				}
				
				if(!toMain) {
					while(true) {
						System.out.println("Please enter the course date, or type 'q' to end. eg. 'MW'");
							
						if(scanner.hasNext()) {
							lectureDate = scanner.next(); 
								
							if (lectureDate.equalsIgnoreCase("q")) {
								toMain = true;
								break; 
							}
							
							if (newAdd.validateLectureDate(lectureDate)) {
								break;
							}
						}
					}
				}
				
				if(!toMain) {
					while(true) {
						System.out.println("Please enter the course capacity, or type 'q' to end. eg. 'MW'");
							
						if (scanner.hasNext()) {
							capacity = scanner.next(); 
								
							if (capacity.equalsIgnoreCase("q")) {
								toMain = true; 
								break; 
							}
							
							if(newAdd.validateCapacity(capacity)) {
								break; 
							}
						}
					}
				}
				
				if(!toMain) {
					while(true) {
						String input; 
						String profID; 
						String profName; 
						String profUsername; 
						Professor newProf = null;
						
						System.out.println("Please enter the course lecturer's id, or type 'q' to end. eg. '001'");
							
						if (scanner.hasNext()) {
							input = scanner.next(); 
								
							if (input.equalsIgnoreCase("q")) {
								toMain = true; 
								break;
							}
								
							if(admin.validateID(input)) {
								Professor prof = null; 
								prof = admin.checkIfProfessorExisted(input, professorList); 
								
								if(prof != null) {
									if(newAdd.checkIfCourseInProfListAndConflict(prof, newAdd)) {
										System.out.print("Successfully added the course: ");
										System.out.println(newAdd.toPrint()); 
										System.out.println();
										toMain = true; 
										break; 
									}
								} else {
									System.out.println("The professor isn't in the system, please add this professor first"); 
									while(true) {
										System.out.println("Please enter the professor's ID, or type 'q' to end");
										
										if (scanner.hasNext()) {
											profID = scanner.next(); 
											
											if (profID.equalsIgnoreCase("q")) {
												toMain = true; 
												break; 
											}
											
											if(admin.validateID(profID)) {
												newProf = admin.checkIfProfessorExisted(profID, professorList); 
												if(newProf == null) {
													newProf = new Professor(Integer.parseInt(profID)); 
													professorList.add(newProf); 
													break; 
												}
												
											}
										}
									}
									
									if(!toMain) {
										while(true) {
											System.out.println("Please enter professor's name, or type 'q' to end");
											if(scanner.hasNextLine()) {
												scanner.nextLine(); 
											}
											if (scanner.hasNextLine()) {
												profName = scanner.nextLine().trim(); 
													
												if(profName.equalsIgnoreCase("q")) {
													toMain = true; 
													break; 
												}
												
												newProf.setName(profName);
												newAdd.setLecturer(profName);
												break; 
											}
										}
										
										
										while (true) {
											System.out.println("Please enter a username");
											if(scanner.hasNext()) {
												profUsername = scanner.next();
												
												if(admin.checkIfProfUsernameExisted(profUsername, professorList)) {
													continue; 
												} else {
													newProf.setUsername(profUsername);
													break; 
												}
											}
										}
										
										while (true) {
											System.out.println("Please enter a password");
											
											if(scanner.hasNext()) {
												newProf.setPassword(scanner.next());
												break; 
											}
										}
										
										if(!toMain) {
											System.out.println("Successfully added the new professor: " + newProf.getID() + " " + newProf.getName()); 

											System.out.print("Successfully added the course: ");
											System.out.println(newAdd.toPrint()); 
											System.out.println();
											toMain = true; 
											break;
										}
									}
								}
							}
						}
					}
				}
				
			} else if (option == 3) {
				toMain = false; 
				String deCourse;
				while(!toMain) {
					System.out.println("Please enter course to delete, or type 'q' to end."); 
					
					if(scanner.hasNext()) {
						deCourse = scanner.next(); 
						
						if(deCourse.equalsIgnoreCase("q")) {
							toMain = true; 
							break; 
						}
						
						if(admin.checkIfCourseExisted(deCourse, courseList)) {
							System.out.println("Delete the course now..."); 
							for(Course c: courseList) {
								if(c.getCourse().equals(deCourse)) {
									courseList.remove(c); 
									System.out.println("The course has successfully removed from the system."); 
									break; 
								}
							}
							toMain = true; 
							break; 
						} else {
							System.out.println("The course doesn't exist, please try again."); 
						}
					}
				}
				
			} else if (option == 4) {
				toMain = false; 
				String input;
				String name; 
				String username; 
				Professor anotherNew = null; 
				
				while(!toMain) { 
					System.out.println("Please eneter the professor's ID, or type 'q' to quit"); 
					if(scanner.hasNext()) {
						input = scanner.next(); 
						
						if (input.equalsIgnoreCase("q")) {
							toMain = true; 
							break; 
						}
						
						if(admin.validateID(input)) {
							anotherNew = admin.checkIfProfessorExisted(input, professorList); 
							if (anotherNew != null) {
								System.out.println("The ID already exists"); 
								continue; 
							} else {
								anotherNew = new Professor(Integer.parseInt(input)); 
								break; 
							}
						}
					}
				}
				while(!toMain) {
					System.out.println("Please enter professor's name, or type 'q' to end"); 
					
					if(scanner.hasNextLine()) {
						scanner.nextLine(); 
					}
					
					if(scanner.hasNextLine()) {
						name = scanner.nextLine(); 
						
						if (name.equalsIgnoreCase("q")) {
							toMain = true; 
							break; 
						}
						
						anotherNew.setName(name);
						break; 
					}
				}
				
				while (true) {
					System.out.println("Please enter a username");
					if(scanner.hasNext()) {
						username = scanner.next(); 
						
						
						if(admin.checkIfProfUsernameExisted(username, professorList)) {
							continue; 
						} else {
							anotherNew.setUsername(username);
							break; 
						}
					}
				}
				
				while (true) {
					System.out.println("Please enter a password");
					
					if(scanner.hasNext()) {
						anotherNew.setPassword(scanner.next());
						toMain = true; 
						break; 
					}
				}
				
				
			} else if (option == 5) {
				toMain = false; 
				String deProf;
				
				while(!toMain) {
					System.out.println("Please enter professor's ID to delete, or type 'q' to quit. eg. 001"); 
					
					if(scanner.hasNext()) {
						deProf = scanner.next(); 
						
						if (deProf.equalsIgnoreCase("q")) {
							toMain = true; 
							break; 
						}
						
						if(admin.validateID(deProf)) {
							Professor deleteProf = admin.checkIfProfessorExisted(deProf, professorList); 
							if(deleteProf != null) {
								
								System.out.println(deleteProf.getName() + " Professor has successfully deleted."); 
								professorList.remove(deleteProf); 
								toMain = true; 
								break; 
							} else {
								continue; 
							}
						} else {
							continue; 
						}
					}
				}
			} else if (option == 6) {
				toMain = false; 
				String input;
				String name; 
				String username; 
				Student newStudent = null; 
				
				while(!toMain) { 
					System.out.println("Please eneter the student's ID, or type 'q' to quit"); 
					if(scanner.hasNext()) {
						input = scanner.next(); 
						
						if (input.equalsIgnoreCase("q")) {
							toMain = true; 
							break; 
						}
						
						if(admin.validateID(input)) {
							newStudent = admin.checkIfStudentExisted(input, studentList); 
							if (newStudent != null) {
								System.out.println("The ID already exists"); 
								continue; 
							} else {
								newStudent = new Student(Integer.parseInt(input)); 
								break; 
							}
						} else {
							continue; 
						}
					}
				}
				while(!toMain) {
					System.out.println("Please enter student's name, or type 'q' to end"); 
					
					if(scanner.hasNextLine()) {
						scanner.nextLine(); 
					}
					
					if(scanner.hasNextLine()) {
						name = scanner.nextLine(); 
						
						if (name.equalsIgnoreCase("q")) {
							toMain = true; 
							break; 
						}
						
						newStudent.setName(name);
						break; 
					}
				}
				
				while (true) {
					System.out.println("Please enter a username");
					if(scanner.hasNext()) {
						username = scanner.next(); 
						
						
						if(admin.checkIfStudentUsernameExsited(username, studentList)) {
							continue; 
						} else {
							newStudent.setUsername(username);
							break; 
						}
					}
				}
				
				while (true) {
					System.out.println("Please enter a password");
					
					if(scanner.hasNext()) {
						newStudent.setPassword(scanner.next());
						toMain = true; 
						break; 
					}
				}
				
			} else if (option == 7) {
				toMain = false; 
				String deStud;
				
				while(!toMain) {
					System.out.println("Please enter student's ID to delete, or type 'q' to quit. eg. 001"); 
					
					if(scanner.hasNext()) {
						deStud = scanner.next(); 
						
						if (deStud.equalsIgnoreCase("q")) {
							toMain = true; 
							break; 
						}
						
						if(admin.validateID(deStud)) {
							Student deleteStudent = admin.checkIfStudentExisted(deStud, studentList); 
							if(deleteStudent != null) {
								
								System.out.println(deleteStudent.getName() + " Student has successfully deleted."); 
								studentList.remove(deleteStudent); 
								toMain = true; 
								break; 
							} else {
								continue; 
							}
						} else {
							continue; 
						}
					}
				}
			} else if (option == 8) {
				run = false; 
			}
		}
	}

}
