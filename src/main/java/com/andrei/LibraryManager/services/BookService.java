package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.entities.Book;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.andrei.LibraryManager.repositories.BookRepository;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository BOOK_REPOSITORY;


  public Book addBook(Book book) {
    return BOOK_REPOSITORY.save(book);
  }

  public Book updateBook(Book book) {
    return BOOK_REPOSITORY.save(book);
  }

  public void deleteBook(Book book) {
    BOOK_REPOSITORY.delete(book);
  }

  public Optional<Book> getBookByTitleAndByAuthor(String title, String author) {
    return BOOK_REPOSITORY.findBookByTitleAndAuthor(title, author);
  }
}
