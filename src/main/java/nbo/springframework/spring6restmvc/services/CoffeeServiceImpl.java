package nbo.springframework.spring6restmvc.services;

import nbo.springframework.spring6restmvc.models.Coffee;
import nbo.springframework.spring6restmvc.models.CoffeeStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CoffeeServiceImpl implements ICoffeeService {
    @Override
    public Coffee getCoffeeById(UUID id) {
        return Coffee.builder()
                .idCoffee(id)
                .versionCoffee(1)
                .nameCoffee("Espresso")
                .coffeeStyle(CoffeeStyle.ESPRESSO)
                .quantityCoffee(100)
                .priceCoffee(new BigDecimal("2.50"))
                .descriptionCoffee("Rich and bold espresso coffee")
                .createdAtCoffee(LocalDateTime.now())
                .updatedAtCoffee(LocalDateTime.now())
                .build();
    }
}
