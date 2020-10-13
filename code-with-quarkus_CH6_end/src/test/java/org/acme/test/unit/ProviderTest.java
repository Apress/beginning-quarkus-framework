package org.acme.test.unit;

import org.acme.provider.exception.JAXRSExceptionMapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class ProviderTest {

    @Inject
    Provider<ExceptionMapper<IllegalArgumentException>> exceptionMapperProvider;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(JAXRSExceptionMapper.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testMapper(){
        ExceptionMapper<IllegalArgumentException> mapper = exceptionMapperProvider.get();
        Response response = mapper.toResponse((IllegalArgumentException) new Exception("A generic failure"));
        assertEquals(400, response.getStatus());
    }
}
