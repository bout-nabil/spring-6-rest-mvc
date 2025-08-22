package nbo.springframework.spring6restmvc.services;

import nbo.springframework.spring6restmvc.models.Coffee;

import java.util.List;
import java.util.UUID;

public interface ICoffeeService {
     Coffee getCoffeeById(UUID id);
    List<Coffee> listAllCoffees();
    Coffee createCoffee(Coffee coffee);
}




