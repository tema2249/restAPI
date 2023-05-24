package by.artsyom.restapi.controllers;

import by.artsyom.restapi.models.User;
import by.artsyom.restapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Principal principal, Model model){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model){
        if(!userService.CreateUser(user)){
            model.addAttribute("errorMessage", "Пользователь с таким логином существует");
            return "registration";
        }
        userService.CreateUser(user);
        return "redirect:/login";

    }
    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User currentUser, Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        if (user.isManeger() || user == currentUser){
        model.addAttribute("currentUser", currentUser);
        }else {
            return "redirect:";
        }
        model.addAttribute("user", user);
        return "user-info";

    }
}
