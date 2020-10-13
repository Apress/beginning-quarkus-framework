package org.acme.dao.panache;

import org.acme.entity.Anagram;
import org.acme.entity.AnagramSource;
import org.acme.util.Scrambler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.*;
import java.io.IOException;
import java.sql.SQLException;

@ApplicationScoped
@Named("panacheDAO")
public class AnagramPanacheDAO {

    @Inject
    UserTransaction userTransaction;

    public String generateAndSaveAnagram(String sourceText) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        String result = Scrambler.scramble(sourceText);
        AnagramSource anagramSource = new AnagramSource();
        anagramSource.setAnagramSourceText(sourceText);
        Anagram anagram = new Anagram();
        anagram.setAnagramText(result);
        userTransaction.begin();
        anagramSource.persist();
        anagram.setAnagramSource(anagramSource);
        anagram.persist();
        userTransaction.commit();
        return anagram.getAnagramText();
    }

}
