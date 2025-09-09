package nbo.springframework.spring6restmvc.services;

import nbo.springframework.spring6restmvc.models.Coffee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICoffeeService {

    Optional<Coffee> getCoffeeById(UUID id); // Optional is added to handle the case where a coffee might not be found

     List<Coffee> listAllCoffees();

    Coffee createCoffee(Coffee coffee);

    void updateCoffeeData(UUID coffeeId, Coffee coffee);

    void deleteCoffeeById(UUID coffeeId);

    void updateCoffeePatchById(UUID coffeeId, Coffee coffee);
}




