package nbo.springframework.spring6restmvc.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoffeControllerTest {
    @Autowired
    CoffeController controller;
    @Test
    void getCoffeeById() {
        System.out.println(controller.getCoffeeById(UUID.randomUUID()));
    }
}