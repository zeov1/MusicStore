package ru.zeovl.musicstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zeovl.musicstore.models.Manufacturer;
import ru.zeovl.musicstore.repositories.ManufacturerRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public void testMethod() {
        System.out.println("Entering transaction now!");
        System.out.println("Exiting transaction now!");
    }

    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer findById(int id) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        return manufacturer.orElse(null);
    }

    @Transactional
    public Manufacturer save(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Transactional
    public void delete(Manufacturer manufacturer) {
        manufacturerRepository.delete(manufacturer);
    }
}
