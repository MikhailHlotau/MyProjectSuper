package com.glotov.myprojectsuper.controller;

import com.glotov.myprojectsuper.model.Customer;
import com.glotov.myprojectsuper.model.Role;
import com.glotov.myprojectsuper.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/authentication")
public class AuthenticationController {
    private final CustomerService customerService;

    @Autowired
    public AuthenticationController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Customer customer = customerService.findByLogin(username);
        if (customer != null && customer.getPassword().equals(password)) {
            session.setAttribute("user", customer);
            if (customer.getRole().equals(Role.ADMIN)) {
                log.info("Авторизовался ADMIN: {}", customer);
                return "admin_menu";
            } else {
                session.setAttribute("user", customer);
                log.info("Авторизовался CUSTOMER: {}", customer);
                return "customer_menu";
            }
        } else {
            session.removeAttribute("user");
            session.setAttribute("error", "Invalid username or password");
            return "index";
        }
    }

    @GetMapping("/goToIndexPage")
    public String showIndexPage(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("user");
        session.removeAttribute("customer");
        session.removeAttribute("error");
        session.removeAttribute("registrationSuccess");
        log.info("Пользователь вышел из аккаунта: {}", customer);
        return "index";
    }
}
