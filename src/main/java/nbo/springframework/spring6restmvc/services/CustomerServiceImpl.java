package nbo.springframework.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import nbo.springframework.spring6restmvc.models.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {
    private Map<UUID, Customer> customerMap = new HashMap<UUID, Customer>();

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .idCustomer(UUID.randomUUID())
                .nameCustomer("Ali")
                .emailCustomer("ali.ali@example.com")
                .phoneCustomer("0612345678")
                .versionCustomer("1")
                .createdAtCustomer(LocalDateTime.now())
                .updatedAtCustomer(LocalDateTime.now())
                .build();
        Customer customer2 = Customer.builder()
                .idCustomer(UUID.randomUUID())
                .nameCustomer("Nabil")
                .emailCustomer("nabil.nabil@example.com")
                .phoneCustomer("0623456789")
                .versionCustomer("1")
                .createdAtCustomer(LocalDateTime.now())
                .updatedAtCustomer(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .idCustomer(UUID.randomUUID())
                .nameCustomer("Tawfiq")
                .emailCustomer("tawfiq.tawfiq@example.com")
                .phoneCustomer("0634567890")
                .versionCustomer("1")
                .createdAtCustomer(LocalDateTime.now())
                .updatedAtCustomer(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getIdCustomer(), customer1);
        customerMap.put(customer2.getIdCustomer(), customer2);
        customerMap.put(customer3.getIdCustomer(), customer3);
    }
    @Override
    public Customer getCustomerById(UUID idCustomer) {
        log.info("Fetching customer with ID");
        return customerMap.get(idCustomer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer createdCustomer = Customer.builder()
                .idCustomer(UUID.randomUUID())
                .nameCustomer(customer.getNameCustomer())
                .emailCustomer(customer.getEmailCustomer())
                .phoneCustomer(customer.getPhoneCustomer())
                .versionCustomer("1")
                .createdAtCustomer(LocalDateTime.now())
                .updatedAtCustomer(LocalDateTime.now())
                .build();

        customerMap.put(createdCustomer.getIdCustomer(), createdCustomer);
        return createdCustomer;
    }
}
