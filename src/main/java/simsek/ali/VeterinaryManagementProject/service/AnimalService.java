package simsek.ali.VeterinaryManagementProject.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import simsek.ali.VeterinaryManagementProject.dto.request.AnimalRequest;
import simsek.ali.VeterinaryManagementProject.dto.response.AnimalResponse;
import simsek.ali.VeterinaryManagementProject.dto.response.CustomerResponse;
import simsek.ali.VeterinaryManagementProject.entity.Animal;
import simsek.ali.VeterinaryManagementProject.entity.Customer;
import simsek.ali.VeterinaryManagementProject.exception.DuplicateDataException;
import simsek.ali.VeterinaryManagementProject.exception.EntityAlreadyExistException;
import simsek.ali.VeterinaryManagementProject.exception.EntityNotFoundException;
import simsek.ali.VeterinaryManagementProject.repository.AnimalRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final ModelMapper modelMapper;
    private final CustomerService customerService;

    public Page<AnimalResponse> findAllAnimals (int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return animalRepository.findAll(pageable).map(animal ->modelMapper.map(animal, AnimalResponse.class) );
    }

    public AnimalResponse findAnimalById (Long id){
        return modelMapper.map(animalRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, Animal.class))
                , AnimalResponse.class);
    }

    public Page<AnimalResponse> findAnimalsByName(String name, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return animalRepository.findByNameContaining(name, pageable).map(animal ->modelMapper.map(animal, AnimalResponse.class));
    }

    public Page<AnimalResponse> findAnimalsByCustomer(String customerName, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return animalRepository.findByCustomerName(customerName, pageable).map(animal ->modelMapper.map(animal, AnimalResponse.class));
    }

    public AnimalResponse createAnimal(AnimalRequest animalRequest){

        Optional<CustomerResponse> customerFromDb = Optional.ofNullable(customerService.findCustomerById(animalRequest.getCustomer().getId()));

        Optional<Animal> existAnimalWithSameSpecs = animalRepository.findByNameAndSpeciesAndGenderAndDateOfBirth(animalRequest.getName(), animalRequest.getSpecies(), animalRequest.getGender(), animalRequest.getDateOfBirth());

        if (existAnimalWithSameSpecs.isPresent()){
            throw new EntityAlreadyExistException(Animal.class);
        }
        Animal newAnimal=null;
        if(customerFromDb.isPresent()){
            newAnimal = modelMapper.map(animalRequest, Animal.class);
        }

        return modelMapper.map(animalRepository.save(newAnimal), AnimalResponse.class);
    }

    public AnimalResponse updateAnimal (Long id, AnimalRequest animalRequest){
        Optional<Animal> animalFromDb = animalRepository.findById(id);
        Optional<Animal> existOtherAnimalFromRequest = animalRepository.findByNameAndSpeciesAndGenderAndDateOfBirth(animalRequest.getName(), animalRequest.getSpecies(), animalRequest.getGender(), animalRequest.getDateOfBirth());

        if (animalFromDb.isEmpty()){
            throw new EntityNotFoundException(id, Animal.class);
        }

        if (existOtherAnimalFromRequest.isPresent() && !existOtherAnimalFromRequest.get().getId().equals(id)){
            throw new DuplicateDataException(Animal.class);
        }

        Animal updatedAnimal = animalFromDb.get();
        modelMapper.map(animalRequest, updatedAnimal);
        return modelMapper.map(animalRepository.save(updatedAnimal), AnimalResponse.class);
    }

    public String deleteAnimal (Long id){
        Optional<Animal> animalFromDb = animalRepository.findById(id);
        if (animalFromDb.isEmpty()){
            throw new EntityNotFoundException(id, Animal.class);
        }
        else {
            animalRepository.delete(animalFromDb.get());
            return "Animal deleted.";
        }
    }

}
