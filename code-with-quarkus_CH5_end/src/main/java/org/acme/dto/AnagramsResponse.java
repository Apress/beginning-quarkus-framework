package org.acme.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.acme.entity.Anagram;

import java.util.ArrayList;
import java.util.List;

@RegisterForReflection
public class AnagramsResponse {

    List<AnagramResponse> anagrams;

    public static AnagramsResponse fromAnagrams(List<Anagram> anagrams){
        AnagramsResponse response = new AnagramsResponse();
        List<AnagramResponse> anagramResponses = new ArrayList<AnagramResponse>(anagrams.size());
        for(int i=0; i<=anagrams.size();i++){
            anagramResponses.add(AnagramResponse.fromAnagram(anagrams.get(i)));
        }
        response.setAnagrams(anagramResponses);
        return response;
    }

    public List<AnagramResponse> getAnagrams() {
        return anagrams;
    }

    public void setAnagrams(List<AnagramResponse> anagrams) {
        this.anagrams = anagrams;
    }
}
