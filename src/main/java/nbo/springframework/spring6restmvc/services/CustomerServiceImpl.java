package nbo.springframework.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import nbo.springframework.spring6restmvc.models.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {
    private final Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .idCustomer(UUID.randomUUID())
                .nameCustomer("Ali")
                .emailCustomer("ali.ali@example.com")
                .phoneCustomer("0612345678")
                .versionCustomer(1)
                .createdAtCustomer(LocalDateTime.now())
                .updatedAtCustomer(LocalDateTime.now())
                .build();
        CustomerDTO customerDTO2 = CustomerDTO.builder()
                .idCustomer(UUID.randomUUID())
                .nameCustomer("Nabil")
                .emailCustomer("nabil.nabil@example.com")
                .phoneCustomer("0623456789")
                .versionCustomer(1)
                .createdAtCustomer(LocalDateTime.now())
                .updatedAtCustomer(LocalDateTime.now())
                .build();

        CustomerDTO customerDTO3 = CustomerDTO.builder()
                .idCustomer(UUID.randomUUID())
                .nameCustomer("Tawfiq")
                .emailCustomer("tawfiq.tawfiq@example.com")
                .phoneCustomer("0634567890")
                .versionCustomer(1)
                .createdAtCustomer(LocalDateTime.now())
                .updatedAtCustomer(LocalDateTime.now())
                .build();

        customerMap.put(customerDTO1.getIdCustomer(), customerDTO1);
        customerMap.put(customerDTO2.getIdCustomer(), customerDTO2);
        customerMap.put(customerDTO3.getIdCustomer(), customerDTO3);
    }
    @Override
    public Optional<CustomerDTO> getCustomerById(UUID idCustomer) {
        log.info("Fetching customer with ID");
        return Optional.of(customerMap.get(idCustomer));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        CustomerDTO createdCustomerDTO = CustomerDTO.builder()
                .idCustomer(UUID.randomUUID())
                .nameCustomer(customerDTO.getNameCustomer())
                .emailCustomer(customerDTO.getEmailCustomer())
                .phoneCustomer(customerDTO.getPhoneCustomer())
                .versionCustomer(1)
                .createdAtCustomer(LocalDateTime.now())
                .updatedAtCustomer(LocalDateTime.now())
                .build();

        customerMap.put(createdCustomerDTO.getIdCustomer(), createdCustomerDTO);
        return createdCustomerDTO;
    }

    @Override
    public void updateCustomerData(UUID idCustomer, CustomerDTO customerDTO) {
        log.info("Updating customerDTO with ID {}", idCustomer);
        CustomerDTO existingCustomerDTO = customerMap.get(idCustomer);
        if (existingCustomerDTO != null) {
            existingCustomerDTO.setNameCustomer(customerDTO.getNameCustomer());
            existingCustomerDTO.setEmailCustomer(customerDTO.getEmailCustomer());
            existingCustomerDTO.setPhoneCustomer(customerDTO.getPhoneCustomer());
            existingCustomerDTO.setVersionCustomer(existingCustomerDTO.getVersionCustomer());
            existingCustomerDTO.setCreatedAtCustomer(existingCustomerDTO.getCreatedAtCustomer());
            existingCustomerDTO.setUpdatedAtCustomer(existingCustomerDTO.getUpdatedAtCustomer());
            customerMap.put(idCustomer, existingCustomerDTO);
        } else {
            log.warn("CustomerDTO with ID {} not found. Update operation skipped.", idCustomer);
        }
    }

    @Override
    public void deleteCustomerById(UUID idCustomer) {
        CustomerDTO removedCustomerDTO = customerMap.remove(idCustomer);
        if(removedCustomerDTO != null) {
            log.info("Deleted customer with ID: {}", idCustomer);
        } else {
            log.warn("CustomerDTO with ID {} not found. Delete operation skipped.", idCustomer);
        }
    }

    @Override
    public void updateCustomerPatchById(UUID idCustomer, CustomerDTO customerDTO) {
        CustomerDTO existingCustomerDTO = customerMap.get(idCustomer);
        if (existingCustomerDTO != null) {
            if (customerDTO.getVersionCustomer() != null){
                existingCustomerDTO.setVersionCustomer(customerDTO.getVersionCustomer());
            }
            if (customerDTO.getNameCustomer() != null) {
                existingCustomerDTO.setNameCustomer(customerDTO.getNameCustomer());
            }
            if (customerDTO.getEmailCustomer() != null) {
                existingCustomerDTO.setEmailCustomer(customerDTO.getEmailCustomer());
            }
            if (customerDTO.getPhoneCustomer() != null) {
                existingCustomerDTO.setPhoneCustomer(customerDTO.getPhoneCustomer());
            }
            existingCustomerDTO.setUpdatedAtCustomer(LocalDateTime.now());
            customerMap.put(idCustomer, existingCustomerDTO);
        } else {
            log.warn("CustomerDTO with ID {} not found. Patch operation skipped.", idCustomer);
        }
    }
}
