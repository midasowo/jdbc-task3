import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;


public class Homework3Test {

    @Test
    void shouldReturnThreeBooksRentedButNotReturned() {
        //WHEN
        final var books = Homework3.getRentedAndNotReturnedBooks
                (ApplicationPropertiesProvider.getApplicationProperties().getProperty("jdbc.url"));

        //THEN
        assertThat(books).extracting(Books::getName, Books::getAuthor, Books::getPublisher).containsExactly(
                tuple("Wojna Makowa", "Rebecca F. Kuang", "Fabryka Slow"),
                tuple("Upadek Gondolinu", "J.R.R. Tolkien", "Proszynski Media"),
                tuple("Xiao Long. Bialy Tygrys", "Dawid Juraszek", "Fabryka Slow")
        );
    }

}
