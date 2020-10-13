package org.acme.dao.panache;

import org.acme.entity.Anagram;
import org.acme.entity.AnagramSource;
import org.acme.repository.AnagramRepository;
import org.acme.repository.AnagramSourceRepository;
import org.acme.util.Scrambler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;

@ApplicationScoped
@Named("panacheRepoDAO")
public class AnagramPanacheRepositoryDAO extends AnagramPanacheDAO {

    @Inject
    AnagramRepository anagramRepository;

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = {SQLException.class, IOException.class})
    public String generateAndSaveAnagram(String sourceText){
        String result = Scrambler.scramble(sourceText);
        AnagramSource anagramSource = new AnagramSource();
        anagramSource.setAnagramSourceText(sourceText);
        Anagram anagram = new Anagram();
        anagram.setAnagramText(result);
        anagram.setAnagramSource(anagramSource);
        anagramRepository.persist(anagram);
        return anagram.getAnagramText();
    }

}
