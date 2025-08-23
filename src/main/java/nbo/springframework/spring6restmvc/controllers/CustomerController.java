package nbo.springframework.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbo.springframework.spring6restmvc.models.Customer;
import nbo.springframework.spring6restmvc.services.ICustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController     // @RestController is a convenience annotation that combines @Controller and @ResponseBody
@RequestMapping("/api/v1/customers") // Base URL for the Customer API
public class CustomerController {
    private final ICustomerService customerService;

    @RequestMapping(method = RequestMethod.GET) // Maps to this method for GET requests
    public List<Customer> getAllCustomers() {
        log.info("Listing all customers");
        return customerService.getAllCustomers();
    }

    //@GetMapping("/{idCustomer}") // Alternative way to map GET requests with a path variable
    //@ResponseBody // @ResponseBody indicates that the return value should be bound to the web response body
    @RequestMapping(value = "{idCustomer}", method = RequestMethod.GET) // Maps to this method for GET requests with a customer ID
    public Customer getCustomerById(@PathVariable("idCustomer") UUID idCustomer) {
        log.info("Fetching customer with ID: {}", idCustomer);
        return customerService.getCustomerById(idCustomer);
    }

    @PostMapping // @PostMapping is a shortcut for @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) { // ResponseEntity is used to control the HTTP response
        log.info("Creating new customer");
        Customer savedCustomer = customerService.createCustomer(customer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v1/customers/" + savedCustomer.getIdCustomer().toString());
        return new ResponseEntity<>(savedCustomer, httpHeaders, HttpStatus.CREATED);
    }
}
