package ru.zeovl.musicstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zeovl.musicstore.models.ProductType;
import ru.zeovl.musicstore.repositories.ProductTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductTypeService {
    private final ProductTypeRepository ProductTypeRepository;

    @Autowired
    public ProductTypeService(ProductTypeRepository ProductTypeRepository) {
        this.ProductTypeRepository = ProductTypeRepository;
    }

    public List<ProductType> findAll() {
        return ProductTypeRepository.findAll();
    }

    public ProductType findById(int id) {
        Optional<ProductType> ProductType = ProductTypeRepository.findById(id);
        return ProductType.orElse(null);
    }

    @Transactional
    public ProductType save(ProductType ProductType) {
        return ProductTypeRepository.save(ProductType);
    }

    @Transactional
    public void delete(ProductType ProductType) {
        ProductTypeRepository.delete(ProductType);
    }
}
