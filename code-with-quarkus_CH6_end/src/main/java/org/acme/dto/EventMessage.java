package org.acme.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class EventMessage {

	public EventMessage(){}

	public EventMessage(String message){
		this.message = message;
	}

    String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
