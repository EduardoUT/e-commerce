package io.github.eduardout.e_commerce.dto.response;

import java.time.LocalDate;

public record PersonalDataResponse(String firstName, String maternalSurname,
                                   String paternalSurname, LocalDate birthDate, String phoneNumber) {
}
