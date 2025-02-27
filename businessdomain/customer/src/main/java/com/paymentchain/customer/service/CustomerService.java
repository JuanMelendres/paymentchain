package com.paymentchain.customer.service;

import com.paymentchain.customer.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public List<Customer> getCustomers();
    public Optional<Customer> getCustomer(long id);
    public Customer createCustomer(Customer customer);
    public Optional<Customer> updateCustomer(long id, Customer customer);
    public Optional<Customer> deleteCustomer(long id);
}
