package by.artsyom.restapi.controllers;

import by.artsyom.restapi.models.Cart;
import by.artsyom.restapi.models.Nomenclature;
import by.artsyom.restapi.models.User;
import by.artsyom.restapi.repositories.CartRepository;
import by.artsyom.restapi.services.CartService;
import by.artsyom.restapi.services.NomenclatureService;
import by.artsyom.restapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasAuthority('ROLE_USER')")
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final NomenclatureService nomenclatureServices;
    private final CartRepository cartRepository;

    @GetMapping(value = "/cart")
    public String product(Principal principal, Model model){
        User user = userService.getUserByPrincipal(principal);
        List<Cart> cartList = cartService.listCart(user);
        model.addAttribute("carts", cartList);
        model.addAttribute("price", cartService.priceForOrder(cartList));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "cart";
    }

    @PostMapping( "/cart/add/{idNom}")
    public String create(@PathVariable Long idNom, @RequestParam("quantity") int quantity, Principal principal) throws IOException {
        Nomenclature nomenclature = nomenclatureServices.getNomenclatureById(idNom);
        User user = userService.getUserByPrincipal(principal);
        cartService.addToCart(user, nomenclature, quantity);
        return "redirect:/nomenclatures";
    }

    @PostMapping(value = "/cart/delete/")
    public String deleteNomenclature(@RequestParam("idCart") int id, Principal principal){
        Long ID = (long) id;
        User user = userService.getUserByPrincipal(principal);
        Cart cart = cartRepository.getById(ID);
        if (cartService.deleteToCart(cart, user)) {
            return "redirect:/cart";
        }else {
            return "redirect:/login";
        }

    }
}
