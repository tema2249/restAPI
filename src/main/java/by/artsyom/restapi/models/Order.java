package by.artsyom.restapi.models;

import by.artsyom.restapi.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private User user;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "order")
    private List<Product> products = new ArrayList<>();
    private String fullAddress;
    private String name;
    private int price;
    private Status status;
    private LocalDateTime dateOfCreated;


    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }

    public void addProductToOrder(Product product){
        products.add(product);
    }
}
