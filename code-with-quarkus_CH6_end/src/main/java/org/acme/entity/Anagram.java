package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;


@Entity
public class Anagram extends PanacheEntityBase{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "anagram_anagram_id_seq")
    @Column(name = "anagram_id",nullable = false)
    public Long id;

    @Column(name="anagram_text")
    public String anagramText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anagram_source_id")
    public AnagramSource anagramSource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnagramText() {
        return anagramText;
    }

    public void setAnagramText(String anagramText) {
        this.anagramText = anagramText;
    }


    public AnagramSource getAnagramSource() {
        return anagramSource;
    }

    public void setAnagramSource(AnagramSource anagramSource) {
        this.anagramSource = anagramSource;
    }
}
