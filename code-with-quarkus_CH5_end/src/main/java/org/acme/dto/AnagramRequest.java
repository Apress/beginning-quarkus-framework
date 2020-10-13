package org.acme.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class AnagramRequest {

    String sourceText;

    public String getSourceText(){
        return this.sourceText;
    }

    public void setSourceText(String sourceText){
        this.sourceText = sourceText;
    }

}