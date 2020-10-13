package org.acme.provider.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JAXRSExceptionMapper implements ExceptionMapper<Exception> {

	final String EXCEPTION_MESSAGE = "invalid request data: ";
	final String RESPONSE_TYPE = "text/plain";

    @Override
    public Response toResponse(Exception exception) {
        return Response.serverError()
				.entity(EXCEPTION_MESSAGE + exception.getMessage())
				.type(RESPONSE_TYPE)
				.status(400)
				.build();
    }

}