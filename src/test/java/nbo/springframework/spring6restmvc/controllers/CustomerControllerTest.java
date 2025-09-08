package nbo.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nbo.springframework.spring6restmvc.models.Customer;
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
    ArgumentCaptor<Customer> argumentCaptor;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testPatchCustomer() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().get(0);

        // Create a map to hold the fields to be patched
        String customerName = "Updated Name";
        String customerPhone = "123-456-7890";

        Customer customerPatch = Customer.builder()
                .nameCustomer(customerName)
                .phoneCustomer(customerPhone)
                .build();

        mockMvc.perform(patch("/api/v1/customers/" + customer.getIdCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerPatch)))
                .andExpect(status().isNoContent());

        verify(iCustomerService).updateCustomerPatchById(uuidArgumentCaptor.capture(), argumentCaptor.capture());
        assert(customer.getIdCustomer().equals(uuidArgumentCaptor.getValue()));
        assert(customerName.equals(argumentCaptor.getValue().getNameCustomer()));
        assert(customerPhone.equals(argumentCaptor.getValue().getPhoneCustomer()));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().get(0);

        mockMvc.perform(put("/api/v1/customers/" + customer.getIdCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());

        verify(iCustomerService).updateCustomerData(any(UUID.class), any(Customer.class));
    }
    @Test
    void testCreateNewCustomer() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().get(0);
        customer.setVersionCustomer(null);
        customer.setIdCustomer(null);

        given(iCustomerService.createCustomer(any(Customer.class))).willReturn(customerServiceImpl.getAllCustomers().get(1));

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testDeleteCustomerById() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().get(0);

        mockMvc.perform(delete("/api/v1/customers/" + customer.getIdCustomer()))
                .andExpect(status().isNoContent());
        //ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(iCustomerService).deleteCustomerById(uuidArgumentCaptor.capture());
        assert(customer.getIdCustomer().equals(uuidArgumentCaptor.getValue()));
    }

    @Test
    void getAllCustomers() throws Exception {
        given(iCustomerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        mockMvc.perform(get("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCustomerById() throws Exception {
        Customer customerTest = customerServiceImpl.getAllCustomers().get(0);

        given(iCustomerService.getCustomerById(any(UUID.class))).willReturn(customerTest);

        mockMvc.perform(get("/api/v1/customers/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idCustomer", is(customerTest.getIdCustomer().toString())))
                .andExpect(jsonPath("$.nameCustomer", is(customerTest.getNameCustomer())));
    }
}