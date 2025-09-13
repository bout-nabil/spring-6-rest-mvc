package nbo.springframework.spring6restmvc.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {
    private UUID idCustomer;
    private String nameCustomer;
    private String emailCustomer;
    private String phoneCustomer;
    private Integer versionCustomer;
    private LocalDateTime createdAtCustomer;
    private LocalDateTime updatedAtCustomer;
}
