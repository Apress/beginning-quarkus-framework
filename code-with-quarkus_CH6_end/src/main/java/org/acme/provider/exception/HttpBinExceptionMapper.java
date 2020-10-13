package org.acme.provider.exception;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.concurrent.ExecutionException;

@Provider
public class HttpBinExceptionMapper implements ResponseExceptionMapper<ExecutionException>{

	@Override
	public ExecutionException toThrowable(Response response) {
		// do something with the content of the response and rethrow the exception
		response.bufferEntity();
		response.readEntity(String.class);
		return null;
	}

}