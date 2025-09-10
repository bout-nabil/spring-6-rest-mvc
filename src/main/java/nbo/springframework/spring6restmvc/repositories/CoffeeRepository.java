package nbo.springframework.spring6restmvc.repositories;

import nbo.springframework.spring6restmvc.entities.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CoffeeRepository extends JpaRepository<Coffee, UUID> {
}
