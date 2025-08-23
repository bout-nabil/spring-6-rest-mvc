package nbo.springframework.spring6restmvc.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class CoffeControllerTest {
    @Autowired
    CoffeeController controller;
    @Test
    void getCoffeeById() {
        System.out.println(controller.getCoffeeById(UUID.randomUUID()));
    }
}