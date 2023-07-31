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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {
    private final CustomerService customerService;
    private final MenuService menuService;
    private final OrderService orderService;

    private final CustomerValidator customerValidator;

    @Autowired
    public AdminController(CustomerService customerService, MenuService menuService, OrderService orderService, CustomerValidator customerValidator) {
        this.customerService = customerService;
        this.menuService = menuService;
        this.orderService = orderService;
        this.customerValidator = customerValidator;
    }

    // Методы для работы с клиентами

    @GetMapping("/customer/list")
    public String findAllCustomers(Model model) {
        List<Customer> customerList = customerService.findAllCustomers();
        model.addAttribute("customerList", customerList);
        log.info("Получен список всех пользователей");
        return "customer_list";
    }

    @GetMapping("/goToAdminMenuPage")
    public String showAdminMenuPage() {
        return "admin_menu";
    }
    @GetMapping("/goToAdminCustomersDeletePage")
    public String showAdminCustomersDeletePage() {
        return "admin_customers_delete";
    }

    @GetMapping("/goToAdminCustomersPage")
    public String showAdminCustomersPage() {
        return "admin_customers";
    }
    @GetMapping("/goToAdminCustomersEditPage")
    public String showAdminCustomersEditPage(Model model, HttpSession session) {
        model.addAttribute("customer", new Customer());
        session.removeAttribute("registrationInvalid");
        return "admin_customers_edit";
    }

/*
    @PostMapping("/customer/edit")
    public Customer editCustomer(@RequestBody Customer customer) {
        return customerService.editCustomer(customer);
    }
*/

    @PostMapping("/customer/edit")
    public String editCustomer(@ModelAttribute("customer") Customer customer, BindingResult result, HttpSession session) {
        customerValidator.validate(customer, result);

        if (result.hasErrors()) {
            return "admin_customers_edit";
        }

        Customer customerEdited = customerService.editCustomer(customer);
        session.setAttribute("user", customerEdited);

        if (customerEdited != null) {
            log.info("Данные пользователя отредактированы: {}", customerEdited);
            session.setAttribute("registrationSuccess", true);
            return "redirect:/admin/goToAdminCustomersPage";
        }

        log.info("Данные пользователя отредактировать не удалось: {}", customer);
        session.setAttribute("registrationInvalid", true);
        return "admin_customers_edit";
    }


    @PostMapping("customer/delete")
    public String deleteCustomer(@RequestParam int customerId) {
        customerService.deleteCustomer(customerId);
        log.info("Удалён пользователь с id: {}", customerId);
        return "redirect:/admin/goToAdminCustomersPage";
    }

    // Методы для работы с меню

    @GetMapping("/menu/list")
    public List<MenuItem> findAllMenuItems() {
        return menuService.findAllMenuItems();
    }

    @PostMapping("/menu/add")
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem) {
        return menuService.addMenuItem(menuItem);
    }

    @PutMapping("/menu/edit/{menuItemId}")
    public MenuItem editMenuItem(@PathVariable int menuItemId, @RequestBody MenuItem menuItem) {
        return menuService.editMenuItem(menuItemId, menuItem);
    }

    @DeleteMapping("/menu/delete/{menuItemId}")
    public void deleteMenuItem(@PathVariable int menuItemId) {
        menuService.deleteMenuItem(menuItemId);
    }

    // Методы для работы с заказами

    @GetMapping("/order/list")
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    @GetMapping("/order/list/{customerId}")
    public List<Order> getOrdersByCustomerId(@PathVariable int customerId) {
        return orderService.findOrdersByCustomerId(customerId);
    }

    @PostMapping("/order/add")
    public Order addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @PutMapping("/order/edit/{orderId}")
    public Order editOrder(@PathVariable int orderId, @RequestBody Order order) {
        return orderService.editOrder(orderId, order);
    }

    @DeleteMapping("/order/delete/{orderId}")
    public void deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
    }

    @PostMapping("/order/pay/{orderId}")
    public void payOrder(@PathVariable int orderId) {
        orderService.payOrder(orderId);
    }
}
