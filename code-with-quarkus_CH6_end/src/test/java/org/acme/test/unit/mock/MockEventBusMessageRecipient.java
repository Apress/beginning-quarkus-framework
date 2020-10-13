package org.acme.test.unit.mock;

import io.quarkus.arc.AlternativePriority;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.acme.dao.vertx.EventBusMessageRecipient;
import org.acme.dto.EventMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;

/* @Mock is hard coded to Priority(1), meaning it will ALWAYS override the original bean :(
 *
 */
//@Mock
public class MockEventBusMessageRecipient extends EventBusMessageRecipient {

    public EventMessage receiveTwoWayMessage(String message){
        EventMessage response = new EventMessage();
        response.setMessage("Mock Message received");
        return response;
    }

}
