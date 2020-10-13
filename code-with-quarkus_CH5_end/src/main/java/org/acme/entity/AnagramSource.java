package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "anagram_source")
public class AnagramSource extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "anagram_source_id_seq")
    public Long id;

    @Column(name = "anagram_source_text", unique = true,nullable = false)
    public  String anagramSourceText;

    @OneToMany(mappedBy = "anagramSource",fetch = FetchType.LAZY)
    public List<Anagram> anagrams;

    public static List<AnagramSource> findByValue(String value){
        return (List<AnagramSource>) findAll(Sort.ascending("anagram_source_text"));
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnagramSourceText() {
        return anagramSourceText;
    }

    public void setAnagramSourceText(String anagramSourceText) {
        this.anagramSourceText = anagramSourceText;
    }

    public List<Anagram> getAnagrams() {
        return anagrams;
    }

    public void setAnagrams(List<Anagram> anagrams) {
        this.anagrams = anagrams;
    }
}
