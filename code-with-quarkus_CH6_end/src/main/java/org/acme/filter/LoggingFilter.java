package org.acme.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Provider
@Priority(1)
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private final Logger logger = LoggerFactory.getLogger(LoggingFilter.class.getName());

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
                logger.info("Message on the way out "+responseContext.getEntity());
		
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		BufferedInputStream iStream = new BufferedInputStream(requestContext.getEntityStream());
		byte[] inputContent = new byte[iStream.available()];
		iStream.read(inputContent);
		String body = new String(inputContent, "utf-8");
		logger.info("Inside request filter. Message size: "+inputContent.length+"; Message on the way in: "+body);
		requestContext.setEntityStream(new ByteArrayInputStream(inputContent));
		
	}

}