package college.courses.client;

import java.util.LinkedList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import college.courses.dto.Course;
import college.courses.dto.Professor;
import college.courses.exceptions.ImproperInputException;

public class CatalogClient {
	static Client wsClient;
	// adjust the following line for your context root
	private static String WEBSERVICE_ROOT = "http://localhost:8088/AnupomGuneetServicesWeb";
	private static String CATALOG_URI = WEBSERVICE_ROOT + "/catalog";

	static {
		ClientBuilder cb = ClientBuilder.newBuilder();
		cb.register(JsonMessageBodyReader.class);
		cb.register(JsonMessageBodyWriter.class);
		wsClient = cb.build();
	}
	
	public CatalogClient() {
		super();
	}

	public List<Course> getAllCourses() throws ImproperInputException {
		WebTarget wt = wsClient.target(CATALOG_URI + "/allCourses/" );
		Invocation webServiceCall = wt.request().accept(MediaType.APPLICATION_JSON).build("GET");
		JsonObject jsono = webServiceCall.invoke(JsonObject.class);
		List<Course> courses = new LinkedList<Course> ();
		JsonArray ja = jsono.getJsonArray ( "Catalog" );
		for (int i = 0; i < ja.size(); i++) {
			JsonObject jo =  ja.getJsonObject(i);
			Course course = CatalogUtils.json2course(jo);
			courses.add(course);
		}
		return courses;
	}

	public List<Professor> getAllProfessors() throws ImproperInputException {
		WebTarget wt = wsClient.target(CATALOG_URI + "/allProfessors/" );
		Invocation webServiceCall = wt.request().accept(MediaType.APPLICATION_JSON).build("GET");
		JsonObject jsono = webServiceCall.invoke(JsonObject.class);
		List<Professor> professors = new LinkedList<Professor> ();
		JsonArray ja = jsono.getJsonArray ( "Professors" );
		for (int i = 0; i < ja.size(); i++) {
			JsonObject jo =  ja.getJsonObject(i);
			Professor professor = CatalogUtils.json2profCourses(jo);
			professors.add(professor);
		}
		return professors;
	}
}