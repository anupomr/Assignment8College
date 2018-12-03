package college.courses.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import college.courses.exceptions.CourseNotFoundException;

public class ResultCourseNotFound implements ExceptionMapper<CourseNotFoundException> {

	@Override
	public Response toResponse(CourseNotFoundException exception) {
        Response r = Response.serverError()
                .status(Response.Status.NOT_FOUND)
                .entity(exception.getMessage())
                .build();
        return r;
	}
	
}
