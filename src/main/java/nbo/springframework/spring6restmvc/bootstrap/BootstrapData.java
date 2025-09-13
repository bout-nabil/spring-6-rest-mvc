package nbo.springframework.spring6restmvc.bootstrap;

import lombok.RequiredArgsConstructor;
import nbo.springframework.spring6restmvc.entities.Coffee;
import nbo.springframework.spring6restmvc.entities.Customer;
import nbo.springframework.spring6restmvc.models.CoffeeStyle;
import nbo.springframework.spring6restmvc.repositories.ICoffeeRepository;
import nbo.springframework.spring6restmvc.repositories.ICustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final ICoffeeRepository ICoffeeRepository;
    private final ICustomerRepository ICustomerRepository;


    @Override
    public void run(String... args) throws Exception {
        loadCoffeeData();
        loadCustomerData();
    }

    private void loadCoffeeData() {
        if (this.ICoffeeRepository.count() == 0) {
            Coffee coffee1 = Coffee.builder()
                    .versionCoffee(1)
                    .nameCoffee("Espresso")
                    .coffeeStyle(CoffeeStyle.ESPRESSO)
                    .quantityCoffee(100)
                    .priceCoffee(new BigDecimal("2.50"))
                    .descriptionCoffee("Rich and bold espresso coffee")
                    .createdAtCoffee(LocalDateTime.now())
                    .updatedAtCoffee(LocalDateTime.now())
                    .build();

            Coffee coffee2 = Coffee.builder()
                    .versionCoffee(1)
                    .nameCoffee("Latte")
                    .coffeeStyle(CoffeeStyle.LATTE)
                    .quantityCoffee(150)
                    .priceCoffee(new BigDecimal("3.00"))
                    .descriptionCoffee("Smooth and creamy latte coffee")
                    .createdAtCoffee(LocalDateTime.now())
                    .updatedAtCoffee(LocalDateTime.now())
                    .build();

            Coffee coffee3 = Coffee.builder()
                    .versionCoffee(1)
                    .nameCoffee("Cappuccino")
                    .coffeeStyle(CoffeeStyle.CAPPUCCINO)
                    .quantityCoffee(120)
                    .priceCoffee(new BigDecimal("3.50"))
                    .descriptionCoffee("Rich and frothy cappuccino coffee")
                    .createdAtCoffee(LocalDateTime.now())
                    .updatedAtCoffee(LocalDateTime.now())
                    .build();

            ICoffeeRepository.save(coffee1);
            ICoffeeRepository.save(coffee2);
            ICoffeeRepository.save(coffee3);
        } else {
            System.out.println("Coffees already loaded");
        }
    }

    private void loadCustomerData() {
        if (ICustomerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .nameCustomer("Ali")
                    .emailCustomer("ali.ali@example.com")
                    .phoneCustomer("0612345678")
                    .versionCustomer(1)
                    .createdAtCustomer(LocalDateTime.now())
                    .updatedAtCustomer(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .nameCustomer("Nabil")
                    .emailCustomer("nabil.nabil@example.com")
                    .phoneCustomer("0623456789")
                    .versionCustomer(1)
                    .createdAtCustomer(LocalDateTime.now())
                    .updatedAtCustomer(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .nameCustomer("Tawfiq")
                    .emailCustomer("tawfiq.tawfiq@example.com")
                    .phoneCustomer("0634567890")
                    .versionCustomer(1)
                    .createdAtCustomer(LocalDateTime.now())
                    .updatedAtCustomer(LocalDateTime.now())
                    .build();

            ICustomerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        } else {
            System.out.println("Customers already loaded");
        }
    }
}
