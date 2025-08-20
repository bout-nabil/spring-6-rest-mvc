package nbo.springframework.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import nbo.springframework.spring6restmvc.services.CoffeeServiceImpl;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class CoffeController {
    private final CoffeeServiceImpl coffeeService;
}
