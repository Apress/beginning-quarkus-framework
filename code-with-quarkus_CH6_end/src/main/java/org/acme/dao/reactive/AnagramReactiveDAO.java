package org.acme.dao.reactive;


import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import org.acme.dto.AnagramResponse;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@ApplicationScoped
public class AnagramReactiveDAO {

    @Inject
    PgPool reactivePgPool;

    final Logger logger = Logger.getLogger(AnagramReactiveDAO.class.getName());

    public List<AnagramResponse> findAllAnagramsBySourceId(long id) throws SQLException, ExecutionException, InterruptedException {
        return reactivePgPool.preparedQuery("select * from anagram where anagram_source_id = $1").execute(Tuple.of(id))
                .onItem()
                .produceMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem()
                .apply(anagramRow -> {
                    logger.info("Anagram text is " + anagramRow.getString("anagram_text"));
                    return AnagramResponse.fromRow(anagramRow);
                })
                .subscribe()
                .asStream()
                .collect(Collectors.toList());
    }

    public Uni<List<AnagramResponse>> findAllAnagramsBySourceId(Long id) throws SQLException, ExecutionException, InterruptedException {
        return reactivePgPool.preparedQuery("select * from anagram where anagram_source_id = $1").execute(Tuple.of(id))
                .onItem()
                .produceMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem()
                .apply(AnagramResponse::fromRow)
                .onItem()
                .apply(anagramResponse -> {
                    logger.info("Anagram response" + anagramResponse);
                    return anagramResponse;
                })
                .collectItems()
                .asList();
    }

}
