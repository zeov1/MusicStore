package ru.zeovl.musicstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zeovl.musicstore.models.Photo;
import ru.zeovl.musicstore.models.Product;
import ru.zeovl.musicstore.repositories.PhotoRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PhotoService {
    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository PhotoRepository) {
        this.photoRepository = PhotoRepository;
    }

    public List<Photo> findAll() {
        return photoRepository.findAll();
    }

    public Photo findById(int id) {
        Optional<Photo> Photo = photoRepository.findById(id);
        return Photo.orElse(null);
    }

    @Transactional
    public Photo save(Photo Photo) {
        return photoRepository.save(Photo);
    }

    @Transactional
    public void delete(Photo Photo) {
        photoRepository.delete(Photo);
    }

    public List<Photo> getByProductsContaining(Product product) {
        return photoRepository.getByProductsContaining(product);
    }
}
