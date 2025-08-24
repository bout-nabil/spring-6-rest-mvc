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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ICustomerService iCustomerService;

    CustomerServiceImpl customerService = new CustomerServiceImpl();

    @Test
    void getCustomerById() throws Exception {
        Customer customerTest = customerService.getAllCustomers().get(0);

        given(iCustomerService.getCustomerById(any(UUID.class))).willReturn(customerTest);

        mockMvc.perform(get("/api/v1/customers/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}