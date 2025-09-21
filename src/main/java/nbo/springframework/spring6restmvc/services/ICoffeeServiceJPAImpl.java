package nbo.springframework.spring6restmvc.services;

import lombok.RequiredArgsConstructor;
import nbo.springframework.spring6restmvc.mappers.CoffeeMapper;
import nbo.springframework.spring6restmvc.models.CoffeeDTO;
import nbo.springframework.spring6restmvc.repositories.ICoffeeRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class ICoffeeServiceJPAImpl implements ICoffeeService {
    private final ICoffeeRepository iCoffeeRepository;
    private final CoffeeMapper coffeeMapper;
    @Override
    public Optional<CoffeeDTO> getCoffeeById(UUID id) {
        return Optional.ofNullable(coffeeMapper.coffeeToCoffeeDto(iCoffeeRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public List<CoffeeDTO> listAllCoffees() {
        return iCoffeeRepository.findAll()
                .stream()
                .map(coffeeMapper::coffeeToCoffeeDto)
                .toList();
    }

    @Override
    public CoffeeDTO createCoffee(CoffeeDTO coffeeDTO) {
        return coffeeMapper.coffeeToCoffeeDto(iCoffeeRepository
                .save(coffeeMapper.coffeeDtoToCoffee(coffeeDTO))); // map coffeeDTO to Coffee entity, save it, then map back to CoffeeDTO
    }

    @Override
    public Optional<CoffeeDTO> updateCoffeeData(UUID coffeeId, CoffeeDTO coffeeDTO) {
        iCoffeeRepository.findById(coffeeId).ifPresent(coffee -> {
            coffee.setNameCoffee(coffeeDTO.getNameCoffee());
            coffee.setDescriptionCoffee(coffeeDTO.getDescriptionCoffee());
            coffee.setCoffeeStyle(coffeeDTO.getCoffeeStyle());
            coffee.setPriceCoffee(coffeeDTO.getPriceCoffee());
            coffee.setQuantityCoffee(coffeeDTO.getQuantityCoffee());
            iCoffeeRepository.save(coffee);
        });
        return Optional.ofNullable(coffeeMapper
                .coffeeToCoffeeDto(iCoffeeRepository
                        .findById(coffeeId).orElse(null)));
    }

    @Override
    public Boolean deleteCoffeeById(UUID coffeeId) {
        if(iCoffeeRepository.existsById(coffeeId)){
            iCoffeeRepository.deleteById(coffeeId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CoffeeDTO> updateCoffeePatchById(UUID coffeeId, CoffeeDTO coffeeDTO) {
        AtomicReference<Optional<CoffeeDTO>> optionalCoffeeDTO = new AtomicReference<>();

        iCoffeeRepository.findById(coffeeId).ifPresent(foundCoffee -> {
            if(StringUtils.hasText(foundCoffee.getNameCoffee())) {
                foundCoffee.setNameCoffee(coffeeDTO.getNameCoffee());
            }
            if (foundCoffee.getCoffeeStyle()!= null) {
                foundCoffee.setCoffeeStyle(coffeeDTO.getCoffeeStyle());
            }
            if (foundCoffee.getPriceCoffee()!= null) {
                foundCoffee.setPriceCoffee(coffeeDTO.getPriceCoffee());
            }
            if (StringUtils.hasText(foundCoffee.getDescriptionCoffee())) {
                foundCoffee.setDescriptionCoffee(coffeeDTO.getDescriptionCoffee());
            }
            if (foundCoffee.getQuantityCoffee()!= null) {
                foundCoffee.setQuantityCoffee(coffeeDTO.getQuantityCoffee());
            }
            optionalCoffeeDTO.set(Optional.of(coffeeMapper.coffeeToCoffeeDto(iCoffeeRepository.save(foundCoffee))));
        });
        return optionalCoffeeDTO.get();
    }
}
