package io.github.eduardout.e_commerce.dto.response;

import io.github.eduardout.e_commerce.entity.PersonalData;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PersonalDataResponseMapper implements Function<PersonalData, PersonalDataResponse> {
    @Override
    public PersonalDataResponse apply(PersonalData personalData) {
        return new PersonalDataResponse(
                personalData.getFirstName(),
                personalData.getMaternalSurname(),
                personalData.getPaternalSurname(),
                personalData.getBirthDate(),
                personalData.getPhoneNumber()
        );
    }
}
