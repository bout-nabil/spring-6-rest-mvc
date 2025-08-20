package nbo.springframework.spring6restmvc.services;

import nbo.springframework.spring6restmvc.models.Coffee;
import java.util.UUID;

public interface ICoffeeService {
    public Coffee getCoffeeById(UUID id);
}
