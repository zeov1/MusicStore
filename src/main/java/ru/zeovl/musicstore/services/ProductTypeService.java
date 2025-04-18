package ru.zeovl.musicstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zeovl.musicstore.models.ProductType;
import ru.zeovl.musicstore.repositories.ProductTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeService(ProductTypeRepository ProductTypeRepository) {
        this.productTypeRepository = ProductTypeRepository;
    }

    public List<ProductType> findAll() {
        return productTypeRepository.findAll();
    }

    public List<ProductType> findAllOrderedById() {
        return productTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public ProductType findById(int id) {
        Optional<ProductType> ProductType = productTypeRepository.findById(id);
        return ProductType.orElse(null);
    }

    @Transactional
    public ProductType save(ProductType ProductType) {
        return productTypeRepository.save(ProductType);
    }

    @Transactional
    public void delete(ProductType ProductType) {
        productTypeRepository.delete(ProductType);
    }

    @Transactional
    public void update(int id, ProductType productType) {
        productType.setId(id);
        productTypeRepository.save(productType);
    }

    @Transactional
    public void deleteById(int id) {
        productTypeRepository.deleteById(id);
    }
}
