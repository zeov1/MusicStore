package ru.zeovl.musicstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zeovl.musicstore.models.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
}
