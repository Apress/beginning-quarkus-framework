package org.acme.dao.hibernate.reactive;

import io.quarkus.agroal.DataSource;
import io.quarkus.arc.profile.IfBuildProfile;
import io.smallrye.mutiny.Uni;
import org.acme.dto.AnagramResponse;
import org.acme.entity.Anagram;
//import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class AnagramHibernateReactive {

    /* Because Hibernate Reactive cannot co-exist with regular JDBC or Hibernate, this implementation is commented out
      To use Hibernate-rx code here:
      1. Uncomment the two Hibernate dependencies in the POM: quarkus-hibernate-reactive and quarkus-hibernate-reactive-deployment
      2. Then uncomment this implementation

     */
    /*
    @Inject
    Uni<Mutiny.Session> mutinySession;

    public Uni<Response> findAnagramById(Long anagramSourceId) {
        AnagramResponse response = mutinySession.flatMap(
                session -> session.find(Anagram.class,
                        Long.valueOf(anagramSourceId)))
                .onItem()
                .ifNull()
                .failWith(new IllegalArgumentException("No anagram found for that Id"))
                .onItem()
                .apply(AnagramResponse::fromAnagram)
                .await()
                .indefinitely();

        return Uni.createFrom()
                .item(Response.ok()
                        .entity(response)
                        .build());
    }

     */
}


