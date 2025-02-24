package ru.zeovl.musicstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zeovl.musicstore.models.Photo;
import ru.zeovl.musicstore.repositories.PhotoRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PhotoService {
    private final PhotoRepository PhotoRepository;

    @Autowired
    public PhotoService(PhotoRepository PhotoRepository) {
        this.PhotoRepository = PhotoRepository;
    }

    public List<Photo> findAll() {
        return PhotoRepository.findAll();
    }

    public Photo findById(int id) {
        Optional<Photo> Photo = PhotoRepository.findById(id);
        return Photo.orElse(null);
    }

    @Transactional
    public Photo save(Photo Photo) {
        return PhotoRepository.save(Photo);
    }

    @Transactional
    public void delete(Photo Photo) {
        PhotoRepository.delete(Photo);
    }
}
