package nbo.springframework.spring6restmvc.controllers;

import nbo.springframework.spring6restmvc.models.CustomerDTO;
import nbo.springframework.spring6restmvc.repositories.ICustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {
    @Autowired
    ICustomerRepository iCustomerRepository;
    @Autowired
    CustomerController customerController;

    @Test
    void testCustomerNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(java.util.UUID.randomUUID());
        });
    }

    @Test
    void testGetCustomerById(){
        var customer = iCustomerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getIdCustomer());
        assertNotNull(customerDTO);
        assertEquals(customer.getIdCustomer(), customerDTO.getIdCustomer());
    }

    @Test
    void listCustomersTest(){
        List<CustomerDTO> customerDTOList = customerController.getAllCustomers();
        assertNotNull(customerDTOList);
        assertTrue(customerDTOList.size() > 0);
    }

    @Rollback
    @Transactional
    @Test
    void listEmptyCustomersTest(){
        iCustomerRepository.deleteAll();
        List<CustomerDTO> customerDTOList = customerController.getAllCustomers();
        assertThat(customerDTOList.size()).isEqualTo(0);
    }
}