import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Homework3 {
    public static void main(String[] args) {

        final String jdbcUrl = ApplicationPropertiesProvider.getApplicationProperties().getProperty("jdbc.url");

        final var booksStrings = getRentedAndNotReturnedBooks(jdbcUrl).stream()
                .map(Books::toString)
                .collect(Collectors.toList());

        System.out.printf("Wypożyczone, ale nie oddane książki:\n\n%s", String.join("\n", booksStrings));
    }

    public static List<Books> getRentedAndNotReturnedBooks(String jdbcUrl) {
        final List<Books> books = new LinkedList<>();
        try (final Connection connection = DriverManager.getConnection(jdbcUrl)) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT books.`name`, books.author, books.publisher" +
                    " FROM books JOIN rents ON books.ID = rents.book_id WHERE rents.return_date IS NULL;");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                Books book = new Books(name, author, publisher);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }


}
