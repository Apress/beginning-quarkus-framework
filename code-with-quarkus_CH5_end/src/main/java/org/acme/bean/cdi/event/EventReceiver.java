package org.acme.bean.cdi.event;

import org.acme.bean.cdi.qualifier.Gossip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;

public class EventReceiver {

    final Logger logger = LoggerFactory.getLogger(EventReceiver.class);

    public void listenForNews(@Observes InterestingEvent event) {
        logger.info("Got some interesting news: " + event.getMessage());
    }

    public void listenForGossip(@Observes @Gossip InterestingEvent event) {
        logger.info("Got some interesting gossip:" + event.getMessage());
    }

    public void listenForGossipAsync(@ObservesAsync InterestingEvent event){
        logger.info("Got some interesting gossip asynchronously: "+event.getMessage());
    }

}
