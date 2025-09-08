package nbo.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nbo.springframework.spring6restmvc.models.Coffee;
import nbo.springframework.spring6restmvc.services.CoffeeServiceImpl;
import nbo.springframework.spring6restmvc.services.ICoffeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(CoffeeController.class) // Use @WebMvcTest for controller layer testing
class CoffeeControllerTest {
//    @Autowired
//    CoffeeController controller;
    @Autowired
    MockMvc mockMvc; // MockMvc is used to simulate HTTP requests in tests

    @Autowired
    ObjectMapper objectMapper; // ObjectMapper for JSON serialization/deserialization

    @MockBean
    ICoffeeService iCoffeeService;  // Mock the service layer to isolate controller tests

    CoffeeServiceImpl coffeeServiceImpl = new CoffeeServiceImpl(); // Real service instance for test data

    @Test
    void testCreateNewCoffee() throws JsonProcessingException {
        Coffee coffee = coffeeServiceImpl.listAllCoffees().get(0);
        System.out.println(objectMapper.writeValueAsString(coffee));
    }

    @Test
    void listAllCoffees() throws Exception {
        given(iCoffeeService.listAllCoffees()).willReturn(coffeeServiceImpl.listAllCoffees()); // Mock the service method to return test data

        mockMvc.perform(get("/api/v1/coffees").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCoffeeById() throws Exception {
        //System.out.println(controller.getCoffeeById(UUID.randomUUID()));
        Coffee coffeTest = coffeeServiceImpl.listAllCoffees().get(0); // Get test data from the real service

        given(iCoffeeService.getCoffeeById(any(UUID.class))).willReturn(coffeTest); // Mock the service method to return test data

        mockMvc.perform(get("/api/v1/coffees/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(coffeTest.getIdCoffee().toString())))
                .andExpect(jsonPath("$.name", is(coffeTest.getNameCoffee())));
    }
}