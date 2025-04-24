package ru.zeovl.musicstore;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

@SpringBootApplication
public class MusicStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicStoreApplication.class, args);
    }

    @Bean
    FileSystemResource uploadDirResource() {
        return new FileSystemResource("upload");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
