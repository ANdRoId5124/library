package services;

import entities.Book;
import org.springframework.stereotype.Service;
import repositories.BookRepository;

@Service
public class BookService {
  private final BookRepository BOOK_REPOSITORY;

  public BookService(BookRepository bookRepository) {
    BOOK_REPOSITORY = bookRepository;
  }

  public Book addBook(Book book){
    return BOOK_REPOSITORY.save(book);
  }

  public Book updateBook(Book book){
    return BOOK_REPOSITORY.save(book);
  }

  public void deleteBook(Book book){
    BOOK_REPOSITORY.delete(book);
  }

  public Book getBookByTitle(String title){ // check if book exist
    return BOOK_REPOSITORY.findBookByTitle(title);
  }
}
