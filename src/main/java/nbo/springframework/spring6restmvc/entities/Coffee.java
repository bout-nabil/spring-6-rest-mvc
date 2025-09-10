package nbo.springframework.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import nbo.springframework.spring6restmvc.models.CoffeeStyle;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Coffee {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(columnDefinition = "varchar", updatable = false, nullable = false, length = 36)
    private UUID idCoffee;
    @Version
    private Integer versionCoffee;
    private String nameCoffee;
    private Integer quantityCoffee;
    private BigDecimal priceCoffee;
    private String descriptionCoffee;
    private LocalDateTime createdAtCoffee;
    private LocalDateTime updatedAtCoffee;
    private CoffeeStyle coffeeStyle;
}
