package nbo.springframework.spring6restmvc.services;

import nbo.springframework.spring6restmvc.models.Customer;

import java.util.List;
import java.util.UUID;

public interface ICustomerService {
    Customer getCustomerById(UUID id);
    List<Customer> getAllCustomers();
    Customer createCustomer(Customer customer);

    void updateCustomerData(UUID idCustomer, Customer customer);
}
