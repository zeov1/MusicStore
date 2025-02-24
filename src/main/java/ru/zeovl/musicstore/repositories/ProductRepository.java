package ru.zeovl.musicstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zeovl.musicstore.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
