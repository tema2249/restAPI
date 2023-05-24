package by.artsyom.restapi.controllers;

import by.artsyom.restapi.models.Nomenclature;
import by.artsyom.restapi.models.Order;
import by.artsyom.restapi.models.User;
import by.artsyom.restapi.repositories.OrderRepository;
import by.artsyom.restapi.services.AddressService;
import by.artsyom.restapi.services.OrderService;
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

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value="/order")
public class OrderController {
    private final UserService userService;
    private final AddressService addressService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @GetMapping("/create")
    public String product(Principal principal, Model model){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("addresses", addressService.listAddress(userService.getUserByPrincipal(principal)));
        return "order-client";
    }

    @PostMapping( "/create")
    public String createOrder(@RequestParam(name = "fullAddress") String fullAddress, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        orderService.create(user, fullAddress);
        return "redirect:/nomenclatures";
    }
    @PreAuthorize("hasAuthority('ROLE_MANEGER')")
    @PostMapping( "/accept")
    public String acceptOrder(@RequestParam(name = "id") Long id, Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Order order = orderRepository.findOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        orderService.acceptOrder(order);
        return "redirect:";
    }
    @PreAuthorize("hasAuthority('ROLE_MANEGER')")
    @PostMapping( "/sent")
    public String sentOrder(@RequestParam(name = "id") Long id, Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Order order = orderRepository.findOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        orderService.sentOrder(order);
        return "redirect:";
    }

    @PreAuthorize("hasAuthority('ROLE_MANEGER')")
    @PostMapping( "/cancelled")
    public String cancelledOrder(@RequestParam(name = "id") Long id, Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Order order = orderRepository.findOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        orderService.cancelledOrder(order);
        return "redirect:";
    }

    @GetMapping("")
    public String listOrder(Principal principal, Model model){
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("orders", orderService.listOrders(user));
        return "list-order";
    }

    @GetMapping(value = "/{id}")
    public String orderInfo(@PathVariable Long id, Model model, Principal principal){
        Order order = orderService.findOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "order-info";
    }
}
