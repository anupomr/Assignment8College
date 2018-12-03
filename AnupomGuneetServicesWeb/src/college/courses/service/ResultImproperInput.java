package college.courses.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import college.courses.exceptions.ImproperInputException;

public class ResultImproperInput implements ExceptionMapper<ImproperInputException> {
	@Override
	public Response toResponse(ImproperInputException exception) {
        Response r = Response.serverError()
                .status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .build();
        return r;
	}
}
