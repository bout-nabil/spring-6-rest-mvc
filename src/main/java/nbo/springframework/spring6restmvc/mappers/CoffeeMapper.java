package nbo.springframework.spring6restmvc.mappers;

import nbo.springframework.spring6restmvc.entities.Coffee;
import nbo.springframework.spring6restmvc.models.CoffeeDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CoffeeMapper {
    Coffee coffeeDtoToCoffee(CoffeeDTO coffeeDto);
    CoffeeDTO coffeeToCoffeeDto(Coffee coffee);
}
