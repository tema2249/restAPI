package by.artsyom.restapi.controllers;

import by.artsyom.restapi.models.User;
import by.artsyom.restapi.models.enums.Role;
import by.artsyom.restapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping(value="/admin")
public class AdminController {
    private final UserService userService;
    @GetMapping("")
    public String admin(Model model, Principal principal){
        model.addAttribute("users", userService.list());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "admin";
    }

    @PostMapping("/user/ban/{id}")
    public String userBan(@PathVariable("id") Long id){
        userService.banUser(id);
        return "redirect:/admin";
    }
    @GetMapping("/user/edit/{user}")
    public String userEditRole(@PathVariable("user") User user, Model model, Principal principal){
        model.addAttribute("currentUser", user);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("roles", Role.values());
        return "user-edit";

    }

    @PostMapping("/user/edit/")
    public String userEditRole(@RequestParam("userId") User user, @RequestParam Map<String, String> form){
        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }

    @PostMapping("/user/edit/password")
    public String userEditPassword(@RequestParam("userId") User user, String password){
        userService.recoverPassword(user, password);
        return "redirect:/admin";
    }

    @GetMapping("/user/edit/password/{user}")
    public String userEditPassword(@PathVariable("user") User user, Model model, Principal principal){
        model.addAttribute("currentUser", user);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "user-editPassword";

    }

}
