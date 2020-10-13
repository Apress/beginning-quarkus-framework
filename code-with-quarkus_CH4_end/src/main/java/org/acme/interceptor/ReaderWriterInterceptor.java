package org.acme.interceptor;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

@javax.ws.rs.ext.Provider
public class ReaderWriterInterceptor implements ReaderInterceptor, WriterInterceptor {

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        final OutputStream outputStream = context.getOutputStream();
        encryptPayload(outputStream);
        //TODO compress the payload if you like
        //context.setOutputStream(new GZIPOutputStream(outputStream));
        context.proceed();
    }

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
		final InputStream inputStream = context.getInputStream();
		context.setInputStream(decryptPayload(inputStream));
		return context.proceed();
    }


    public String encryptPayload(OutputStream stream) {
		//TODO encryption business here!
		return null;
    }

    public InputStream decryptPayload(InputStream inputStream){
    	//TODO decryption business here!
		return inputStream;
	}

}