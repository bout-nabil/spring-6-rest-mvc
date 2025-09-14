package nbo.springframework.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import nbo.springframework.spring6restmvc.models.CoffeeDTO;
import nbo.springframework.spring6restmvc.models.CoffeeStyle;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CoffeeServiceImpl implements ICoffeeService {
    private final Map<UUID, CoffeeDTO> coffeeMap;

    public CoffeeServiceImpl(){
        this.coffeeMap = new HashMap<>();

        CoffeeDTO coffeeDTO1 = CoffeeDTO.builder()
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

        CoffeeDTO coffeeDTO2 = CoffeeDTO.builder()
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

        CoffeeDTO coffeeDTO3 = CoffeeDTO.builder()
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

        coffeeMap.put(coffeeDTO1.getIdCoffee(), coffeeDTO1);
        coffeeMap.put(coffeeDTO2.getIdCoffee(), coffeeDTO2);
        coffeeMap.put(coffeeDTO3.getIdCoffee(), coffeeDTO3);
    }

    @Override
    public List<CoffeeDTO> listAllCoffees() {
        return new ArrayList<> (coffeeMap.values());
    }

    @Override
    public CoffeeDTO createCoffee(CoffeeDTO coffeeDTO) {
        CoffeeDTO createdCoffeeDTO = CoffeeDTO.builder()
                .idCoffee(UUID.randomUUID())
                .versionCoffee(1)
                .nameCoffee(coffeeDTO.getNameCoffee())
                .coffeeStyle(coffeeDTO.getCoffeeStyle())
                .quantityCoffee(coffeeDTO.getQuantityCoffee())
                .priceCoffee(coffeeDTO.getPriceCoffee())
                .descriptionCoffee(coffeeDTO.getDescriptionCoffee())
                .createdAtCoffee(LocalDateTime.now())
                .updatedAtCoffee(LocalDateTime.now())
                .build();

        coffeeMap.put(createdCoffeeDTO.getIdCoffee(), createdCoffeeDTO);
        return createdCoffeeDTO;
    }
    @Override
    public Optional<CoffeeDTO> updateCoffeeData(UUID coffeeId, CoffeeDTO coffeeDTO) {
        log.info("Updating coffeeDTO with ID: {}", coffeeId);
        CoffeeDTO existingCoffeeDTO = coffeeMap.get(coffeeId);
        if (existingCoffeeDTO != null) {
            existingCoffeeDTO.setNameCoffee(existingCoffeeDTO.getNameCoffee());
            existingCoffeeDTO.setVersionCoffee(existingCoffeeDTO.getVersionCoffee());
            existingCoffeeDTO.setCoffeeStyle(existingCoffeeDTO.getCoffeeStyle());
            existingCoffeeDTO.setQuantityCoffee(existingCoffeeDTO.getQuantityCoffee());
            existingCoffeeDTO.setPriceCoffee(existingCoffeeDTO.getPriceCoffee());
            existingCoffeeDTO.setDescriptionCoffee(existingCoffeeDTO.getDescriptionCoffee());
            existingCoffeeDTO.setUpdatedAtCoffee(LocalDateTime.now());
            existingCoffeeDTO.setUpdatedAtCoffee(LocalDateTime.now());
            coffeeMap.put(coffeeId, existingCoffeeDTO);
        } else {
            log.warn("CoffeeDTO with ID {} not found. Update operation skipped.", coffeeId);
        }
        return Optional.ofNullable(existingCoffeeDTO);
    }

    @Override
    public void deleteCoffeeById(UUID coffeeId) {
        CoffeeDTO removedCoffeeDTO = coffeeMap.remove(coffeeId);
        if(removedCoffeeDTO != null) {
            log.info("Deleted coffee with ID: {}", coffeeId);
        } else {
            log.warn("CoffeeDTO with ID {} not found. Delete operation skipped.", coffeeId);
        }
    }

    @Override
    public void updateCoffeePatchById(UUID coffeeId, CoffeeDTO coffeeDTO) {
        CoffeeDTO existingCoffeeDTO = coffeeMap.get(coffeeId);
        if (existingCoffeeDTO != null) {
            if (StringUtils.hasText(coffeeDTO.getNameCoffee())) {
                existingCoffeeDTO.setNameCoffee(coffeeDTO.getNameCoffee());
            }
            if (coffeeDTO.getVersionCoffee() != null) {
                existingCoffeeDTO.setVersionCoffee(coffeeDTO.getVersionCoffee());
            }
            if (coffeeDTO.getCoffeeStyle() != null) {
                existingCoffeeDTO.setCoffeeStyle(coffeeDTO.getCoffeeStyle());
            }
            if (coffeeDTO.getQuantityCoffee() != null) {
                existingCoffeeDTO.setQuantityCoffee(coffeeDTO.getQuantityCoffee());
            }
            if (coffeeDTO.getPriceCoffee() != null) {
                existingCoffeeDTO.setPriceCoffee(coffeeDTO.getPriceCoffee());
            }
            if (StringUtils.hasText(coffeeDTO.getDescriptionCoffee())) {
                existingCoffeeDTO.setDescriptionCoffee(coffeeDTO.getDescriptionCoffee());
            }
            existingCoffeeDTO.setUpdatedAtCoffee(LocalDateTime.now());
            coffeeMap.put(coffeeId, existingCoffeeDTO);
        } else {
            log.warn("CoffeeDTO with ID {} not found. Patch operation skipped.", coffeeId);
        }
    }

    @Override
    public Optional<CoffeeDTO> getCoffeeById(UUID id) {
        log.debug("Getting coffee by ID: {}", id + " - in the service");
        return Optional.of(coffeeMap.get(id));
    }
}





