package nbo.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nbo.springframework.spring6restmvc.models.Coffee;
import nbo.springframework.spring6restmvc.services.CoffeeServiceImpl;
import nbo.springframework.spring6restmvc.services.ICoffeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor; // ArgumentCaptor to capture UUID arguments

    @Captor
    ArgumentCaptor<Coffee> coffeeArgumentCaptor;    // ArgumentCaptor to capture Coffee arguments

    @BeforeEach     // Initialize before each test
    void setUp() {
        coffeeServiceImpl = new CoffeeServiceImpl();
    }

    @Test
    void testPatchCoffe() throws Exception {
        Coffee coffee = coffeeServiceImpl.listAllCoffees().get(0);

        Map<String, Object> coffeeMap = new HashMap<>(); // Create a map to hold the fields to be patched
        coffeeMap.put("nameCoffee", "new Name");

        mockMvc.perform(patch(CoffeeController.COFFEE_PATH_ID, coffee.getIdCoffee())
                        .contentType(MediaType.APPLICATION_JSON) // Specify that we are sending JSON
                        .accept(MediaType.APPLICATION_JSON) // Specify that we expect JSON in the response
                        .content(objectMapper.writeValueAsString(coffeeMap))) // Convert the map to a JSON string
                .andExpect(status().isNoContent()); // Expect a 204 No Content response

        verify(iCoffeeService).updateCoffeePatchById(uuidArgumentCaptor.capture(), coffeeArgumentCaptor.capture()); // Capture the arguments passed to the service method
        assertThat(coffee.getIdCoffee()).isEqualTo(uuidArgumentCaptor.getValue()); // Assert that the captured UUID matches the coffee ID
        assertThat(coffeeMap.get("nameCoffee")).isEqualTo(coffeeArgumentCaptor.getValue().getNameCoffee()); // Assert that the captured Coffee object's name matches the patched name
    }

    @Test
    void testUpdateCoffee() throws Exception {
        Coffee coffee = coffeeServiceImpl.listAllCoffees().get(0); // Get a test coffee from the real service

        mockMvc.perform(put(CoffeeController.COFFEE_PATH_ID, coffee.getIdCoffee()) // Simulate a POST request to update the coffee
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffee)))
                .andExpect(status().isNoContent());

        // Verify that the service method was called with any UUID and any Coffee object
        verify(iCoffeeService).updateCoffeeData(any(UUID.class), any(Coffee.class));
    }

    @Test
    void testCreateNewCoffee() throws Exception {
        Coffee coffee = coffeeServiceImpl.listAllCoffees().get(0); // Get a test coffee from the real service
        coffee.setVersionCoffee(null);
        coffee.setIdCoffee(null);

        given(iCoffeeService.createCoffee(any(Coffee.class))).willReturn(coffeeServiceImpl.listAllCoffees().get(1)); // Mock the service method to return a new coffee

        mockMvc.perform(post(CoffeeController.COFFEE_PATH)         // Simulate a POST request to create a new coffee
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffee)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testDeleteCoffee() throws Exception {
        Coffee coffee = coffeeServiceImpl.listAllCoffees().get(0); // Get a test coffee from the real service

        mockMvc.perform(delete(CoffeeController.COFFEE_PATH_ID, coffee.getIdCoffee()) // Simulate a DELETE request to delete the coffee
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        // Verify that the service method was called with any UUID
        verify(iCoffeeService).deleteCoffeeById(uuidArgumentCaptor.capture());
        // Assert that the captured UUID matches the ID of the coffee we attempted to delete
        assertThat(coffee.getIdCoffee()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void listAllCoffees() throws Exception {
        given(iCoffeeService.listAllCoffees()).willReturn(coffeeServiceImpl.listAllCoffees()); // Mock the service method to return test data

        mockMvc.perform(get(CoffeeController.COFFEE_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCoffeeById() throws Exception {
        //System.out.println(controller.getCoffeeById(UUID.randomUUID()));
        Coffee coffeTest = coffeeServiceImpl.listAllCoffees().get(0); // Get test data from the real service

        given(iCoffeeService.getCoffeeById(any(UUID.class))).willReturn(coffeTest); // Mock the service method to return test data

        mockMvc.perform(get(CoffeeController.COFFEE_PATH_ID, UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idCoffee", is(coffeTest.getIdCoffee().toString())))
                .andExpect(jsonPath("$.nameCoffee", is(coffeTest.getNameCoffee())));
    }
}