package nbo.springframework.spring6restmvc.repositories;

import nbo.springframework.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer(){
        Customer customer = customerRepository.save(Customer.builder()
                .nameCustomer("Test Customer")
                .build());
        assertNotNull(customer);
        assertNotNull(customer.getIdCustomer());
    }
}