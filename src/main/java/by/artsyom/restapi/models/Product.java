package by.artsyom.restapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Nomenclature nomenclature;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn
    private Order order;
    private String title;
    private int price;
    private int quantity;
    private LocalDateTime dateOfCreated;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }

    public Product(Nomenclature nomenclature) {
        title = nomenclature.getTitle();
        price = nomenclature.getPrice();
    }
}
