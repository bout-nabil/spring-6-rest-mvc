package nbo.springframework.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbo.springframework.spring6restmvc.models.Coffee;
import nbo.springframework.spring6restmvc.services.CoffeeServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController // @RestController is a convenience annotation that combines @Controller and @ResponseBody
@RequestMapping("/api/v1/coffees") // Base URL for the Coffee API
public class CoffeeController {
    private final CoffeeServiceImpl coffeeService;

    @RequestMapping(method = RequestMethod.GET) // @RequestMapping with method GET maps to this method
    public List<Coffee> listAllCoffees() {
        log.debug("Listing all coffees - in the controller");
        return coffeeService.listAllCoffees();
    }

    //@GetMapping("/{coffeId}") // Alternative way to map GET requests with a path variable
    //@ResponseBody // @ResponseBody indicates that the return value should be bound to the web response body
    @RequestMapping(value = "{coffeeId}", method = RequestMethod.GET)
    public Coffee getCoffeeById(@PathVariable("coffeeId") UUID coffeeId) { //@PathVariable binds the coffeeId from the URL to the method parameter
        log.debug("Getting coffee by ID: {}", coffeeId + " - in the controller");
        return coffeeService.getCoffeeById(coffeeId);
    }

    @PostMapping // @PostMapping is a shortcut for @RequestMapping(method = RequestMethod.POST)
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Coffee> createdCoffee(@RequestBody Coffee coffee) { // @RequestBody binds the request body to the Coffee object
        Coffee savedCoffee = coffeeService.createCoffee(coffee);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/coffees/" + savedCoffee.getIdCoffee().toString()); // Set the Location header to the URI of the created coffee
        return new ResponseEntity<>(savedCoffee, headers, HttpStatus.CREATED);
    }

    @PutMapping("{coffeeId}")
    public ResponseEntity<Void> updateCoffeeById(@PathVariable("coffeeId") UUID coffeeId, @RequestBody Coffee coffee) {
        coffeeService.updateCoffeeData(coffeeId, coffee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{coffeeId}")
    public ResponseEntity<Void> deleteCoffeeById(@PathVariable("coffeeId") UUID coffeeId) {
        coffeeService.deleteCoffeeById(coffeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}



