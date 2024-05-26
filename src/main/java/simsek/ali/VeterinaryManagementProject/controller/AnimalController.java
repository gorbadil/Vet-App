package simsek.ali.VeterinaryManagementProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simsek.ali.VeterinaryManagementProject.dto.request.AnimalRequest;
import simsek.ali.VeterinaryManagementProject.dto.response.AnimalResponse;
import simsek.ali.VeterinaryManagementProject.service.AnimalService;

@RestController
@RequestMapping("/api/v1/animals")
@RequiredArgsConstructor
public class AnimalController {

    public final AnimalService animalService;

    @GetMapping
    public ResponseEntity< Page<AnimalResponse> > findAllAnimals(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
        ){
        return ResponseEntity.status(HttpStatus.OK).body(animalService.findAllAnimals(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponse> findAnimalById (@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(animalService.findAnimalById(id));
    }

    @GetMapping("/searchByName")
    public ResponseEntity<Page<AnimalResponse>> findAnimalsByName (
            @RequestParam String name,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
            ){
        return ResponseEntity.status(HttpStatus.OK).body(animalService.findAnimalsByName(name, pageNumber, pageSize));
    }

    @GetMapping("/searchByCustomer")
    public ResponseEntity<Page<AnimalResponse>> findAnimalsByCustomer (
            @RequestParam String customerName,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
            ){

        return ResponseEntity.status(HttpStatus.OK).body(animalService.findAnimalsByCustomer(customerName, pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<AnimalResponse> createAnimal (@RequestBody AnimalRequest animalRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.createAnimal(animalRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalResponse> updateAnimal (@PathVariable Long id, @RequestBody AnimalRequest animalRequest){

        return ResponseEntity.status(HttpStatus.OK).body(animalService.updateAnimal(id, animalRequest));

    }

    @DeleteMapping("/{id}")
    public String deleteAnimal(@PathVariable Long id){
        return animalService.deleteAnimal(id);
    }

}
