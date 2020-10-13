package org.acme.java.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.acme.ExampleResource;
import org.acme.dto.AnagramRequest;
import org.acme.dto.AnagramResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("http-lambda-handler")
@ApplicationScoped
public class LambdaHandlerImpl implements RequestHandler<AnagramRequest, AnagramResponse> {

    @Inject
    ExampleResource restResource;

    final Logger logger = LoggerFactory.getLogger(LambdaHandlerImpl.class.getName());


	public AnagramResponse handleRequest(AnagramRequest input, Context context) {
        logger.info("Received serverless request: "+context.getAwsRequestId()+"; function version: "+context.getFunctionVersion());
        LambdaLogger logger = context.getLogger();
        logger.log("logging with the LambdaLogger");
		return restResource.persistAnagram(input);
	}

    
}