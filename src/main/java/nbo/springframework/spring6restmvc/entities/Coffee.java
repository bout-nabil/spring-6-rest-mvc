package nbo.springframework.spring6restmvc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import nbo.springframework.spring6restmvc.models.CoffeeStyle;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

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
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false, length = 36)
    private UUID idCoffee;
    @Version
    private Integer versionCoffee;

    @NonNull
    @NotBlank
    @Size(max = 50)
    @Column(length = 50)
    private String nameCoffee;
    private Integer quantityCoffee;

    @NonNull
    private BigDecimal priceCoffee;

    @Size(max = 250)
    private String descriptionCoffee;
    private LocalDateTime createdAtCoffee;
    private LocalDateTime updatedAtCoffee;

    @NonNull
    private CoffeeStyle coffeeStyle;
}
