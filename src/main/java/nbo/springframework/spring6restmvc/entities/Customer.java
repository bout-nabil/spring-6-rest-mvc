package nbo.springframework.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

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
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false, length = 36)
    private UUID idCustomer;
    private String nameCustomer;
    private String emailCustomer;
    private String phoneCustomer;
    @Version
    private Integer versionCustomer;
    private LocalDateTime createdAtCustomer;
    private LocalDateTime updatedAtCustomer;

    @Column(name = "code_customer", length = 255)
    private String codeCustomer;
}
