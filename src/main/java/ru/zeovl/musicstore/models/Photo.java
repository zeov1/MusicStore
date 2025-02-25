package ru.zeovl.musicstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "photos")
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
    @JoinTable(
            name = "product_photo_links",
            joinColumns = @JoinColumn(name = "photo_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    List<Product> products;

    public Photo(String imageName) {
        this.imageName = imageName;
        this.createdAt = new Date();
    }

    public Photo() {
        this.createdAt = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", imageName='" + imageName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
