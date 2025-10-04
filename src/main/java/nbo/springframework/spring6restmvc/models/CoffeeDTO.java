package nbo.springframework.spring6restmvc.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CoffeeDTO {
    private UUID idCoffee;
    private Integer versionCoffee;
    @NotBlank
    @NotNull
    private String nameCoffee;
    private Integer quantityCoffee;
    @NotNull
    private BigDecimal priceCoffee;
    private String descriptionCoffee;
    private LocalDateTime createdAtCoffee;
    private LocalDateTime updatedAtCoffee;
    @NotNull
    private CoffeeStyle coffeeStyle;
}
