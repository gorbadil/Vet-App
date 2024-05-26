package simsek.ali.VeterinaryManagementProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Table(name = "animal")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "species")
    private String species;

    @Column(name = "breed")
    private String breed;

    @Column(name = "gender")
    private String gender;

    @Column(name = "colour")
    private String colour;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "Customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "animal",  cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Vaccination> vaccinations;

    @OneToMany(mappedBy = "animal")
    @JsonIgnore
    List<Appointment> appointments;

    


}
