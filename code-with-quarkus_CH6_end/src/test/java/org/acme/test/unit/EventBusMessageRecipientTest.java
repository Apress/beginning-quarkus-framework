package org.acme.test.unit;


import io.quarkus.test.junit.QuarkusTest;
import org.acme.dao.vertx.EventBusMessageRecipient;
import org.acme.dto.EventMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@QuarkusTest
@DisplayName("Unit testing the EventMessageBusRecipient class")
public class EventBusMessageRecipientTest {

    @Inject
    @ApplicationScoped
    EventBusMessageRecipient eventBusBean;

    @Test
    @DisplayName("Test for two-way messaging with the Vert.x event bus")
    public void testEventBusTwoWay() {
        String message = "message to send";
        EventMessage eventResponse = eventBusBean.receiveTwoWayMessage(message);
        Assertions.assertNotNull(eventResponse);
        Assertions.assertEquals("Message received", eventResponse.getMessage());
    }
}
