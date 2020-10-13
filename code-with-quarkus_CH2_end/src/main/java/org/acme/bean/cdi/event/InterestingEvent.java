package org.acme.bean.cdi.event;

public class InterestingEvent {
    String message;

    public InterestingEvent(String message){
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
