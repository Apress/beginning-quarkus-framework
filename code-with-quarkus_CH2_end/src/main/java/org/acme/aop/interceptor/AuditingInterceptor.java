package org.acme.aop.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Audited
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class AuditingInterceptor implements Serializable {

    final Logger logger = LoggerFactory.getLogger(AuditingInterceptor.class);

    @AroundInvoke
    public Object logExecutions(InvocationContext invocationContext) throws Exception {
        logger.info("Method: "+ invocationContext.getMethod().
                getName());
        logger.info("Arguments: "+invocationContext.
                getParameters());
        logger.info("Executing the called method");
        Object possibleReturn = invocationContext.proceed();
        logger.info("The object that the method was invoked on:"+invocationContext.getTarget());
        return possibleReturn;
    }

}
