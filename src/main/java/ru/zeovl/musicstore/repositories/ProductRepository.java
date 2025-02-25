package ru.zeovl.musicstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zeovl.musicstore.models.Manufacturer;
import ru.zeovl.musicstore.models.Photo;
import ru.zeovl.musicstore.models.Product;
import ru.zeovl.musicstore.models.ProductType;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByManufacturer(Manufacturer manufacturer);
    List<Product> findByProductType(ProductType productType);
    List<Product> findByPhotosContaining(Photo photo);
}
