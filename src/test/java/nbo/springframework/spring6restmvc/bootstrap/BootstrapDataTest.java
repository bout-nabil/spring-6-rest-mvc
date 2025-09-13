package nbo.springframework.spring6restmvc.bootstrap;

import nbo.springframework.spring6restmvc.repositories.CoffeeRepository;
import nbo.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BootstrapDataTest {

    @Autowired
    CoffeeRepository coffeeRepository;
    @Autowired
    CustomerRepository customerRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(coffeeRepository, customerRepository);
    }

    @Test
    void testRun() throws Exception {
        bootstrapData.run(null);
//        assertThat(coffeeRepository.count()).isEqualTo(3);
//        assertThat(customerRepository.count()).isEqualTo(3);
        assertEquals(3, coffeeRepository.count());
        assertEquals(3, customerRepository.count());
    }
}