package college.courses.client;

import java.util.Set;
import java.util.HashSet;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import college.courses.dto.Course;
import college.courses.dto.Professor;
import college.courses.exceptions.ImproperInputException;

public class CatalogUtils {

//	Converts a Course to JSON including professors field
	static JsonObject course2json(Course course) {
		JsonObjectBuilder jobc = Json.createObjectBuilder();
		jobc.add("courseCode", course.getCourseCode());
		jobc.add("courseTitle", course.getCourseTitle());
		jobc.add("capacity", course.getCapacity());
		jobc.add("enrolled", course.getEnrolled());
		Set<Professor> professors = course.getProfessors();
		if (professors != null && professors.size() > 0) {
			JsonArrayBuilder jabp = Json.createArrayBuilder();
			for (Professor p : professors) {
				JsonObject jsonp = professor2json(p);
				jabp.add(jsonp);
			}
			jobc.add("professors", jabp);
		}
		JsonObject jsonc = jobc.build();
		return jsonc;
	}

//	Converts a Professor to JSON excluding courses field
	static JsonObject professor2json(Professor professor) {
		JsonObjectBuilder jobp = Json.createObjectBuilder();
		jobp.add("pid", professor.getPid());
		jobp.add("firstName", professor.getFirstName());
		jobp.add("middleName", professor.getMiddleName());
		jobp.add("lastName", professor.getLastName());
		JsonObject jsonp = jobp.build();
		return jsonp;
	}
	
//	Converts a Professor to JSON including courses field
	static JsonObject profCourses2json(Professor professor) {
		JsonObjectBuilder jobp = Json.createObjectBuilder();
		jobp.add("pid", professor.getPid());
		jobp.add("firstName", professor.getFirstName());
		jobp.add("middleName", professor.getMiddleName());
		jobp.add("lastName", professor.getLastName());
		Set<Course> courses = professor.getCourses();
		if (courses != null && courses.size() > 0) {
			JsonArrayBuilder jabc = Json.createArrayBuilder();
			for (Course course : courses) {
				JsonObjectBuilder jobc = Json.createObjectBuilder();
				jobc.add("courseCode", course.getCourseCode());
				jobc.add("courseTitle", course.getCourseTitle());
				jabc.add(jobc);
			}
			jobp.add("courses", jabc);
		}
		JsonObject jsonp = jobp.build();
		return jsonp;
	}
	
//	Converts JSON to a Course including professors field
	static Course json2course(JsonObject jsonc) throws ImproperInputException {
		Course course = null;
		String code = jsonc.getString("courseCode");
		String title = jsonc.getString("courseTitle");
		course = new Course(code, title);
		int capacity = jsonc.getInt("capacity");
		course.setCapacity(capacity);
		int enrolled = jsonc.getInt("enrolled");
		course.setEnrolled(enrolled);
		JsonArray jap = jsonc.getJsonArray("professors");
		if (jap != null) {
			Set<Professor> professors = new HashSet<Professor>();
			for (int i = 0; i < jap.size(); i++) {
				Professor professor = json2professor(jap.getJsonObject(i));
				professors.add(professor);
			}
			course.setProfessors( professors);
		}
		return course;
	}

//	Converts JSON to a Professor excluding courses field
	static Professor json2professor(JsonObject jsonp) throws ImproperInputException {
		int pid = jsonp.getInt("pid");
		String fName = jsonp.getString("firstName");
		String middleName = jsonp.getString("middleName");
		String lName = jsonp.getString("lastName");
		Professor professor = new Professor(fName, middleName, lName);
		professor.setPid(pid);
		return professor;
	}
	
//  Converts JSON to a Professor including courses field 
	static Professor json2profCourses(JsonObject jsonp) throws ImproperInputException {
		int pid = jsonp.getInt("pid");
		String fName = jsonp.getString("firstName");
		String middleName = jsonp.getString("middleName");
		String lName = jsonp.getString("lastName");
		Professor professor = new Professor(fName, middleName, lName);
		professor.setPid(pid);
		JsonArray jac = jsonp.getJsonArray("courses");
		if (jac != null) {
			Set<Course> courses = new HashSet<Course>();
			for (int i = 0; i < jac.size(); i++) {
				JsonObject jsonc = jac.getJsonObject(i);
				String code = jsonc.getString("courseCode");
				String title = jsonc.getString("courseTitle");
				courses.add( new Course(code, title) );
			}
			professor.setCourses(courses);
		}
		return professor;
	}
}
