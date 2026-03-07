package io.github.eduardout.e_commerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "aPersonalData", setterPrefix = "with")
public class PersonalData {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "maternal_surname", nullable = false)
    private String maternalSurname;
    @Column(name = "paternal_surname", nullable = false)
    private String paternalSurname;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
}
