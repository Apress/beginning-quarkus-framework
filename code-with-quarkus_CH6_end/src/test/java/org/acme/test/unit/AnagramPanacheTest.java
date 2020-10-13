package org.acme.test.unit;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.acme.dao.panache.AnagramPanacheDAO;
import org.acme.dao.panache.AnagramPanacheRepositoryDAO;
import org.acme.entity.Anagram;
import org.acme.entity.AnagramSource;
import org.acme.repository.AnagramRepository;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.*;
import java.util.ArrayList;
import java.util.Collections;

@QuarkusTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Test panache components")
public class AnagramPanacheTest {

    Logger logger = LoggerFactory.getLogger(AnagramPanacheTest.class);

    @Inject
    @Named("panacheDAO")
    AnagramPanacheDAO anagramPanacheDAO;

    @Inject
    @Named("panacheRepoDAO")
    AnagramPanacheRepositoryDAO panacheRepositoryDAO;

    @InjectMock
    AnagramRepository anagramRepository;

    Anagram anagram;
    final static String ANAGRAM = "scrambledText";

    @Before
    public void init() throws SystemException, NotSupportedException {
        PanacheMock.mock(Anagram.class);
        PanacheMock.mock(AnagramSource.class);
        anagram = new Anagram();
        AnagramSource anagramSource = new AnagramSource();
        anagram.setAnagramSource(anagramSource);
        anagram.setId(101l);
        anagramSource.setAnagrams(Collections.singletonList(anagram));
        anagram.setAnagramSource(new AnagramSource());
        Mockito.verify(anagramRepository,Mockito.atMostOnce());
        //Mockito.verify(userTransaction, Mockito.atLeast(2));
        Mockito.when(anagramRepository.findByAnagramText(ANAGRAM)).thenReturn(anagram);
    }

    @Test
    public void test_mock_panache_repository(){
        panacheRepositoryDAO.generateAndSaveAnagram(ANAGRAM);
    }

    @Test
    public void test_mock_panache_repository_find() {
        Anagram anagram2 = anagramRepository.findByAnagramText(ANAGRAM);
        Assertions.assertEquals(anagram,anagram2);
    }

    public void test_mock_panache_entity_CRUD(){
        try {
            anagramPanacheDAO.generateAndSaveAnagram(ANAGRAM);
        }catch(Exception ex){
            ex.printStackTrace();
            Assertions.assertTrue(1==2);
        }
    }

}
