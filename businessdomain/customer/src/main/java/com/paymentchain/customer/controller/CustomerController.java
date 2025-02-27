package com.paymentchain.customer.controller;

import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.service.CustomerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public ResponseEntity<List<Customer>> getCustomers() {
        try {
            List<Customer> customers = customerService.getCustomers();
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        log.info("Get customer with id {}", id);
        try {
            Optional<Customer> existingCustomer = customerService.getCustomer(id);
            return existingCustomer
                    .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        log.info("Create customer {}", customer.getId());
        try {
            Customer newCustomer = customerService.createCustomer(customer);
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        log.info("Update customer with id {}", customer.getId());
        try {
            Optional<Customer> customerOptional = customerService.updateCustomer(customer);
            return customerOptional
                    .map(updatedCustomer -> new ResponseEntity<>(updatedCustomer, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        log.info("Delete customer with id {}", id);
        try {
            Optional<Customer> customerOptional = customerService.deleteCustomer(id);
            return customerOptional
                    .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
