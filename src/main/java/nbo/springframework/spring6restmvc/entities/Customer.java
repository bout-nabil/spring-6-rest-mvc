package nbo.springframework.spring6restmvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    private UUID idCustomer;
    private String nameCustomer;
    private String emailCustomer;
    private String phoneCustomer;
    @Version
    private String versionCustomer;
    private LocalDateTime createdAtCustomer;
    private LocalDateTime updatedAtCustomer;
}
