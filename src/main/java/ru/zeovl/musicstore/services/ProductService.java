package ru.zeovl.musicstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zeovl.musicstore.models.Manufacturer;
import ru.zeovl.musicstore.models.Photo;
import ru.zeovl.musicstore.models.Product;
import ru.zeovl.musicstore.models.ProductType;
import ru.zeovl.musicstore.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository ProductRepository) {
        this.productRepository = ProductRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        Optional<Product> Product = productRepository.findById(id);
        return Product.orElse(null);
    }

    @Transactional
    public Product save(Product Product) {
        return productRepository.save(Product);
    }

    @Transactional
    public void delete(Product Product) {
        productRepository.delete(Product);
    }

    public List<Product> findByManufacturer(Manufacturer manufacturer) {
        return productRepository.findByManufacturer(manufacturer);
    }

    public List<Product> findByProductType(ProductType productType) {
        return productRepository.findByProductType(productType);
    }

    public List<Product> findByPhotosContaining(Photo photo) {
        return productRepository.findByPhotosContaining(photo);
    }

    @Transactional
    public void update(int id, Product product) {
        Product productToBeUpdated = productRepository.findById(id).orElse(new Product());
        productToBeUpdated.setName(product.getName());
        productToBeUpdated.setDescription(product.getDescription());
        productToBeUpdated.setPrice(product.getPrice());
        productToBeUpdated.setAmount(product.getAmount());
        productToBeUpdated.setUnitsSold(product.getUnitsSold());
        productToBeUpdated.setIsArchived(product.getIsArchived());
    }

    @Transactional
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}
