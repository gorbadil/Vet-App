package simsek.ali.VeterinaryManagementProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simsek.ali.VeterinaryManagementProject.dto.request.CustomerRequest;
import simsek.ali.VeterinaryManagementProject.dto.response.CustomerResponse;
import simsek.ali.VeterinaryManagementProject.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    public final CustomerService customerService;

    @GetMapping
    public ResponseEntity< Page<CustomerResponse> > findAllCustomers (
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAllCustomers(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findCustomerById (@PathVariable Long id){
        return  ResponseEntity.status(HttpStatus.OK).body(customerService.findCustomerById(id));
    }

    @GetMapping("/searchByName")
    public ResponseEntity< Page<CustomerResponse> >  findCustomersByName (
            @RequestParam String name,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findCustomersByName(name, pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer (@RequestBody CustomerRequest customerRequest){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.createCustomer(customerRequest)) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer (@PathVariable Long id, @RequestBody CustomerRequest customerRequest){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(id, customerRequest));
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id){
        return customerService.deleteCustomer(id);
    }

}
