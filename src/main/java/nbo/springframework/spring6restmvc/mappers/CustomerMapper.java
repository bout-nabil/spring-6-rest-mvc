package nbo.springframework.spring6restmvc.mappers;

import nbo.springframework.spring6restmvc.entities.Customer;
import nbo.springframework.spring6restmvc.models.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO customerDto);
    CustomerDTO customerToCustomerDto(Customer customer);
}
