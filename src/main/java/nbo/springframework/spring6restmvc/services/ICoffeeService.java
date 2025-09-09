package nbo.springframework.spring6restmvc.services;

import nbo.springframework.spring6restmvc.models.CoffeeDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICoffeeService {

    Optional<CoffeeDTO> getCoffeeById(UUID id); // Optional is added to handle the case where a coffee might not be found

     List<CoffeeDTO> listAllCoffees();

    CoffeeDTO createCoffee(CoffeeDTO coffeeDTO);

    void updateCoffeeData(UUID coffeeId, CoffeeDTO coffeeDTO);

    void deleteCoffeeById(UUID coffeeId);

    void updateCoffeePatchById(UUID coffeeId, CoffeeDTO coffeeDTO);
}




