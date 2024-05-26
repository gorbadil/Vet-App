package simsek.ali.VeterinaryManagementProject.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import simsek.ali.VeterinaryManagementProject.dto.request.CustomerRequest;
import simsek.ali.VeterinaryManagementProject.dto.response.CustomerResponse;
import simsek.ali.VeterinaryManagementProject.entity.Customer;
import simsek.ali.VeterinaryManagementProject.exception.DuplicateDataException;
import simsek.ali.VeterinaryManagementProject.exception.EntityAlreadyExistException;
import simsek.ali.VeterinaryManagementProject.exception.EntityNotFoundException;
import simsek.ali.VeterinaryManagementProject.repository.CustomerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public Page<CustomerResponse> findAllCustomers (int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        //TODO LİSTE BOŞSA BULUNAMADI HATASI FIRLAT
        return customerRepository.findAll(pageable).map(customer -> modelMapper.map(customer, CustomerResponse.class));
    }

    public CustomerResponse findCustomerById (Long id){
        return modelMapper.map(customerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, Customer.class))
                , CustomerResponse.class);
    }

    public Page<CustomerResponse>  findCustomersByName(String name, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        //TODO LİSTE BOŞSA BULUNAMADI HATASI FIRLAT
        return customerRepository.findByNameContaining(name,pageable).map(customer -> modelMapper.map(customer, CustomerResponse.class));
    }

    public CustomerResponse createCustomer(CustomerRequest customerRequest){
        Optional<Customer> existCustomerWithSameSpecs = customerRepository.findByNameAndEmail(customerRequest.getName(), customerRequest.getEmail());

        if (existCustomerWithSameSpecs.isPresent()){
            throw new EntityAlreadyExistException(Customer.class);
        }
        Customer newCustomer = modelMapper.map(customerRequest, Customer.class);
        return modelMapper.map(customerRepository.save(newCustomer), CustomerResponse.class);
    }

    public CustomerResponse updateCustomer (Long id, CustomerRequest customerRequest){
        Optional<Customer> customerFromDb = customerRepository.findById(id);
        Optional<Customer> existOtherCustomerFromRequest = customerRepository.findByNameAndEmail(customerRequest.getName(), customerRequest.getEmail());

        if (customerFromDb.isEmpty()){
            throw new EntityNotFoundException(id, Customer.class);
        }

        if (existOtherCustomerFromRequest.isPresent() && !existOtherCustomerFromRequest.get().getId().equals(id)){
            throw new DuplicateDataException (Customer.class);
        }

        Customer updatedCustomer = customerFromDb.get();
        modelMapper.map(customerRequest, updatedCustomer);
        return modelMapper.map(customerRepository.save(updatedCustomer), CustomerResponse.class);
    }


    public String deleteCustomer (Long id){
        Optional<Customer> customerFromDb = customerRepository.findById(id);
        if (customerFromDb.isEmpty()){
            throw new EntityNotFoundException(id, Customer.class);
        }
        else {
            customerRepository.delete(customerFromDb.get());
            return "Customer deleted.";
        }
    }

}
