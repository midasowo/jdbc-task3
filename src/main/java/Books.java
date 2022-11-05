public class Books {

    private  final int bookId;
    private final String name;
    private final String author;
    private final String publisher;

    public Books(int bookId, String name, String author, String publisher) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
    }

    public int getBookId() {
        return bookId;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String toString() {
        return "Books{" +
                "bookId=" + bookId +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
