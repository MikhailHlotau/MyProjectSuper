package com.glotov.myprojectsuper.controller;


import com.glotov.myprojectsuper.model.Customer;
import com.glotov.myprojectsuper.model.MenuItem;
import com.glotov.myprojectsuper.model.Order;
import com.glotov.myprojectsuper.service.CustomerService;
import com.glotov.myprojectsuper.service.MenuService;
import com.glotov.myprojectsuper.service.OrderService;
import com.glotov.myprojectsuper.util.CustomerValidator;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
@Slf4j
@Validated
public class CustomerController {
    private final MenuService menuService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final CustomerValidator customerValidator;


    @Autowired
    public CustomerController(MenuService menuService, CustomerService customerService, OrderService orderService, CustomerValidator customerValidator) {
        this.menuService = menuService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.customerValidator = customerValidator;
    }

    @GetMapping("/goToRegistrationPage")
    public String showRegistrationPage(Model model, HttpSession session) {
        model.addAttribute("customer", new Customer());
        session.removeAttribute("registrationInvalid");
        return "registration";
    }


    @PostMapping("/save")
    public String registerCustomer(@ModelAttribute("customer") Customer customer, BindingResult result, HttpSession session) {
        customerValidator.validate(customer, result);

        if (result.hasErrors()) {
            return "registration";
        }

        Customer customerAdded = customerService.addCustomer(customer);
        session.setAttribute("user", customerAdded);

        if (customerAdded != null) {
            log.info("Добавлен новый клиент: {}", customerAdded);
            session.setAttribute("registrationSuccess", true);
            return "index";
        }

        log.info("Добавить клиента не удалось: {}", customer);
        session.setAttribute("registrationInvalid", true);
        return "registration";
    }



    @GetMapping("/menu")
    public List<MenuItem> findAllMenuItems() {
        return menuService.findAllMenuItems();
    }

    @GetMapping("/order/history")
    public List<Order> findCustomerOrderHistory() {
        // Здесь нужно получить текущего клиента (например, из контекста аутентификации)
        // и затем передать его ID в метод сервиса для получения истории заказов
        int customerId = findCurrentCustomerId(); // Замените на код получения ID текущего клиента
        return orderService.findOrdersByCustomerId(customerId);
    }

    @PostMapping("/order/add")
    public Order addOrder(@RequestBody Order order) {
        // Здесь нужно получить текущего клиента (например, из контекста аутентификации)
        // и затем установить его в поле customer перед сохранением заказа
        int customerId = findCurrentCustomerId(); // Замените на код получения ID текущего клиента
        order.setCustomer(customerService.findCustomerById(customerId));
        return orderService.addOrder(order);
    }

    @PutMapping("/order/edit/{orderId}")
    public Order editOrder(@PathVariable int orderId, @RequestBody Order order) {
        // Здесь нужно получить текущего клиента (например, из контекста аутентификации)
        // и затем проверить, что заказ с указанным ID принадлежит этому клиенту
        int customerId = findCurrentCustomerId(); // Замените на код получения ID текущего клиента
        Order existingOrder = orderService.getOrderById(orderId);
        if (existingOrder == null || existingOrder.getCustomer().getId() != customerId) {
            throw new IllegalArgumentException("Заказ с указанным ID не найден или не принадлежит текущему клиенту.");
        }

        order.setId(orderId);
        return orderService.editOrder(orderId, order);
    }

    @PostMapping("/order/pay/{orderId}")
    public void payOrder(@PathVariable int orderId) {
        // Здесь нужно получить текущего клиента (например, из контекста аутентификации)
        // и затем проверить, что заказ с указанным ID принадлежит этому клиенту
        int customerId = findCurrentCustomerId(); // Замените на код получения ID текущего клиента
        Order existingOrder = orderService.getOrderById(orderId);
        if (existingOrder == null || existingOrder.getCustomer().getId() != customerId) {
            throw new IllegalArgumentException("Заказ с указанным ID не найден или не принадлежит текущему клиенту.");
        }

        orderService.payOrder(orderId);
    }

    // Вспомогательный метод для получения ID текущего клиента (заглушка, замените на реальный код)
    private int findCurrentCustomerId() {
        // Здесь должен быть код для получения ID текущего клиента, например, из контекста аутентификации
        // Заглушка: возвращаем ID=1
        return 1;
    }
}