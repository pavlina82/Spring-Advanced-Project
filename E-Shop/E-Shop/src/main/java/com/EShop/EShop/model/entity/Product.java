package com.EShop.EShop.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT '0.00'")
    private BigDecimal price;

    @Column(name = "image_url", nullable = false)
    private String image_url;

    @ManyToMany(targetEntity = Category.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(
                    name = "product_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id"
            )
    )
    private List<Category> categories;


    private boolean isDeleted;



    public Product() {
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getImage_url() {
        return image_url;
    }

    public Product setImage_url(String image_url) {
        this.image_url = image_url;
        return this;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Product setCategories(List<Category> categories) {
        this.categories = categories;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

}
