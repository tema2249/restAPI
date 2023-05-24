package by.artsyom.restapi.services;

import by.artsyom.restapi.models.*;
import by.artsyom.restapi.models.enums.Status;
import by.artsyom.restapi.repositories.CartRepository;
import by.artsyom.restapi.repositories.OrderRepository;
import by.artsyom.restapi.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService{
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    public void create(User user, String fullAddress) {
        Order order = new Order();
        order.setUser(user);
        order.setName(user.getName());
        order.setFullAddress(fullAddress);
        //Order orderSave = orderRepository.save(order);
        List<Cart> carts = cartRepository.findCartByUser(user);
        int price = 0;
        for (Cart cart: carts){
            Product product = new Product(cart.getNomenclature());
            price += cart.getNomenclature().getPrice()*cart.getQuantity();
            product.setQuantity(cart.getQuantity());
            product.setOrder(order);
            product.setUser(user);
            //productRepository.save(product);
            order.addProductToOrder(product);
            cartService.deleteToCart(cart, user);
        }
        order.setPrice(price);
        order.setStatus(Status.created);
        orderRepository.save(order);
        //cartService.clearCarts(user);

    }

    public List<Order> listOrders(User user) {
        List<Order> orders;
        if (user.isManeger()){
            orders = orderRepository.findOrdersBy();
        } else {
            orders = orderRepository.findOrdersByUser(user);
        }
        Collections.sort(orders, new PersonComparator());
        return orders;
    }
    public Order findOrderById(Long id){
        return orderRepository.findOrderById(id);
    }

    public void acceptOrder(Order order) {
        order.setStatus(Status.accepted);
        orderRepository.save(order);
    }

    public void sentOrder(Order order) {
        order.setStatus(Status.sent);
        orderRepository.save(order);
    }

    public void cancelledOrder(Order order) {
        order.setStatus(Status.Cancelled);
        orderRepository.save(order);
    }
}

class PersonComparator implements java.util.Comparator<Order> {
    @Override
    public int compare(Order a, Order b) {
        return (int) (b.getId() - a.getId());
    }
}