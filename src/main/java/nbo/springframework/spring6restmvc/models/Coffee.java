package nbo.springframework.spring6restmvc.models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Coffee {
    private UUID idCoffee;
    private Integer versionCoffee;
    private String nameCoffee;
    private Integer quantityCoffee;
    private BigDecimal priceCoffee;
    private String descriptionCoffee;
    private LocalDateTime createdAtCoffee;
    private LocalDateTime updatedAtCoffee;
    private CoffeeStyle coffeeStyle;
}
