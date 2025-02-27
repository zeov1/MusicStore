package ru.zeovl.musicstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "photos")
@Data
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 3, max = 80)
    @Column(name = "image_name")
    private String imageName;

    @NotNull
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @ManyToMany
    @JoinTable(name = "product_photo_links", joinColumns = @JoinColumn(name = "photo_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    List<Product> products;

    public Photo() {
        this.createdAt = new Date();
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}

