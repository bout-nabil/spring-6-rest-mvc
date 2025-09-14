package nbo.springframework.spring6restmvc.services;

import lombok.RequiredArgsConstructor;
import nbo.springframework.spring6restmvc.mappers.CustomerMapper;
import nbo.springframework.spring6restmvc.models.CustomerDTO;
import nbo.springframework.spring6restmvc.repositories.ICustomerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class ICustomerServiceJPAImpl implements ICustomerService {

    private final ICustomerRepository iCustomerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(iCustomerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return iCustomerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        return customerMapper.customerToCustomerDto(iCustomerRepository
                .save(customerMapper.customerDtoToCustomer(customerDTO))); // map customerDTO to Customer entity, save it, then map back to CustomerDTO
    }

    @Override
    public Optional<CustomerDTO> updateCustomerData(UUID idCustomer, CustomerDTO customerDTO) {
        iCustomerRepository.findById(idCustomer).ifPresent(customer -> {
           customer.setNameCustomer(customerDTO.getNameCustomer());
           customer.setPhoneCustomer(customerDTO.getPhoneCustomer());
           customer.setEmailCustomer(customerDTO.getEmailCustomer());
              iCustomerRepository.save(customer);
        });
        return Optional.ofNullable(customerMapper
                .customerToCustomerDto(iCustomerRepository
                        .findById(idCustomer).orElse(null)));
    }

    @Override
    public void deleteCustomerById(UUID idCustomer) {
        iCustomerRepository.deleteById(idCustomer);
    }

    @Override
    public void updateCustomerPatchById(UUID idCustomer, CustomerDTO customerDTO) {

    }
}
