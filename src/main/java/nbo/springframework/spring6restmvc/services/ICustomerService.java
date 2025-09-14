package nbo.springframework.spring6restmvc.services;

import nbo.springframework.spring6restmvc.models.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICustomerService {

    Optional<CustomerDTO> getCustomerById(UUID id); // Optional is used to handle the case where a customer might not be found

    List<CustomerDTO> getAllCustomers();

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    Optional<CustomerDTO> updateCustomerData(UUID idCustomer, CustomerDTO customerDTO);

    Boolean deleteCustomerById(UUID idCustomer);

    void updateCustomerPatchById(UUID idCustomer, CustomerDTO customerDTO);
}
