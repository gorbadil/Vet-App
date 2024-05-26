package simsek.ali.VeterinaryManagementProject.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import simsek.ali.VeterinaryManagementProject.dto.request.DoctorRequest;
import simsek.ali.VeterinaryManagementProject.dto.response.DoctorResponse;
import simsek.ali.VeterinaryManagementProject.entity.Doctor;
import simsek.ali.VeterinaryManagementProject.exception.DuplicateDataException;
import simsek.ali.VeterinaryManagementProject.exception.EntityAlreadyExistException;
import simsek.ali.VeterinaryManagementProject.exception.EntityNotFoundException;
import simsek.ali.VeterinaryManagementProject.repository.DoctorRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public Page<DoctorResponse> findAllDoctors (int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        //TODO LİSTE BOŞSA BULUNAMADI HATASI FIRLAT
        return doctorRepository.findAll(pageable).map(doctor -> modelMapper.map(doctor, DoctorResponse.class));
    }

    public DoctorResponse findDoctorById (Long id){
        return modelMapper.map(doctorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, Doctor.class))
                , DoctorResponse.class);
    }

    public Doctor findDoctor (Long id){
        return doctorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, Doctor.class));
    }

    public DoctorResponse createDoctor(DoctorRequest doctorRequest){
        Optional<Doctor> existDoctorWithSameSpecs = doctorRepository.findByNameAndEmail(doctorRequest.getName(), doctorRequest.getEmail());

        if (existDoctorWithSameSpecs.isPresent()){
            throw new EntityAlreadyExistException(Doctor.class);
        }

        Doctor newDoctor = modelMapper.map(doctorRequest, Doctor.class);
        return modelMapper.map(doctorRepository.save(newDoctor), DoctorResponse.class);
    }

    public DoctorResponse updateDoctor (Long id, DoctorRequest doctorRequest){
        Optional<Doctor> doctorFromDb = doctorRepository.findById(id);
        Optional<Doctor> existOtherDoctorFromRequest = doctorRepository.findByNameAndEmail(doctorRequest.getName(), doctorRequest.getEmail());

        if (doctorFromDb.isEmpty()){
            throw new EntityNotFoundException(id, Doctor.class);
        }

        if (existOtherDoctorFromRequest.isPresent() && !existOtherDoctorFromRequest.get().getId().equals(id)){
            throw new DuplicateDataException(Doctor.class);
        }

        Doctor updatedDoctor = doctorFromDb.get();
        modelMapper.map(doctorRequest, updatedDoctor);
        return modelMapper.map(doctorRepository.save(updatedDoctor), DoctorResponse.class);
    }

    public String deleteDoctor (Long id){
        Optional<Doctor> doctorFromDb = doctorRepository.findById(id);
        if (doctorFromDb.isEmpty()){
            throw new EntityNotFoundException(id, Doctor.class);
        }
        else {
            doctorRepository.delete(doctorFromDb.get());
            return "Doctor deleted.";
        }
    }
}
