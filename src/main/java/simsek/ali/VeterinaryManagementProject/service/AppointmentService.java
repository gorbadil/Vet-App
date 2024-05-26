package simsek.ali.VeterinaryManagementProject.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import simsek.ali.VeterinaryManagementProject.dto.request.AppointmentRequest;
import simsek.ali.VeterinaryManagementProject.dto.response.AppointmentResponse;
import simsek.ali.VeterinaryManagementProject.entity.Appointment;
import simsek.ali.VeterinaryManagementProject.entity.WorkDay;
import simsek.ali.VeterinaryManagementProject.exception.DoctorAppointmentConflictException;
import simsek.ali.VeterinaryManagementProject.exception.DoctorNotAvailableException;
import simsek.ali.VeterinaryManagementProject.exception.EntityAlreadyExistException;
import simsek.ali.VeterinaryManagementProject.exception.EntityNotFoundException;
import simsek.ali.VeterinaryManagementProject.repository.AppointmentRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final WorkDayService workDayService;
    private final ModelMapper modelMapper;

    public Page<AppointmentResponse> findAllAppointments (int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return appointmentRepository.findAll(pageable).map(appointment ->modelMapper.map(appointment, AppointmentResponse.class) );
    }

    public AppointmentResponse findAppointmentByIdResponse(Long id){
        return modelMapper.map(appointmentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, Appointment.class))
                , AppointmentResponse.class);
    }

    public Appointment findAppointmenById(Long id){
        return appointmentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, Appointment.class));
    }

    public Page<AppointmentResponse> findAppointmentByDoctorIdAndDateRange(Long doctorId, LocalDate startDate, LocalDate endDate, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        if (doctorId==null){
            return appointmentRepository.findByAppointmentDateBetween(startDate.atStartOfDay(),endDate.atStartOfDay(), pageable).map(appointment ->modelMapper.map(appointment, AppointmentResponse.class));
        }
        return appointmentRepository.findByDoctorIdAndAppointmentDateBetween(doctorId,startDate.atStartOfDay(),endDate.atStartOfDay(), pageable).map(appointment ->modelMapper.map(appointment, AppointmentResponse.class));
    }

    public Page<AppointmentResponse> findAppointmentByAnimalIdAndDateRange(Long animalId, LocalDate startDate, LocalDate endDate, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        if (animalId==null){
            return appointmentRepository.findByAppointmentDateBetween(startDate.atStartOfDay(),endDate.atStartOfDay(), pageable).map(appointment ->modelMapper.map(appointment, AppointmentResponse.class));
        }
        return appointmentRepository.findByAnimalIdAndAppointmentDateBetween(animalId,startDate.atStartOfDay(),endDate.atStartOfDay(), pageable).map(appointment ->modelMapper.map(appointment, AppointmentResponse.class));
    }

    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest){

        Optional<Appointment> existAppointmentWithSameSpecs =
                appointmentRepository.findByAppointmentDateAndDoctorIdAndAnimalId(appointmentRequest.getAppointmentDate(), appointmentRequest.getDoctor().getId(), appointmentRequest.getAnimal().getId());

        Optional<WorkDay> existsWorkDayByDoctorIdAndDate =
                workDayService.findByDoctorIdAndDate(appointmentRequest.getDoctor().getId(), appointmentRequest.getAppointmentDate().toLocalDate());

        Optional<Appointment> existAppointmentWithDateAndDoctorId =
                appointmentRepository.findByAppointmentDateAndDoctorId(appointmentRequest.getAppointmentDate(), appointmentRequest.getDoctor().getId());

        if (existAppointmentWithSameSpecs.isPresent()){
            throw new EntityAlreadyExistException(Appointment.class);
        }

        if (existsWorkDayByDoctorIdAndDate.isEmpty()){
            throw new DoctorNotAvailableException(appointmentRequest.getAppointmentDate().toLocalDate());
        }

        if (existAppointmentWithDateAndDoctorId.isPresent()){
            throw new DoctorAppointmentConflictException(appointmentRequest.getAppointmentDate().toLocalDate());
        }

        Appointment newAppointment = modelMapper.map(appointmentRequest, Appointment.class);
        return modelMapper.map(appointmentRepository.save(newAppointment), AppointmentResponse.class);
    }

    public AppointmentResponse updateAppointment (Long id, AppointmentRequest appointmentRequest){

        Optional<Appointment> appointmentFromDb = appointmentRepository.findById(id);

        if (appointmentFromDb.isEmpty()){
            throw new EntityNotFoundException(id, Appointment.class);
        }

        Optional<Appointment> existAppointmentWithSameSpecs =
                appointmentRepository.findByAppointmentDateAndDoctorIdAndAnimalId(appointmentRequest.getAppointmentDate(), appointmentRequest.getDoctor().getId(), appointmentRequest.getAnimal().getId());

        if (existAppointmentWithSameSpecs.isPresent()){
            throw new EntityAlreadyExistException(Appointment.class);
        }

        Optional<WorkDay> existsWorkDayByDoctorIdAndDate =
                workDayService.findByDoctorIdAndDate(appointmentRequest.getDoctor().getId(), appointmentRequest.getAppointmentDate().toLocalDate());

        if (existsWorkDayByDoctorIdAndDate.isEmpty()){
            throw new DoctorNotAvailableException(appointmentRequest.getAppointmentDate().toLocalDate());
        }

        Optional<Appointment> existAppointmentWithDateAndDoctorId =
                appointmentRepository.findByAppointmentDateAndDoctorId(appointmentRequest.getAppointmentDate(), appointmentRequest.getDoctor().getId());

        if (existAppointmentWithDateAndDoctorId.isPresent()){
            throw new DoctorAppointmentConflictException(appointmentRequest.getAppointmentDate().toLocalDate());
        }

        Appointment updatedAppointment = appointmentFromDb.get();
        modelMapper.map(appointmentRequest, updatedAppointment);
        return modelMapper.map(appointmentRepository.save(updatedAppointment), AppointmentResponse.class);
    }

    public String deleteAppointment (Long id){
        Optional<Appointment> appointmentFromDb = appointmentRepository.findById(id);

        if (appointmentFromDb.isEmpty()){
            throw new EntityNotFoundException(id, Appointment.class);
        }
        else {
            appointmentRepository.delete(appointmentFromDb.get());
            return "Appointment deleted.";
        }
    }
}
