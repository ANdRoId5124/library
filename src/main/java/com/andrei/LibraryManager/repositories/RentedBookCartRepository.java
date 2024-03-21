package com.andrei.LibraryManager.repositories;

import com.andrei.LibraryManager.entities.RentedBookCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentedBookCartRepository extends JpaRepository<RentedBookCart, Long> {
}
