package com.glotov.myprojectsuper.service;

import com.glotov.myprojectsuper.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAllCustomers();
    Customer findCustomerById(int customerId);
    Customer addCustomer(Customer customer);

    Customer editCustomer(Customer customer);

    void deleteCustomer(int customerId);

    Customer findByLogin(String username);
}

