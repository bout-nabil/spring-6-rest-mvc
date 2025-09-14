package nbo.springframework.spring6restmvc.controllers;

import nbo.springframework.spring6restmvc.entities.Customer;
import nbo.springframework.spring6restmvc.mappers.CustomerMapper;
import nbo.springframework.spring6restmvc.models.CustomerDTO;
import nbo.springframework.spring6restmvc.repositories.ICustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {
    @Autowired
    ICustomerRepository iCustomerRepository;
    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testDeleteNotFoundCustomer(){
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomerById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteCustomerById(){
        Customer customer = iCustomerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getIdCustomer());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);

        assertThat(iCustomerRepository.findById(customer.getIdCustomer()).isEmpty());
    }

    @Test
    void testUpdatedNotFoundCustomer(){
        assertThrows(NotFoundException.class, () -> {
           customerController.updateCustomerData(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Test
    void testUpdatedExistingCustomer(){
        Customer customer = iCustomerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setIdCustomer(null);
        customerDTO.setVersionCustomer(null);
        String newName = "Updated Name Customer";
        customerDTO.setNameCustomer(newName);

        ResponseEntity responseEntity = customerController.updateCustomerData(customer.getIdCustomer(), customerDTO);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);

        Customer updatedCustomer = iCustomerRepository.findById(customer.getIdCustomer()).get();
        assertThat(updatedCustomer.getNameCustomer()).isEqualTo(newName);
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewCustomer(){
        CustomerDTO customerDTO = CustomerDTO.builder().nameCustomer("Test Customer").build();

        ResponseEntity responseEntity = customerController.createCustomer(customerDTO);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/"); // Splitting the Location header to extract the UUID
        UUID savedUUID = UUID.fromString(locationUUID[locationUUID.length -1]); // Extracting the UUID from the Location header

        Customer saveedCustomer = iCustomerRepository.findById(savedUUID).get();
        assertThat(saveedCustomer).isNotNull();
    }


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