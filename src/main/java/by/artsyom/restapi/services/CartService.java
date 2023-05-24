package by.artsyom.restapi.services;

import by.artsyom.restapi.models.Cart;
import by.artsyom.restapi.models.Nomenclature;
import by.artsyom.restapi.models.User;
import by.artsyom.restapi.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private List<Cart> cart = new ArrayList<>();

    public List<Cart> listCart(User user){
        return cartRepository.findCartByUser(user);
    }

    public boolean addToCart(User user, Nomenclature nomenclature, int quantity){
        Cart current = cartRepository.findCartByNomenclatureAndUser(nomenclature, user);
        Cart cart;
        if (current != null){
            cart = current;
            cart.setQuantity(quantity + current.getQuantity());
        } else {
            cart = new Cart();
            cart.setQuantity(quantity);
        }
        cart.setNomenclature(nomenclature);
        cart.setUser(user);
        cartRepository.save(cart);
        return true;
    }

    public boolean deleteToCart(Cart cart, User user) {
        if (cart.getUser() == user) {
            cartRepository.delete(cart);
            return true;
        }
        return false;

    }

    public String priceForOrder(List<Cart> cartList) {
        double priceOrder = 0.00F;
        for (Cart cart : cartList) {
            priceOrder = priceOrder + (double ) cart.getNomenclature().getPrice() * cart.getQuantity();
        }
        return String.format("%.2f",priceOrder);
    }

}
