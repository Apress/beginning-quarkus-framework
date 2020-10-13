package org.acme.dto;

public class AnagramResponse {
    String anagram;

    public String getAnagram() {
        return this.anagram;
    }

    public void setAnagram(String anagram) {
        this.anagram = anagram;
    }

    @Override
    public String toString() {
        return "AnagramResponse{" +
                "anagram='" + anagram + '\'' +
                '}';
    }
}