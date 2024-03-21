package com.andrei.LibraryManager.repositories;

import com.andrei.LibraryManager.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  Book findBookByTitle(String title);
}
