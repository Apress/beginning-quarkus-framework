package org.acme.dto;


public class AnagramRequest {

    String sourceText;

    public String getSourceText(){
        return this.sourceText;
    }

    public void setSourceText(String sourceText){
        this.sourceText = sourceText;
    }

}