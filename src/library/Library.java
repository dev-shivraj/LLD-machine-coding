package library;

import library.exceptions.BookNotFoundException;
import library.exceptions.DuplicateBookException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Library {

    private final Map<String, Book> booksByIsbn = new HashMap<>();

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        String isbn = book.getIsbn();

        if (booksByIsbn.containsKey(isbn)) {
            throw new DuplicateBookException("Book with ISBN " + isbn + " already exists");
        }

        booksByIsbn.put(isbn, book);
    }

    public void removeBook(String isbn) {
        if (!booksByIsbn.containsKey(isbn)) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found");
        }

        booksByIsbn.remove(isbn);
    }

    public Optional<Book> searchByIsbn(String isbn) {
        return Optional.ofNullable(booksByIsbn.get(isbn));
    }

    public List<Book> searchByTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Search title cannot be null or empty");
        }

        String searchTerm = title.toLowerCase();

        List<Book> result = new ArrayList<>();

        for (Book book : booksByIsbn.values()) {
            if (book.getTitle().toLowerCase().contains(searchTerm)) {
                result.add(book);
            }
        }

        return result;
    }

    public List<Book> listAllBooks() {
        return new ArrayList<>(booksByIsbn.values());
    }
}