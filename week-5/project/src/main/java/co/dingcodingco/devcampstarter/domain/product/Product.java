package co.dingcodingco.devcampstarter.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false, unique = true, length = 40)
    private String sku;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductStatus status = ProductStatus.ACTIVE;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private int price;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    protected Product() {
    }

    public Product(String title, String sku, int stock, int price) {
        this.title = title;
        this.sku = sku;
        this.stock = stock;
        this.price = price;
    }

    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
        if (this.stock < quantity) {
            throw new IllegalStateException("not enough stock");
        }
        this.stock -= quantity;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getSku() { return sku; }
    public ProductStatus getStatus() { return status; }
    public int getStock() { return stock; }
    public int getPrice() { return price; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
