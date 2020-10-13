package org.acme.repository.spring;

import org.acme.entity.AnagramSource;
import org.springframework.data.repository.CrudRepository;

public interface AnagramSourceSpringRepository extends CrudRepository<AnagramSource,Long> {

    public AnagramSource findByAnagramSourceText(String sourceText);
}
