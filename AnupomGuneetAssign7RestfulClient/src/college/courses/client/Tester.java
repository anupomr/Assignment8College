package college.courses.client;

import java.util.Scanner;

import college.courses.dto.Course;
import college.courses.dto.Professor;

public class Tester {

	public static void main(String[] args) {
		CatalogClient cc = new CatalogClient();
		Scanner sin = new Scanner(System.in);
		System.out.println("Test client for Course Services");
// TEST 1
		System.out.println("TEST 1: Getting an existing course");
		try {
			System.out.println("getting course HIST101");
			System.out.println(cc.getCourse("HIST101"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Press ENTER to continue");
		sin.nextLine();

// TEST 2
		System.out.println("TEST 2: Creating a new course with no professor");
		try {
			System.out.println("Adding TEST1234");
			Course course = new Course("TEST1234", "Software Testing");
			System.out.println(cc.addCourse(course));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Press ENTER to continue");
		sin.nextLine();

// TEST 3
		System.out.println("TEST 3: Updating an existing course");
		try {
			Course course = new Course("HIST260", "Struggles for Freedom and Democracy");
			course.setCapacity(12);
			System.out.println(cc.updateCourse(course));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Press ENTER to continue");
		sin.nextLine();
//		
// TEST 4
		System.out.println("TEST 4: Creating a new course with a new professor");
		try {
			Course course = new Course("BARK1221", "Dog Training");
			course.addProfessor(new Professor("Rover", "Ferdinand", "Jones"));
			System.out.println(cc.addCourse(course));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Press ENTER to continue");
		sin.nextLine();

// TEST 5
		System.out.println("TEST 5: Deleting a course with no professors");
		try {
			System.out.println("Deleting HIST260");
			System.out.println(cc.deleteCourse("HIST260"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Press ENTER to continue");
		sin.nextLine();

// TEST 6
		System.out.println("TEST 6: Deleting a course with professors");
		try {
			System.out.println("Deleting HIST333");
			System.out.println(cc.deleteCourse("HIST333"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Press ENTER to continue");
		sin.nextLine();

// TEST 7
		System.out.println("TEST 7: Adding professors to an existing course");
		try {
			System.out.println("Updating TEST1234");
			Course course = new Course("TEST1234", "Software Testing");
			course.addProfessor(new Professor("Rover", "Ferdinand", "Jones"));
			course.addProfessor(new Professor("Preeta", "Hedge"));
			System.out.println(course);
			System.out.println(cc.updateCourse(course));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Press ENTER to continue");
		sin.nextLine();


// TEST 8
		System.out.println("TEST 8: Removing professors, without removing a course");
		try {
			System.out.println("updating TEST1234");
			Course course = new Course("TEST1234", "Quality Assurance");
			course.addProfessor(new Professor("T.","J.", "Park"));
			System.out.println(course);
			System.out.println(cc.updateCourse(course));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
// All done
		sin.close();
		System.out.println();
		System.out.println("tests complete");
	}
}
