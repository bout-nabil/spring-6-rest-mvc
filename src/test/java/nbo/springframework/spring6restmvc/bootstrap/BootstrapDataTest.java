package nbo.springframework.spring6restmvc.bootstrap;

import nbo.springframework.spring6restmvc.repositories.ICoffeeRepository;
import nbo.springframework.spring6restmvc.repositories.ICustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BootstrapDataTest {

    @Autowired
    ICoffeeRepository ICoffeeRepository;
    @Autowired
    ICustomerRepository ICustomerRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(ICoffeeRepository, ICustomerRepository);
    }

    @Test
    void testRun() throws Exception {
        bootstrapData.run(null);
//        assertThat(ICoffeeRepository.count()).isEqualTo(3);
//        assertThat(ICustomerRepository.count()).isEqualTo(3);
        assertEquals(3, ICoffeeRepository.count());
        assertEquals(3, ICustomerRepository.count());
    }
}