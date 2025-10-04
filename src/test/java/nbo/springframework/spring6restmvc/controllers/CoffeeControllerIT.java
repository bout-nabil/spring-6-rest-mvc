package nbo.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nbo.springframework.spring6restmvc.entities.Coffee;
import nbo.springframework.spring6restmvc.mappers.CoffeeMapper;
import nbo.springframework.spring6restmvc.models.CoffeeDTO;
import nbo.springframework.spring6restmvc.repositories.ICoffeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CoffeeControllerIT {
    @Autowired
    ICoffeeRepository iCoffeeRepository;
    @Autowired
    CoffeeController coffeeController;
    @Autowired
    CoffeeMapper coffeeMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void testPatchCoffeBadName() throws Exception {
        Coffee coffee = iCoffeeRepository.findAll().get(0);

        Map<String, Object> coffeeMap = new HashMap<>();
        coffeeMap.put("nameCoffee", "new Name 0123465789012345678901234567890123456789012345678901234567890123456789"); // 101 characters
        coffeeMap.put("coffeeStyle", "ESPRESSO");
        coffeeMap.put("priceCoffee", 1.99);

        MvcResult mvcResult = mockMvc.perform(patch(CoffeeController.COFFEE_PATH_ID, coffee.getIdCoffee())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffeeMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)))
                //.andExpect(jsonPath("$[0].nameCoffee", is("size must be between 0 and 50")))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testDeleteNotFoundCoffee(){
        assertThrows(NotFoundException.class, () -> {
            coffeeController.deleteCoffeeById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteCoffeeById(){
        Coffee coffee = iCoffeeRepository.findAll().get(0);
        ResponseEntity responseEntity = coffeeController.deleteCoffeeById(coffee.getIdCoffee());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);

        assertThat(iCoffeeRepository.findById(coffee.getIdCoffee()).isEmpty());
    }

    @Test
    void testUpdatedNotFoundCoffee(){
        assertThrows(NotFoundException.class, () -> {
           coffeeController.updateCoffeeData(UUID.randomUUID(), CoffeeDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testUpdatedExistingCoffee(){
        Coffee coffee = iCoffeeRepository.findAll().get(0);
        CoffeeDTO coffeeDTO = coffeeMapper.coffeeToCoffeeDto(coffee);
        coffeeDTO.setIdCoffee(null);
        coffeeDTO.setVersionCoffee(null);
        String newName = "Updated Name Coffee";
        coffeeDTO.setNameCoffee(newName);

        ResponseEntity responseEntity = coffeeController.updateCoffeeData(coffee.getIdCoffee(), coffeeDTO);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);

        Coffee updatedCoffee = iCoffeeRepository.findById(coffee.getIdCoffee()).get();
        assertThat(updatedCoffee.getNameCoffee()).isEqualTo(newName);
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewCoffee(){
        CoffeeDTO coffeeDTO = CoffeeDTO.builder()
                .nameCoffee("Test Coffee")
                .build(); // Using builder pattern to create a Coffee object with name "Test Coffee"
        ResponseEntity responseEntity = coffeeController.createdCoffee(coffeeDTO); // Using the controller to create a new coffee

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[locationUUID.length -1]);

        Coffee coffee = iCoffeeRepository.findById(savedUUID).get();
        assertThat(coffee).isNotNull();
    }

    @Test
    void testCoffeeNotFound(){
        assertThrows(NotFoundException.class, () -> {
            coffeeController.getCoffeeById(UUID.randomUUID());
        });
    }

    @Test
    void testGetCoffeeById(){
        Coffee coffee = iCoffeeRepository.findAll().get(0);
        CoffeeDTO coffeeDTO = coffeeController.getCoffeeById(coffee.getIdCoffee());
        assertNotNull(coffeeDTO);
        assertEquals(coffee.getIdCoffee(), coffeeDTO.getIdCoffee());
    }


    @Test
    void ListAllCoffees() {
        List<CoffeeDTO> coffeeDTOList = coffeeController.listAllCoffees();
        assertNotNull(coffeeDTOList);
        assertTrue(coffeeDTOList.size() > 0);
    }

    @Rollback // To ensure that the database changes are rolled back after the test
    @Transactional // To manage the transaction for the test
    @Test
    void ListEmptyCoffees() {
        iCoffeeRepository.deleteAll();
        List<CoffeeDTO> coffeeDTOList = coffeeController.listAllCoffees();
        assertNotNull(coffeeDTOList);
        assertEquals(0, coffeeDTOList.size());
    }
}