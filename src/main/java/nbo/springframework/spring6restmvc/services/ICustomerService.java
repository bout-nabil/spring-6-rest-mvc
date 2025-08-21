package nbo.springframework.spring6restmvc.services;

import nbo.springframework.spring6restmvc.models.Customer;

import java.util.List;
import java.util.UUID;

public interface ICustomerService {
    public Customer getCustomerById(UUID id);
    public List<Customer> getAllCustomers();
}
