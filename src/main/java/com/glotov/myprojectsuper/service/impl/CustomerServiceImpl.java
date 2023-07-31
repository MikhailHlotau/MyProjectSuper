package com.glotov.myprojectsuper.service.impl;

import com.glotov.myprojectsuper.model.Customer;
import com.glotov.myprojectsuper.repository.CustomerRepository;
import com.glotov.myprojectsuper.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;


    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer addCustomer(Customer customer) {
        Customer createdUser = null;
            if (customerRepository.findByLogin(customer.getLogin()) != null) {
                log.info("Пользователь с таким логином уже существует: {}", customer.getLogin());
            } else {
                createdUser  = customerRepository.save(customer);
            }
        return createdUser;
    }


    public Customer editCustomer(Customer customer) {
        /*Optional<Customer> optionalExistingCustomer = Optional.ofNullable(customerRepository.findByLogin(customer.getLogin()));

        Customer existingCustomer = optionalExistingCustomer.orElseThrow(() ->
                new IllegalArgumentException("Клиент с указанным login не найден."));*/

        Customer existingCustomer = customerRepository.findByLogin(customer.getLogin());

        if (existingCustomer == null) {
            return null;
        }

        // Обновляем поля у существующего пользователя
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPassword(customer.getPassword());
        existingCustomer.setRole(customer.getRole());
        existingCustomer.setBanned(customer.isBanned());

        // Сохраняем обновленного пользователя без явного указания id
        return customerRepository.save(existingCustomer);
    }


    public Customer findCustomerById(int customerId) {
        return customerRepository.findById(customerId)
                .orElse(null);
    }

    public void deleteCustomer(int customerId) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Клиент с указанным ID не найден."));

        customerRepository.delete(existingCustomer);
    }
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findByLogin(String username) {
        return customerRepository.findByLogin(username);
    }


}