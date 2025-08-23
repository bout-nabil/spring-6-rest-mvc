package nbo.springframework.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import nbo.springframework.spring6restmvc.models.Coffee;
import nbo.springframework.spring6restmvc.models.CoffeeStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CoffeeServiceImpl implements ICoffeeService {
    private final Map<UUID, Coffee> coffeeMap;

    public CoffeeServiceImpl(){
        this.coffeeMap = new HashMap<>();

        Coffee coffee1 = Coffee.builder()
                .idCoffee(UUID.randomUUID())
                .versionCoffee(1)
                .nameCoffee("Espresso")
                .coffeeStyle(CoffeeStyle.ESPRESSO)
                .quantityCoffee(100)
                .priceCoffee(new BigDecimal("2.50"))
                .descriptionCoffee("Rich and bold espresso coffee")
                .createdAtCoffee(LocalDateTime.now())
                .updatedAtCoffee(LocalDateTime.now())
                .build();

        Coffee coffee2 = Coffee.builder()
                .idCoffee(UUID.randomUUID())
                .versionCoffee(1)
                .nameCoffee("Latte")
                .coffeeStyle(CoffeeStyle.LATTE)
                .quantityCoffee(150)
                .priceCoffee(new BigDecimal("3.00"))
                .descriptionCoffee("Smooth and creamy latte coffee")
                .createdAtCoffee(LocalDateTime.now())
                .updatedAtCoffee(LocalDateTime.now())
                .build();

        Coffee coffee3 = Coffee.builder()
                .idCoffee(UUID.randomUUID())
                .versionCoffee(1)
                .nameCoffee("Cappuccino")
                .coffeeStyle(CoffeeStyle.CAPPUCCINO)
                .quantityCoffee(120)
                .priceCoffee(new BigDecimal("3.50"))
                .descriptionCoffee("Rich and frothy cappuccino coffee")
                .createdAtCoffee(LocalDateTime.now())
                .updatedAtCoffee(LocalDateTime.now())
                .build();

        coffeeMap.put(coffee1.getIdCoffee(), coffee1);
        coffeeMap.put(coffee2.getIdCoffee(), coffee2);
        coffeeMap.put(coffee3.getIdCoffee(), coffee3);
    }

    @Override
    public List<Coffee> listAllCoffees() {
        return new ArrayList<> (coffeeMap.values());
    }

    @Override
    public Coffee createCoffee(Coffee coffee) {
        Coffee createdCoffee = Coffee.builder()
                .idCoffee(UUID.randomUUID())
                .versionCoffee(1)
                .nameCoffee(coffee.getNameCoffee())
                .coffeeStyle(coffee.getCoffeeStyle())
                .quantityCoffee(coffee.getQuantityCoffee())
                .priceCoffee(coffee.getPriceCoffee())
                .descriptionCoffee(coffee.getDescriptionCoffee())
                .createdAtCoffee(LocalDateTime.now())
                .updatedAtCoffee(LocalDateTime.now())
                .build();

        coffeeMap.put(createdCoffee.getIdCoffee(), createdCoffee);
        return createdCoffee;
    }

    @Override
    public void updateCoffeeData(UUID coffeeId, Coffee coffee) {
        log.info("Updating coffee with ID: {}", coffeeId);
        Coffee existingCoffee = coffeeMap.get(coffeeId);
        if (existingCoffee != null) {
            existingCoffee.setNameCoffee(existingCoffee.getNameCoffee());
            existingCoffee.setVersionCoffee(existingCoffee.getVersionCoffee());
            existingCoffee.setCoffeeStyle(existingCoffee.getCoffeeStyle());
            existingCoffee.setQuantityCoffee(existingCoffee.getQuantityCoffee());
            existingCoffee.setPriceCoffee(existingCoffee.getPriceCoffee());
            existingCoffee.setDescriptionCoffee(existingCoffee.getDescriptionCoffee());
            existingCoffee.setUpdatedAtCoffee(LocalDateTime.now());
            existingCoffee.setUpdatedAtCoffee(LocalDateTime.now());
            coffeeMap.put(coffeeId, existingCoffee);
        } else {
            log.warn("Coffee with ID {} not found. Update operation skipped.", coffeeId);
        }
    }

    @Override
    public Coffee getCoffeeById(UUID id) {
        log.debug("Getting coffee by ID: {}", id + " - in the service");
        return coffeeMap.get(id);
    }
}





