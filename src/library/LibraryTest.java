package library;

import library.exceptions.BookNotFoundException;
import library.exceptions.DuplicateBookException;

import java.util.List;

public class LibraryTest {

    public static void main(String[] args) {

        Library library = new Library();

        Book cleanCode = new Book("9780132350884", "Clean Code", "Robert C. Martin", 2008);
        Book effectiveJava = new Book("9780134685991", "Effective Java", "Joshua Bloch", 2018);
        Book cleanArchitecture = new Book("9780134494166", "Clean Architecture", "Robert C. Martin", 2017);

        // --------------------------------------------------
        // 1. Add books
        // --------------------------------------------------

        library.addBook(cleanCode);
        library.addBook(effectiveJava);
        library.addBook(cleanArchitecture);
        System.out.println("Books added successfully");


        // --------------------------------------------------
        // 2. Search by ISBN
        // --------------------------------------------------

        library.searchByIsbn("9780132350884")
                .ifPresent(book ->
                        System.out.println("ISBN Search: " + book.getTitle())
                );


        // --------------------------------------------------
        // 3. Search by title - partial + case insensitive
        // --------------------------------------------------

        List<Book> cleanBooks = library.searchByTitle("CLEAN");
        System.out.println("\nTitle Search with title 'CLEAN' :");

        for (Book book : cleanBooks) {
            System.out.println(book.getTitle());
        }


        // --------------------------------------------------
        // 4. List all books
        // --------------------------------------------------

        System.out.println("\nAll Books:");

        for (Book book : library.listAllBooks()) {
            System.out.println(book.getTitle() + " - " + book.getAuthor());
        }


        // --------------------------------------------------
        // 5. Change book title
        // --------------------------------------------------

        cleanCode.setTitle("Clean Code - Updated");
        System.out.println("\nUpdated Title: " + cleanCode.getTitle());


        // --------------------------------------------------
        // 6. Search after title update
        // --------------------------------------------------

        List<Book> updatedSearch = library.searchByTitle("updated");
        System.out.println("\nSearch After Title Update:");

        for (Book book : updatedSearch) {
            System.out.println(book.getTitle());
        }


        // --------------------------------------------------
        // 7. Duplicate ISBN
        // --------------------------------------------------

        try {
            Book duplicateBook = new Book("9780132350884", "Another Book", "Some Author", 2025);
            library.addBook(duplicateBook);
        } catch (DuplicateBookException e) {
            System.out.println("\nDuplicate Test: " + e.getMessage());
        }


        // --------------------------------------------------
        // 8. Remove book
        // --------------------------------------------------

        library.removeBook("9780134685991");
        System.out.println("\nBook removed successfully");


        // --------------------------------------------------
        // 9. Verify removal
        // --------------------------------------------------

        System.out.println("Search after removal: " + library.searchByIsbn("9780134685991").isPresent());


        // --------------------------------------------------
        // 10. Remove non-existing book
        // --------------------------------------------------

        try {
            library.removeBook("9999999999999");
        } catch (BookNotFoundException e) {
            System.out.println("Remove Missing Book Test: " + e.getMessage());
        }
    }
}