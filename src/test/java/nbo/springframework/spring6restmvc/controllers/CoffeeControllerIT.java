package nbo.springframework.spring6restmvc.controllers;

import nbo.springframework.spring6restmvc.entities.Coffee;
import nbo.springframework.spring6restmvc.models.CoffeeDTO;
import nbo.springframework.spring6restmvc.repositories.ICoffeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CoffeeControllerIT {
    @Autowired
    ICoffeeRepository iCoffeeRepository;
    @Autowired
    CoffeeController coffeeController;

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