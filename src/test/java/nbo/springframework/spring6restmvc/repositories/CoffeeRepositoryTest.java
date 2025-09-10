package nbo.springframework.spring6restmvc.repositories;

import nbo.springframework.spring6restmvc.entities.Coffee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // @DataJpaTest is used for JPA tests that focus only on JPA components. It will configure in-memory database, Hibernate, Spring Data, and the DataSource.
class CoffeeRepositoryTest {
    @Autowired
    CoffeeRepository coffeeRepository;

    @Test
    void testSaveCoffee(){
        Coffee coffee = coffeeRepository.save(Coffee.builder()
                .nameCoffee("Test Coffee")
                .build()); // Using builder pattern to create a Coffee object with name "Test Coffee"
        assertThat(coffee).isNotNull();
        assertThat(coffee.getIdCoffee()).isNotNull();
    }
}