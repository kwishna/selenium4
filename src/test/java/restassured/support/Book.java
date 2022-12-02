package restassured.support;

import org.testng.annotations.Ignore;

import java.util.Objects;

@Ignore
public class Book {
    private String category;
    private String author;
    private String title;
    private String isbn;
    private float price;

    public Book(String category, String author, String title, String isbn, float price) {
        this.category = category;
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
    }

    public Book() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (Float.compare(book.price, price) != 0) return false;
        if (!Objects.equals(author, book.author)) return false;
        if (!Objects.equals(category, book.category)) return false;
        if (!Objects.equals(isbn, book.isbn)) return false;
        return Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        return result;
    }
}