package service.recommendation;

import model.Book;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static org.junit.Assert.*;

public class RecommendationServiceTest {

    private RecommendationService<Book> recommendationService;

    @Before
    public void setUp(){
        recommendationService = new GoogleRecommendationService();
    }

    @Test
    public void recomendByTitle() {
        try {
            List<Book> books = recommendationService.recomendByTitle("Orlando");
            assertTrue(books.size()>0);
        } catch (GeneralSecurityException | IOException e) {
            assertFalse(false);
        }
    }
}