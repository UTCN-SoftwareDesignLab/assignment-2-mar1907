package service.recommendation;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface RecommendationService<T> {
    List<T> recomendByTitle(String title) throws GeneralSecurityException, IOException;
}
