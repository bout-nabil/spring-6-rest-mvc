package nbo.springframework.spring6restmvc.services;

import nbo.springframework.spring6restmvc.models.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICustomerService {

    Optional<Customer> getCustomerById(UUID id); // Optional is used to handle the case where a customer might not be found

    List<Customer> getAllCustomers();

    Customer createCustomer(Customer customer);

    void updateCustomerData(UUID idCustomer, Customer customer);

    void deleteCustomerById(UUID idCustomer);

    void updateCustomerPatchById(UUID idCustomer, Customer customer);
}
