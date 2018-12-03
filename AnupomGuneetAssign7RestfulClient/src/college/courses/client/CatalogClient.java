package college.courses.client;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import college.courses.dto.Course;
import college.courses.exceptions.ImproperInputException;

public class CatalogClient {
	Client wsClient;
	// adjust the following line for your context root
	private static String WEBSERVICE_ROOT = "http://localhost:8088/AnupomGuneetServicesWeb";
	private static String CATALOG_URI = WEBSERVICE_ROOT + "/catalog";

	public CatalogClient() {
		ClientBuilder cb = ClientBuilder.newBuilder();
		cb.register(JsonMessageBodyReader.class);
		cb.register(JsonMessageBodyWriter.class);
		wsClient = cb.build();
	}

	public Course getCourse(String code) {
		WebTarget wt = this.wsClient.target(CATALOG_URI + "/courses/" + code );
		Invocation webServiceCall = wt.request().accept(MediaType.APPLICATION_JSON).build("GET");
		JsonObject jsono = webServiceCall.invoke(JsonObject.class);
		Course course = null;
		try {
			course = CatalogUtils.json2course( jsono );
		} catch (ImproperInputException e) {
			System.out.println(e.getClass() + ": " + e.getMessage());
		} 
		return course;
	}

	public Course addCourse(Course course) throws ImproperInputException {
		JsonObject jsono = CatalogUtils.course2json(course);
		WebTarget wt = this.wsClient.target(CATALOG_URI + "/courses/" + course.getCourseCode() );
        Invocation webServiceCall = wt.request()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .build("PUT", Entity.json(jsono));
        JsonObject result = webServiceCall.invoke(JsonObject.class);
        Course newCourse = CatalogUtils.json2course( result  );
        return newCourse;
	}

	public Course deleteCourse(String courseCode)  {
		WebTarget wt = this.wsClient.target(CATALOG_URI + "/courses/" + courseCode );
		Invocation webServiceCall = wt.request().accept(MediaType.APPLICATION_JSON).build("DELETE");
		JsonObject jsono = webServiceCall.invoke(JsonObject.class);
		Course course = null;
		try {
			course = CatalogUtils.json2course( jsono );
		} catch (ImproperInputException e) {
			System.out.println(e.getClass() + ": " + e.getMessage());
		} 
		return course;
	}

	public Course updateCourse(Course course) throws ImproperInputException {
		JsonObject jsono = CatalogUtils.course2json(course);
		WebTarget wt = this.wsClient.target(CATALOG_URI + "/courses/" + course.getCourseCode() );
        Invocation webServiceCall = wt.request()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .build("POST", Entity.json(jsono));
        JsonObject result = webServiceCall.invoke(JsonObject.class);
        Course newCourse = CatalogUtils.json2course( result  );
        return newCourse;
	}
	// alternative update that does not include course code in URI
	public Course updateVersion2(Course course) throws ImproperInputException {
		JsonObject jsono = CatalogUtils.course2json(course);
		WebTarget wt = this.wsClient.target(CATALOG_URI + "/courses/updateCourse" );
        Invocation webServiceCall = wt.request()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .build("POST", Entity.json(jsono));
        JsonObject result = webServiceCall.invoke(JsonObject.class);
        Course newCourse = CatalogUtils.json2course( result  );
        return newCourse;
	}
}