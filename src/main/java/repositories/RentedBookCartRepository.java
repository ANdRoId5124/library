package repositories;

import entities.RentedBookCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentedBookCartRepository extends JpaRepository<RentedBookCart, Long> {
}
