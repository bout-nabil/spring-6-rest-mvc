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
        return null;
    }

    @Override
    public void updateCustomerData(UUID idCustomer, CustomerDTO customerDTO) {

    }

    @Override
    public void deleteCustomerById(UUID idCustomer) {

    }

    @Override
    public void updateCustomerPatchById(UUID idCustomer, CustomerDTO customerDTO) {

    }
}
