package nbo.springframework.spring6restmvc.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {
    private UUID idCustomer;
    @NotBlank
    @NotNull
    private String nameCustomer;
    private String emailCustomer;
    private String phoneCustomer;
    private Integer versionCustomer;
    private LocalDateTime createdAtCustomer;
    private LocalDateTime updatedAtCustomer;
}
