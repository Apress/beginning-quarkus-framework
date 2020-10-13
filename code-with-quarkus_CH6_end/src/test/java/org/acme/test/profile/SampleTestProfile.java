package org.acme.test.profile;

import io.quarkus.test.junit.QuarkusTestProfile;
import org.acme.test.integration.MockSampleResource;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SampleTestProfile implements QuarkusTestProfile{

     
    @Override
    public Map<String, String> getConfigOverrides(){
        Map<String, String> propertyOverrides = new HashMap<>();
        propertyOverrides.put("quarkus.test.port", "8009");
        propertyOverrides.put("quarkus.application.name","override-hello-goodbye");
        return propertyOverrides;
    }

    public Set<Class<?>> getEnabledAlternatives(){
        return Collections.singleton(MockSampleResource.class);
    }

    public String getConfigProfile(){
        return "special-test";
    }

    
}