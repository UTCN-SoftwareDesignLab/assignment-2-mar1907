package service.recommendation;

import booksapi.ClientCredentials;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import model.Book;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleRecommendationService implements RecommendationService<Book> {
    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "sm-sd2/1.0";

    @Override
    public List<Book> recomendByTitle(String title) throws GeneralSecurityException, IOException {
        ClientCredentials.errorIfNotSpecified();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        // Set up Books client.
        final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                .setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
                .build();


        Books.Volumes.List volumesList = books.volumes().list("intitle:"+title);

        Volumes volumes = volumesList.execute();

        List<Book> bookList = new ArrayList<>();
        for(Volume volume:volumes.getItems()){
            Book book = new Book();

            Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();

            book.setTitle(volumeInfo.getTitle());
            if (volumeInfo.getAuthors() != null && !volumeInfo.getAuthors().isEmpty()) {
                book.setAuthor(volumeInfo.getAuthors().get(0));
            } else {
                book.setAuthor("Unknown");
            }
            if (volumeInfo.getCategories() != null && !volumeInfo.getCategories().isEmpty()) {
                book.setGenre(volumeInfo.getCategories().get(0));
            } else {
                book.setGenre("Unknown");
            }
            bookList.add(book);
        }

        return bookList;
    }
}