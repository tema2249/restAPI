package by.artsyom.restapi.repositories;


import by.artsyom.restapi.models.Order;
import by.artsyom.restapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findOrdersByUser(User user);

    public List<Order> findOrdersBy();

    public Order findOrderById(Long id);


}
