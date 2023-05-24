package by.artsyom.restapi.controllers;

import by.artsyom.restapi.models.Address;
import by.artsyom.restapi.models.Nomenclature;
import by.artsyom.restapi.models.User;
import by.artsyom.restapi.repositories.AddressRepository;
import by.artsyom.restapi.services.AddressService;
import by.artsyom.restapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;



@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value="/address")
public class AddressController {
    private final AddressService addressService;
    private final UserService userService;
    private final AddressRepository addressRepository;

    @GetMapping(value = "")
    public String product(Principal principal, Model model){
        model.addAttribute("addresses", addressService.listAddress(userService.getUserByPrincipal(principal)));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "address";
    }
    @PostMapping( "/create")
    public String createAddress(@RequestParam String fullAddress, Principal principal)  {
        User user = userService.getUserByPrincipal(principal);
        addressService.save(fullAddress, user);
        return "redirect:/address/";
    }

    @PostMapping( "/delete")
    public String deleteAddress(@RequestParam Long idAddress, Principal principal)  {
        User user = userService.getUserByPrincipal(principal);
        Address address = addressRepository.findAddressByIdAndUser(idAddress, user);
        if (address != null) {
            addressService.delete(address);
        }
        return "redirect:/address/";
    }

    @PostMapping("/setMain")
    public String setMain(@RequestParam Long idAddress, Principal principal){
        User user = userService.getUserByPrincipal(principal);
        Address address = addressRepository.findAddressByIdAndUser(idAddress, user);
        addressService.setMain(address, user);
        return "redirect:/address/";
    }
}
