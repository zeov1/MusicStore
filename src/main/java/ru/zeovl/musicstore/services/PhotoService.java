package ru.zeovl.musicstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.zeovl.musicstore.models.Photo;
import ru.zeovl.musicstore.models.Product;
import ru.zeovl.musicstore.repositories.PhotoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final FileSystemResource uploadDirResource;

    @Autowired
    public PhotoService(PhotoRepository PhotoRepository, FileSystemResource uploadDirResource) {
        this.photoRepository = PhotoRepository;
        this.uploadDirResource = uploadDirResource;
    }

    public List<Photo> findAll() {
        return photoRepository.findAll();
    }

    public List<Photo> findAllOrderedById() {
        return photoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Photo findById(int id) {
        Optional<Photo> photo = photoRepository.findById(id);
        return photo.orElse(null);
    }

    @Transactional
    public Photo save(Photo photo, MultipartFile file) throws IOException {
        File[] files = new File(uploadDirResource.getURL().getPath() + "/photos/").listFiles();
        String filename = photo.getImageName();
        String extension;
        if (file.getOriginalFilename().contains(".")) {
            extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        } else {
            extension = ".jpg";
        }
        String customID = "";
        if (files != null) {
            for (File f : files) {
                String fName = f.getName();
                if (fName.equals(filename + extension)) {
                    customID = System.currentTimeMillis() + "-" + UUID.randomUUID() + "-";
                    break;
                }
            }
        }
        String resultName = customID + filename + extension;
        file.transferTo(new File(uploadDirResource.getURL().getPath() + "/photos/" + resultName));
        photo.setImageName(resultName);
        return photoRepository.save(photo);
    }

    @Transactional
    public void delete(Photo photo) {
        photoRepository.delete(photo);
    }

    public List<Photo> getByProductsContaining(Product product) {
        return photoRepository.getByProductsContaining(product);
    }

    @Transactional
    public void deleteById(int id) {
        Photo photo = photoRepository.findById(id).get();
        try {
            Files.delete(Path.of(uploadDirResource.getPath() + "/static/photos/" + photo.getImageName()));
            System.out.println("File deleted: " + photo.getImageName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        photoRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Photo photo) {
        Photo photoToBeUpdated = photoRepository.findById(id).orElse(new Photo());
        photoToBeUpdated.setImageName(photo.getImageName());
    }
}
