import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Homework3 {
    public static void main(String[] args) throws RentalException {

        final String jdbcUrl = ApplicationPropertiesProvider.getApplicationProperties().getProperty("jdbc.url");

        showRentedNotReturnedBooks(jdbcUrl);
        System.out.println("\n");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj ID ksiązki jaką chcesz wypożyczyć");
        int bookToRent = scanner.nextInt();

        final var result = rentBook(jdbcUrl, bookToRent, 1);

        if (result.isPresent()) {
            System.out.printf("Id wypożyczenia: %d\n", result.get());
        }
        else {
            System.out.println("Brak książki na stanie :(");
        }
    }

    private static void showRentedNotReturnedBooks(String jdbcUrl) {
        final var booksStrings = getRentedAndNotReturnedBooks(jdbcUrl).stream()
                .map(Books::toString)
                .collect(Collectors.toList());
        System.out.printf("Wypożyczone, ale nie oddane książki:\n%s", String.join("\n", booksStrings));
    }

    public static List<Books> getRentedAndNotReturnedBooks(String jdbcUrl) {
        final List<Books> books = new LinkedList<>();
        try (final Connection connection = DriverManager.getConnection(jdbcUrl)) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT books.ID, books.`name`, books.author, books.publisher" +
                    " FROM books JOIN rents ON books.ID = rents.book_id WHERE rents.return_date IS NULL ORDER BY books.ID;");
            while (resultSet.next()) {
                int bookId = resultSet.getInt(1);
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                Books book = new Books(bookId, name, author, publisher);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static Optional<Integer> rentBook(String jdbcUrl, int bookId, int cusId) throws RentalException {
        try (final var connection = DriverManager.getConnection(jdbcUrl)) {
            try {
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                if (!isInventoryInStock(connection, bookId)) {
                    return Optional.empty();
                }
                final var rentalId = addRental(connection, bookId, cusId);
                connection.commit();
                return Optional.of(rentalId);
            } catch (Exception exception) {
                connection.rollback();
                throw exception;
            }
        }
        catch (SQLException sqlException) {
            throw new RentalException(sqlException);
        }
    }

    private static boolean isInventoryInStock(Connection connection, int bookId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(rents.ID) = 0 FROM rents JOIN books ON rents.book_id = books.ID WHERE book_id = ? AND rents.return_date IS NULL;");
        preparedStatement.setInt(1, bookId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getBoolean(1);
    }

    private static int addRental(Connection connection, int bookId, int cusId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO rents(book_id, cus_id, rent_date)" +
                "VALUES(?, ?, NOW());", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, bookId);
        preparedStatement.setInt(2, cusId);
        preparedStatement.executeUpdate();
        final var rs = preparedStatement.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }


}
