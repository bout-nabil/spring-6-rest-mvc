package nbo.springframework.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbo.springframework.spring6restmvc.models.Coffee;
import nbo.springframework.spring6restmvc.services.CoffeeServiceImpl;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class CoffeController {
    private final CoffeeServiceImpl coffeeService;

    public Coffee getCoffeeById(UUID id) {
        log.debug("Getting coffee by ID: {}", id + " - in the controller");
        return coffeeService.getCoffeeById(id);
    }
}
