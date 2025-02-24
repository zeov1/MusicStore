package ru.zeovl.musicstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zeovl.musicstore.models.Product;
import ru.zeovl.musicstore.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository ProductRepository;

    @Autowired
    public ProductService(ProductRepository ProductRepository) {
        this.ProductRepository = ProductRepository;
    }

    public List<Product> findAll() {
        return ProductRepository.findAll();
    }

    public Product findById(int id) {
        Optional<Product> Product = ProductRepository.findById(id);
        return Product.orElse(null);
    }

    @Transactional
    public Product save(Product Product) {
        return ProductRepository.save(Product);
    }

    @Transactional
    public void delete(Product Product) {
        ProductRepository.delete(Product);
    }
}
