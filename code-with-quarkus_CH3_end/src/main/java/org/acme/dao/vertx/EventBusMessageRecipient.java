package org.acme.dao.vertx;

import io.quarkus.vertx.ConsumeEvent;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.Message;
import org.acme.dto.EventMessage;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.logging.Logger;


public class EventBusMessageRecipient {

    @Inject
    Vertx vertx;

    final Logger logger = Logger.getLogger(EventBusMessageRecipient.class.getName());

    @ConsumeEvent("one-way-message")
    public void receiveOneWayMessage(Message<EventMessage> message){
        logger.info("Received one way message: "+message.body().getMessage());
    }

    @ConsumeEvent("two-way-message")
    public EventMessage receiveTwoWayMessage(String message){
        logger.info("Received two-way message "+message);
        EventMessage response = new EventMessage();
        response.setMessage("Message received");
        return response;
    }

    @ConsumeEvent("multi-cast")
    public void coReceiveOneWayMessage(Message<EventMessage> message){
        logger.info("Received broadcast message");
    }

    @ConsumeEvent("multi-cast")
    public void coReceiveOneWayMessage(EventMessage message){
        logger.info("Received broadcast message "+message.getMessage());
    }

    @ConsumeEvent(value = "blocking-two-way-message", blocking = true)
    public void receiveBlockingTwoWayMessage(Message<String> message){
        logger.info("Message received in blocking handler: "+message.body());
        vertx.setTimer(5, timeout ->{
            logger.info("Sending response after "+timeout);
            message.reply("Here's the response");
        });
    }



}