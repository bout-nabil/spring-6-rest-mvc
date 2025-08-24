package nbo.springframework.spring6restmvc.controllers;

import nbo.springframework.spring6restmvc.models.Customer;
import nbo.springframework.spring6restmvc.services.CustomerServiceImpl;
import nbo.springframework.spring6restmvc.services.ICustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ICustomerService iCustomerService;

    CustomerServiceImpl customerService = new CustomerServiceImpl();

    @Test
    void getAllCustomers() throws Exception {
        given(iCustomerService.getAllCustomers()).willReturn(customerService.getAllCustomers());

        mockMvc.perform(get("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCustomerById() throws Exception {
        Customer customerTest = customerService.getAllCustomers().get(0);

        given(iCustomerService.getCustomerById(any(UUID.class))).willReturn(customerTest);

        mockMvc.perform(get("/api/v1/customers/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customerTest.getIdCustomer().toString())))
                .andExpect(jsonPath("$.name", is(customerTest.getNameCustomer())));
    }
}