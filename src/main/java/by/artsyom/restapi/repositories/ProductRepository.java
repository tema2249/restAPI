package by.artsyom.restapi.repositories;

import by.artsyom.restapi.models.Order;
import by.artsyom.restapi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
