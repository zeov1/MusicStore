package ru.zeovl.musicstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
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
    @JoinTable(
            name = "product_photo_links",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "photo_id", referencedColumnName = "id")
    )
    private List<Photo> photos;

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Product() {
        this.dateAdded = new Date();
    }

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.dateAdded = new Date();
    }

    public Product(String name, String description, BigDecimal price, int amount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.dateAdded = new Date();
    }

    public Product(String name, String description, BigDecimal price, int amount, int unitsSold, boolean isArchived) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.unitsSold = unitsSold;
        this.isArchived = isArchived;
        this.dateAdded = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(int unitsSold) {
        this.unitsSold = unitsSold;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
