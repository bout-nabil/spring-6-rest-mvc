package nbo.springframework.spring6restmvc.controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbo.springframework.spring6restmvc.models.Coffee;
import nbo.springframework.spring6restmvc.services.CoffeeServiceImpl;
import nbo.springframework.spring6restmvc.services.ICoffeeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
//@AllArgsConstructor
@RequiredArgsConstructor
@RestController // @RestController is a convenience annotation that combines @Controller and @ResponseBody
//@RequestMapping("/api/v1/coffees") // Base URL for the Coffee API
public class CoffeeController {
    public static final String COFFEE_PATH = "/api/v1/coffees";
    public static final String COFFEE_PATH_ID = COFFEE_PATH + "/{coffeeId}";
    private final ICoffeeService coffeeService;

    @GetMapping(value = COFFEE_PATH) // @RequestMapping with method GET maps to this method
    public List<Coffee> listAllCoffees() {
        log.debug("Listing all coffees - in the controller");
        return coffeeService.listAllCoffees();
    }

    //@GetMapping("/{coffeId}") // Alternative way to map GET requests with a path variable
    //@ResponseBody // @ResponseBody indicates that the return value should be bound to the web response body
    @GetMapping(value = COFFEE_PATH_ID)
    public Coffee getCoffeeById(@PathVariable("coffeeId") UUID coffeeId) { //@PathVariable binds the coffeeId from the URL to the method parameter
        log.debug("Getting coffee by ID: {}", coffeeId + " - in the controller");
        return coffeeService.getCoffeeById(coffeeId);
    }

    @PostMapping(COFFEE_PATH) // @PostMapping is a shortcut for @RequestMapping(method = RequestMethod.POST)
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Coffee> createdCoffee(@RequestBody Coffee coffee) { // @RequestBody binds the request body to the Coffee object
        Coffee savedCoffee = coffeeService.createCoffee(coffee);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", COFFEE_PATH +"/" + savedCoffee.getIdCoffee().toString()); // Set the Location header to the URI of the created coffee
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(COFFEE_PATH_ID)
    public ResponseEntity<Void> updateCoffeeData(@PathVariable("coffeeId") UUID coffeeId, @RequestBody Coffee coffee) {
        coffeeService.updateCoffeeData(coffeeId, coffee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(COFFEE_PATH_ID)
    public ResponseEntity<Void> deleteCoffeeById(@PathVariable("coffeeId") UUID coffeeId) {
        coffeeService.deleteCoffeeById(coffeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(COFFEE_PATH_ID)
    public ResponseEntity<Void> updateCoffeePatchById(@PathVariable("coffeeId") UUID coffeeId, @RequestBody Coffee coffee) {
        coffeeService.updateCoffeePatchById(coffeeId, coffee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}



