package nbo.springframework.spring6restmvc.controllers;

import nbo.springframework.spring6restmvc.entities.Coffee;
import nbo.springframework.spring6restmvc.mappers.CoffeeMapper;
import nbo.springframework.spring6restmvc.models.CoffeeDTO;
import nbo.springframework.spring6restmvc.repositories.ICoffeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CoffeeControllerIT {
    @Autowired
    ICoffeeRepository iCoffeeRepository;
    @Autowired
    CoffeeController coffeeController;
    @Autowired
    CoffeeMapper coffeeMapper;

    @Test
    void testUpdatedNonFoundCoffee(){
        assertThrows(NotFoundException.class, () -> {
           coffeeController.updateCoffeeData(UUID.randomUUID(), CoffeeDTO.builder().build());
        });
    }

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