package nbo.springframework.spring6restmvc.repositories;

import jakarta.validation.ConstraintViolationException;
import nbo.springframework.spring6restmvc.entities.Coffee;
import nbo.springframework.spring6restmvc.models.CoffeeStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest // @DataJpaTest is used for JPA tests that focus only on JPA components. It will configure in-memory database, Hibernate, Spring Data, and the DataSource.
class ICoffeeRepositoryTest {
    @Autowired
    ICoffeeRepository iCoffeeRepository;


    @Test
    void testSaveCoffeeToLong(){

        assertThrows(ConstraintViolationException.class , () -> {
            Coffee coffee = iCoffeeRepository.save(Coffee.builder()
                    .nameCoffee("Test Coffee 012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890")
                    .priceCoffee(new BigDecimal("2.50"))
                    .coffeeStyle(CoffeeStyle.ESPRESSO)
                    .build()); // Using builder pattern to create a Coffee object with name "Test Coffee"

            iCoffeeRepository.flush();
        });
    }

    @Test
    void testSaveCoffee(){
        Coffee coffee = iCoffeeRepository.save(Coffee.builder()
                .nameCoffee("Test Coffee")
                        .priceCoffee(new BigDecimal("2.50"))
                .coffeeStyle(CoffeeStyle.ESPRESSO)
                .build()); // Using builder pattern to create a Coffee object with name "Test Coffee"

        iCoffeeRepository.flush();

        assertThat(coffee).isNotNull();
        assertThat(coffee.getIdCoffee()).isNotNull();
    }
}