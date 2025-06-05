package com.paymentchain.customer.service;

import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.entities.CustomerProduct;
import com.paymentchain.customer.exception.BusinessRuleException;
import com.paymentchain.customer.repository.CustomerRepository;
import com.paymentchain.customer.rest.ProductClient;
import com.paymentchain.customer.rest.TransactionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductClient productClient;
    private final TransactionClient transactionClient;

    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            ProductClient productClient,
            TransactionClient transactionClient) {
        this.customerRepository = customerRepository;
        this.productClient = productClient;
        this.transactionClient = transactionClient;
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
    public Customer createCustomer(Customer customer) throws BusinessRuleException, UnknownHostException {
        log.info("Creating new customer: {}", customer);
//        customer.getProducts().forEach(product -> product.setCustomer(customer));
        if (customer.getProducts() != null) {
            for (CustomerProduct product : customer.getProducts()) {
                String productName = this.productClient.getProductName(Long.parseLong(product.getProductName()));
                if (productName.isBlank()) {
                    throw new BusinessRuleException("1025",
                            "Validation Error, Prodcut with id " + product.getId() + "doesn't exist",
                            HttpStatus.PRECONDITION_FAILED);
                } else {
                    product.setCustomer(customer);
                }
            }
        }
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

    public Optional<Customer> getCustomerByCode(String code) {
        log.info("Get customer with code {}", code);

        Customer customer = this.customerRepository.findByCode(code);
        List<CustomerProduct> products = customer.getProducts();

        products.forEach(product -> {
            String productName = null;
            try {
                productName = this.productClient.getProductName(product.getId());
            } catch (UnknownHostException e) {
                log.error("Unknown Host Exception", e);
            }
            product.setProductName(productName);
        });

        // Find all transactions that belong this account number
        List<?> transactions = this.transactionClient.getTransactions(customer.getIban());
        customer.setTransactions(transactions);

        return Optional.of(customer);

    }

}
