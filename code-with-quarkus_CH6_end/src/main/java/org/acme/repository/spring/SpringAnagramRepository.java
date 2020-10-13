package org.acme.repository.spring;

import org.acme.entity.Anagram;
import org.springframework.data.repository.CrudRepository;

public interface SpringAnagramRepository extends CrudRepository<Anagram,Long> {
     Anagram findByAnagramText(String anagramText);
}
