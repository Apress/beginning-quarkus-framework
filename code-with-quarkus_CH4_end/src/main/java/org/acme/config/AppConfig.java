package org.acme.config;

import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(info =
    @Info(
        title = "Anagrams - The Quarkus Way",
        version = "0.0",
        description = "This is an Anagram API. Send a string into one of the 'scramble' endpoints and it'll scramble the text",
        license = @License(name = "Apache 2.0", url = "http://localhost:8080"),
        contact = @Contact(url = "http://www.apress.com", name = "Tayo", email = "tayo@somewhere.com")
    )
)
@LoginConfig(authMethod = "BASIC",realmName = "TCK-MP-JWT")
public class AppConfig extends Application {

}