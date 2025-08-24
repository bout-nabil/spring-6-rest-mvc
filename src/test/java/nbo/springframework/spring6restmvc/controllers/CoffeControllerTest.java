package nbo.springframework.spring6restmvc.controllers;

import nbo.springframework.spring6restmvc.services.CoffeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(CoffeeController.class) // Use @WebMvcTest for controller layer testing
class CoffeControllerTest {
//    @Autowired
//    CoffeeController controller;
    @Autowired
    MockMvc mockMvc; // MockMvc is used to simulate HTTP requests in tests

    @MockitoBean    // Use @MockitoBean to create and inject a mock of the CoffeeServiceImpl
    CoffeeServiceImpl coffeeService; // Mock the CoffeeServiceImpl to isolate controller tests

    @Test
    void getCoffeeById() throws Exception {
        //System.out.println(controller.getCoffeeById(UUID.randomUUID()));
        mockMvc.perform(get("/api/v1/coffees/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()); // Perform a GET request and expect a 200 OK status
    }
}