package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.entities.Book;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.andrei.LibraryManager.repositories.BookRepository;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;


  public Book addBook(Book book) {
    return bookRepository.save(book);
  }

  public Book updateBook(Book book) {
    return bookRepository.save(book);
  }

  public void deleteBook(Book book) {
    bookRepository.delete(book);
  }

  public Optional<Book> getBookByTitleAndByAuthor(String title, String author) {
    return bookRepository.findBookByTitleAndAuthor(title, author);
  }
}
