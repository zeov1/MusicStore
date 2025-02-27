package ru.zeovl.musicstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 2, max = 150)
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(max = 2000)
    @Column(name = "description")
    private String description;

    @NotNull
    @Min(value = 0)
    @Max(value = 10_000_000)
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull
    @Min(value = 0)
    @Max(value = 500_000)
    @Column(name = "amount")
    private int amount;

    @NotNull
    @Min(value = 0)
    @Max(value = 10_000_000)
    @Column(name = "units_sold")
    private int unitsSold;

    @NotNull
    @Column(name = "date_added")
    @CreationTimestamp
    private Date dateAdded;

    @NotNull
    @Column(name = "is_archived")
    private boolean isArchived;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToMany
    @JoinTable(name = "product_photo_links", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "photo_id", referencedColumnName = "id"))
    private List<Photo> photos;

    public Product() {
        this.dateAdded = new Date();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturer=" + manufacturer +
                '}';
    }
}
