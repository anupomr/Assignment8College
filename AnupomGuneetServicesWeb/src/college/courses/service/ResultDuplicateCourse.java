package college.courses.service;

import javax.ws.rs.ext.ExceptionMapper;

import college.courses.exceptions.DuplicateCourseException;

import javax.ws.rs.core.Response;

public class ResultDuplicateCourse implements ExceptionMapper<DuplicateCourseException> {

	@Override
	public Response toResponse(DuplicateCourseException exception) {
		Response r = Response.serverError().status(Response.Status.CONFLICT).entity(exception.getMessage()).build();
		return r;
	}

}
