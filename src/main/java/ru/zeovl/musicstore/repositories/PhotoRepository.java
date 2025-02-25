package ru.zeovl.musicstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zeovl.musicstore.models.Photo;
import ru.zeovl.musicstore.models.Product;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    List<Photo> getByProductsContaining(Product product);
}
