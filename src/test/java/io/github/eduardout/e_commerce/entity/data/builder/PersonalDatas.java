package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.PersonalData;

import java.time.LocalDate;

public class PersonalDatas {
    public static PersonalData.PersonalDataBuilder aPersonalData() {
        return PersonalData.aPersonalData()
                .withFirstName("John")
                .withPaternalSurname("Doe")
                .withMaternalSurname("Dow")
                .withBirthDate(LocalDate.of(1982, 3, 22))
                .withPhoneNumber("55-45465461");
    }
}
