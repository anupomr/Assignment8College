package college.courses.service;

import java.util.Collection;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import college.courses.data.CourseManager;
import college.courses.data.ProfessorManager;
import college.courses.dto.Course;
import college.courses.dto.Professor;
import college.courses.exceptions.CourseNotFoundException;
import college.courses.exceptions.DuplicateCourseException;
import college.courses.exceptions.ImproperInputException;


@Path("catalog")
public class CatalogService {

	@GET
	@Path("allCourses")
	@Produces("application/json")
	public JsonObject getAllCourses() {
		CourseManager cm = new CourseManager();
		Collection<Course> courses = cm.getAllCourses();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for (Course course : courses) {
			JsonObject jo = CatalogUtils.course2json(course);
			jab.add(jo);
		}
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Catalog", jab);
		return job.build();
	}

	@GET
	@Path("allProfessors")
	@Produces("application/json")
	public JsonObject getAllProfessors() {
		ProfessorManager pm = new ProfessorManager();
		Collection<Professor> professors = pm.getAllProfessors();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for (Professor professor : professors) {
			JsonObject jo = CatalogUtils.profCourses2json(professor);
			jab.add(jo);
		}
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Professors", jab);
		return job.build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("courses/{code}")
	public JsonObject getCourse(@PathParam("code") final String courseCode) throws CourseNotFoundException {
		CourseManager cm = new CourseManager();
		Course retrivedCourse = cm.getCourse( courseCode );
		return CatalogUtils.course2json(retrivedCourse);
	}
	
	@PUT
	@Path("courses/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public JsonObject addCourse(JsonObject jsonObj) throws DuplicateCourseException, ImproperInputException {
        Course addCourse = CatalogUtils.json2course(jsonObj);
        CourseManager cm = new CourseManager();
        Course addedCourse = cm.addCourse(addCourse);        
		return CatalogUtils.course2json(addedCourse);        
	}
	@POST
	@Path("courses/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public JsonObject updateCourse(JsonObject jsono) throws DuplicateCourseException, ImproperInputException, CourseNotFoundException {
        Course course = CatalogUtils.json2course(jsono);
        CourseManager cm = new CourseManager();
        course = cm.updateCourse(course);
        String code = course.getCourseCode();
        try {
		Course newCourse = cm.getCourse( code );
		return CatalogUtils.course2json(newCourse);
        } catch (CourseNotFoundException e) {
        	e.printStackTrace();
        	throw new CourseNotFoundException( "Updated course not found: "  + code );
        }
	}
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("courses/{code}")
	public JsonObject deleteCourse(@PathParam("code") final String courseCode) throws CourseNotFoundException {
		CourseManager cc = new CourseManager();
		Course c = cc.deleteCourse(courseCode);
		return CatalogUtils.course2json(c);
	}
	
}
