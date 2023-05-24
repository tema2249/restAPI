package by.artsyom.restapi.services;

import by.artsyom.restapi.mail.EmailDetails;
import by.artsyom.restapi.mail.EmailService;
import by.artsyom.restapi.models.User;
import by.artsyom.restapi.models.enums.Role;
import by.artsyom.restapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    public boolean CreateUser(User user){
        String login = user.getLogin();
        if (userRepository.findByLogin(login) != null) return false;
        user.setActive(true);
        EmailDetails details = new EmailDetails(user.getEmail(),"Добро пожаловать на сайт. Ваш логин:" + user.getLogin() +". Ваш пароль:"+user.getPassword(), "Регистрация в интернет-магазине", null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        //user.getRoles().add(Role.ROLE_ADMIN);
        log.info("Saved user{}",login);
        userRepository.save(user);
        if (user.getEmail()!=null) {
            emailService.sendSimpleMail(details);
        }
        return true;
    }
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByLogin(principal.getName());
    }
    public List<User> list(){
        return userRepository.findAll();
    }

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user!=null){
            if (user.isActive()){
            user.setActive(false);
                log.info("ban user {}",user.getLogin());
            } else {
                user.setActive(true);
                log.info("Unban user {}",user.getLogin());
            }

        }
        userRepository.save(user);

    }

    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()){
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
            userRepository.save(user);
        }
    }

    public boolean recoverPassword(User user, String newPassword){
        EmailDetails details = new EmailDetails(user.getEmail(),user.getLogin() +", Ваш новый пароль на сайте:"+newPassword, "Ваш пароль был изменён", null);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        if (user.getEmail() != null) {
            emailService.sendSimpleMail(details);
        }
        return true;

    }
}
