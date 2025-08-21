package nbo.springframework.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbo.springframework.spring6restmvc.models.Coffee;
import nbo.springframework.spring6restmvc.services.CoffeeServiceImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController // @RestController is a convenience annotation that combines @Controller and @ResponseBody
@RequestMapping("/api/v1/coffees") // Base URL for the Coffee API
public class CoffeController {
    private final CoffeeServiceImpl coffeeService;

    @RequestMapping(method = RequestMethod.GET) // @RequestMapping with method GET maps to this method
    public List<Coffee> listAllCoffees() {
        log.debug("Listing all coffees - in the controller");
        return coffeeService.listAllCoffees();
    }

    @RequestMapping(value = "{coffeeId}", method = RequestMethod.GET)
    public Coffee getCoffeeById(@PathVariable("coffeeId") UUID coffeeId) { //@PathVariable binds the coffeeId from the URL to the method parameter
        log.debug("Getting coffee by ID: {}", coffeeId + " - in the controller");
        return coffeeService.getCoffeeById(coffeeId);
    }
}
