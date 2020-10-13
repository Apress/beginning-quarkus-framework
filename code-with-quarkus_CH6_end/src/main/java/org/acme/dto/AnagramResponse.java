package org.acme.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.vertx.mutiny.sqlclient.Row;
import org.acme.entity.Anagram;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RegisterForReflection
public class AnagramResponse {
    String anagram;

    public String getAnagram() {
        return this.anagram;
    }

    public void setAnagram(String anagram) {
        this.anagram = anagram;
    }

    public static AnagramResponse fromString(String anagramText) {
        AnagramResponse response = new AnagramResponse();
        response.setAnagram(anagramText);
        return response;
    }

    public static AnagramResponse fromRow(Row row){
        AnagramResponse response =  new AnagramResponse();
        response.setAnagram(row.getString("anagram_text"));
        return response;
    }

    public static List<AnagramResponse> fromRows(List<Row> rows){
        return rows.stream().map(AnagramResponse::fromRow).collect(Collectors.toList());
    }

    public static AnagramResponse fromAnagram(Anagram anagram){
        return fromString(anagram.getAnagramText());
    }


    @Override
    public String toString() {
        return "AnagramResponse{" +
                "anagram='" + anagram + '\'' +
                '}';
    }
}