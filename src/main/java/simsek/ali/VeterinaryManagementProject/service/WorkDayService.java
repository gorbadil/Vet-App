package simsek.ali.VeterinaryManagementProject.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import simsek.ali.VeterinaryManagementProject.dto.request.WorkDayRequest;
import simsek.ali.VeterinaryManagementProject.dto.response.WorkDayResponse;
import simsek.ali.VeterinaryManagementProject.entity.Doctor;
import simsek.ali.VeterinaryManagementProject.entity.WorkDay;
import simsek.ali.VeterinaryManagementProject.exception.EntityAlreadyExistException;
import simsek.ali.VeterinaryManagementProject.exception.EntityNotFoundException;
import simsek.ali.VeterinaryManagementProject.repository.WorkDayRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkDayService {

    private final WorkDayRepository workDayRepository;
    private final ModelMapper modelMapper;
    private final DoctorService doctorService;

    public Page<WorkDayResponse> findAllWorkDays (int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        //TODO LİSTE BOŞSA BULUNAMADI HATASI FIRLAT
        return workDayRepository.findAll(pageable).map(workDay -> modelMapper.map(workDay, WorkDayResponse.class));
    }

    public WorkDayResponse findWorkDayById (Long id){
        return modelMapper.map(workDayRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, WorkDay.class))
                , WorkDayResponse.class);
    }

    public WorkDayResponse createWorkDay(WorkDayRequest workDayRequest){
        Doctor doctorFromDb = doctorService.findDoctor(workDayRequest.getDoctorId());

        Optional<WorkDay> existWorkDayWithSameSpecs
                = workDayRepository.findByWorkDayAndDoctor_Id(workDayRequest.getWorkDate(), workDayRequest.getDoctorId());

        if (existWorkDayWithSameSpecs.isPresent()){
            throw new EntityAlreadyExistException(WorkDay.class);
        }

        workDayRequest.setDoctorId(null);
        WorkDay newWorkDay = modelMapper.map(workDayRequest, WorkDay.class);
        newWorkDay.setDoctor(doctorFromDb);
        return modelMapper.map(workDayRepository.save(newWorkDay), WorkDayResponse.class);
    }

    public WorkDayResponse updateWorkDay(Long id, WorkDayRequest workDayRequest){
        Doctor doctorFromDb = doctorService.findDoctor(workDayRequest.getDoctorId());

        Optional<WorkDay> workDayFromDb = workDayRepository.findById(id);
        Optional<WorkDay> existOtherWorkDayFromRequest
                = workDayRepository.findByWorkDayAndDoctor_Id(workDayRequest.getWorkDate(), workDayRequest.getDoctorId());

        if (workDayFromDb.isEmpty()){
            throw new EntityNotFoundException(id, WorkDay.class);
        }

        if (existOtherWorkDayFromRequest.isPresent() && !existOtherWorkDayFromRequest.get().getId().equals(id)){
            throw new EntityAlreadyExistException(WorkDay.class);
        }

        WorkDay updatedWorkDay = workDayFromDb.get();
        workDayRequest.setDoctorId(null);
        modelMapper.map(workDayRequest, updatedWorkDay);
        updatedWorkDay.setDoctor(doctorFromDb);
        return modelMapper.map(workDayRepository.save(updatedWorkDay), WorkDayResponse.class);
    }

    public String deleteWorkDay(Long id){
        Optional<WorkDay> workDayFromDb = workDayRepository.findById(id);

        if (workDayFromDb.isEmpty()){
            throw new EntityNotFoundException(id, WorkDay.class);
        }
        else {
            workDayRepository.delete(workDayFromDb.get());
            return "Work day deleted.";
        }
    }

    public Optional<WorkDay> findByDoctorIdAndDate(Long id, LocalDate workDay) {
        return workDayRepository.findByWorkDayAndDoctor_Id(workDay, id);
    }
}
