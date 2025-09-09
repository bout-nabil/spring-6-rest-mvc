package nbo.springframework.spring6restmvc.controllers;

import lombok.RequiredArgsConstructor;
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
//@AllArgsConstructor
@RequiredArgsConstructor
@RestController     // @RestController is a convenience annotation that combines @Controller and @ResponseBody
//@RequestMapping("/api/v1/customers") // Base URL for the Customer API
public class CustomerController {
    public static final String CUSTOMER_PATH = "/api/v1/customers";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{idCustomer}";
    private final ICustomerService customerService;

    @GetMapping(value = CUSTOMER_PATH) // Maps to this method for GET requests
    public List<Customer> getAllCustomers() {
        log.info("Listing all customers");
        return customerService.getAllCustomers();
    }

    //@GetMapping("/{idCustomer}") // Alternative way to map GET requests with a path variable
    //@ResponseBody // @ResponseBody indicates that the return value should be bound to the web response body
    @GetMapping(value = CUSTOMER_PATH_ID) // Maps to this method for GET requests with a customer ID
    public Customer getCustomerById(@PathVariable("idCustomer") UUID idCustomer) {
        log.info("Fetching customer with ID: {}", idCustomer);
        return customerService.getCustomerById(idCustomer);
    }

    @PostMapping (CUSTOMER_PATH)// @PostMapping is a shortcut for @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) { // ResponseEntity is used to control the HTTP response
        log.info("Creating new customer");
        Customer savedCustomer = customerService.createCustomer(customer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v1/customers/" + savedCustomer.getIdCustomer().toString());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID) // @PutMapping is a shortcut for @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateCustomerData(@PathVariable("idCustomer") UUID idCustomer,@RequestBody Customer customer){
        customerService.updateCustomerData(idCustomer,customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID) // @DeleteMapping is a shortcut for @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("idCustomer") UUID idCustomer) {
        customerService.deleteCustomerById(idCustomer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID) // @PatchMapping is a shortcut for @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateCustomerPatchById(@PathVariable("idCustomer") UUID idCustomer, @RequestBody Customer customer) {
        customerService.updateCustomerPatchById(idCustomer, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
