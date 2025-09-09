package nbo.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nbo.springframework.spring6restmvc.models.CustomerDTO;
import nbo.springframework.spring6restmvc.services.CustomerServiceImpl;
import nbo.springframework.spring6restmvc.services.ICustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ICustomerService iCustomerService;

    CustomerServiceImpl customerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> argumentCaptor;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.getAllCustomers().get(0);

        // Create a map to hold the fields to be patched
        String customerName = "Updated Name";
        String customerPhone = "123-456-7890";

        CustomerDTO customerDTOPatch = CustomerDTO.builder()
                .nameCustomer(customerName)
                .phoneCustomer(customerPhone)
                .build();

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, customerDTO.getIdCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTOPatch)))
                .andExpect(status().isNoContent());

        verify(iCustomerService).updateCustomerPatchById(uuidArgumentCaptor.capture(), argumentCaptor.capture());
        assert(customerDTO.getIdCustomer().equals(uuidArgumentCaptor.getValue()));
        assert(customerName.equals(argumentCaptor.getValue().getNameCustomer()));
        assert(customerPhone.equals(argumentCaptor.getValue().getPhoneCustomer()));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.getAllCustomers().get(0);

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customerDTO.getIdCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isNoContent());

        verify(iCustomerService).updateCustomerData(any(UUID.class), any(CustomerDTO.class));
    }

    @Test
    void testCreateNewCustomer() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.getAllCustomers().get(0);
        customerDTO.setVersionCustomer(null);
        customerDTO.setIdCustomer(null);

        given(iCustomerService.createCustomer(any(CustomerDTO.class))).willReturn(customerServiceImpl.getAllCustomers().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testDeleteCustomerById() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.getAllCustomers().get(0);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID , customerDTO.getIdCustomer()))
                .andExpect(status().isNoContent());
        //ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(iCustomerService).deleteCustomerById(uuidArgumentCaptor.capture());
        assert(customerDTO.getIdCustomer().equals(uuidArgumentCaptor.getValue()));
    }

    @Test
    void getAllCustomers() throws Exception {
        given(iCustomerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCustomerByIdNotFound() throws Exception {
        given(iCustomerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customerDTOTest = customerServiceImpl.getAllCustomers().get(0);

        given(iCustomerService.getCustomerById(any(UUID.class))).willReturn(Optional.of(customerDTOTest));

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idCustomer", is(customerDTOTest.getIdCustomer().toString())))
                .andExpect(jsonPath("$.nameCustomer", is(customerDTOTest.getNameCustomer())));
    }
}