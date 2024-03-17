package repositories;

import entities.RentedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentedBookRepository extends JpaRepository<RentedBook, Long> {

}
