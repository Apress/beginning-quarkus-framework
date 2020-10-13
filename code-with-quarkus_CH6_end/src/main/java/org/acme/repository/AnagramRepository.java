package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.entity.Anagram;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.LockModeType;

@ApplicationScoped
public class AnagramRepository implements PanacheRepository<Anagram> {

    public Anagram findByAnagramText(String anagramText){
         PanacheQuery<Anagram> result = find("anagramText",anagramText, LockModeType.PESSIMISTIC_READ);
         return result.singleResult();
    }
}
