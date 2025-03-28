package com.paymentchain.customer.service;

import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomers() {
        log.info("Get customers");
        return this.customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomer(long id) {
        log.info("Get customer with id {}", id);

        return this.customerRepository.findById(id);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        log.info("Creating new customer: {}", customer);
        customer.getProducts().forEach(product -> product.setCustomer(customer));
        return this.customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> updateCustomer(long id, Customer customer) {
        Optional<Customer> customerExist = this.customerRepository.findById(id);
        if (customerExist.isPresent()) {

            log.info("Updating customer with id {}", customer.getId());

            customerExist.get().setCode(customer.getCode());
            customerExist.get().setName(customer.getName());
            customerExist.get().setIban(customer.getIban());
            customerExist.get().setPhone(customer.getPhone());
            customerExist.get().setSurname(customer.getSurname());

            this.customerRepository.save(customerExist.get());

            return customerExist;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> deleteCustomer(long id) {
        Optional<Customer> customerOptional = this.customerRepository.findById(id);

        if (customerOptional.isPresent()) {
            log.info("Deleting customer with id: {}", id);
            log.info("Customer: {}", customerOptional.get());
            this.customerRepository.delete(customerOptional.get());
        }

        return Optional.empty();
    }

}
