package nbo.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nbo.springframework.spring6restmvc.models.Coffee;
import nbo.springframework.spring6restmvc.services.CoffeeServiceImpl;
import nbo.springframework.spring6restmvc.services.ICoffeeService;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    CoffeeServiceImpl coffeeServiceImpl; // Real service instance for test data

    @BeforeEach     // Initialize before each test
    void setUp() {
        coffeeServiceImpl = new CoffeeServiceImpl();
    }

    @Test
    void testCreateNewCoffee() throws Exception {
        Coffee coffee = coffeeServiceImpl.listAllCoffees().get(0); // Get a test coffee from the real service
        coffee.setVersionCoffee(null);
        coffee.setIdCoffee(null);

        given(iCoffeeService.createCoffee(any(Coffee.class))).willReturn(coffeeServiceImpl.listAllCoffees().get(1)); // Mock the service method to return a new coffee

        mockMvc.perform(post("/api/v1/coffees")         // Simulate a POST request to create a new coffee
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffee)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
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
                .andExpect(jsonPath("$.idCoffee", is(coffeTest.getIdCoffee().toString())))
                .andExpect(jsonPath("$.nameCoffee", is(coffeTest.getNameCoffee())));
    }
}