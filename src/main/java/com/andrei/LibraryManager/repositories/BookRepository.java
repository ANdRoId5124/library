package com.andrei.LibraryManager.repositories;

import com.andrei.LibraryManager.entities.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  Optional<Book> findBookByTitle(String title);
}
