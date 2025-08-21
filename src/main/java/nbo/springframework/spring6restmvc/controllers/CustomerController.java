package nbo.springframework.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbo.springframework.spring6restmvc.models.Customer;
import nbo.springframework.spring6restmvc.services.ICustomerService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers") // Base URL for the Customer API
public class CustomerController {
    private final ICustomerService customerService;

    @RequestMapping(method = RequestMethod.GET) // Maps to this method for GET requests
    public List<Customer> getAllCustomers() {
        log.info("Listing all customers");
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = "{idCustomer}", method = RequestMethod.GET) // Maps to this method for GET requests with a customer ID
    public Customer getCustomerById(@PathVariable("idCustomer") UUID idCustomer) {
        log.info("Fetching customer with ID: {}", idCustomer);
        return customerService.getCustomerById(idCustomer);
    }
}
