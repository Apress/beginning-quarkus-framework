package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.entity.AnagramSource;

public interface AnagramSourceRepository extends PanacheRepository<AnagramSource> {
}
