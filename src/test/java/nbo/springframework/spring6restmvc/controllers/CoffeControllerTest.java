package nbo.springframework.spring6restmvc.controllers;

import nbo.springframework.spring6restmvc.models.Coffee;
import nbo.springframework.spring6restmvc.services.CoffeeServiceImpl;
import nbo.springframework.spring6restmvc.services.ICoffeeService;
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

//@SpringBootTest
@WebMvcTest(CoffeeController.class) // Use @WebMvcTest for controller layer testing
class CoffeControllerTest {
//    @Autowired
//    CoffeeController controller;
    @Autowired
    MockMvc mockMvc; // MockMvc is used to simulate HTTP requests in tests

    @MockitoBean    // Use @MockitoBean to create and inject a mock of the CoffeeServiceImpl
    ICoffeeService iCoffeeService;  // Mock the service layer to isolate controller tests

    CoffeeServiceImpl coffeeService = new CoffeeServiceImpl(); // Real service instance for test data

    @Test
    void getCoffeeById() throws Exception {
        //System.out.println(controller.getCoffeeById(UUID.randomUUID()));
        Coffee coffeTest = iCoffeeService.listAllCoffees().get(0); // Get test data from the real service

        given(iCoffeeService.getCoffeeById(any(UUID.class))).willReturn(coffeTest); // Mock the service method to return test data

        mockMvc.perform(get("/api/v1/coffees/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)); // Verify response status and content type
    }
}