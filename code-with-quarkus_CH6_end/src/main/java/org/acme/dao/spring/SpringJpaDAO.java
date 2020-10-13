package org.acme.dao.spring;

import org.acme.entity.Anagram;
import org.acme.entity.AnagramSource;
import org.acme.repository.spring.SpringAnagramRepository;
import org.acme.util.Scrambler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;

@ApplicationScoped
public class SpringJpaDAO {

    @Inject
    SpringAnagramRepository anagramRepository;

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = {SQLException.class, IOException.class})
    public String generateAndSaveAnagram(String sourceText){
        String result = Scrambler.scramble(sourceText);
        AnagramSource anagramSource = new AnagramSource();
        anagramSource.setAnagramSourceText(sourceText);
        Anagram anagram = new Anagram();
        anagram.setAnagramText(result);
        anagram.setAnagramSource(anagramSource);
        anagramRepository.save(anagram);
        return anagram.getAnagramText();
    }
}
