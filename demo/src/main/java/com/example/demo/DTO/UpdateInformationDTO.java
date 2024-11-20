package com.example.demo.DTO;

import com.example.demo.Model.Enum.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class UpdateInformationDTO {
    private String email;
    private String fullName;
    private String phoneNumber;
    private Gender gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateInformationDTO that = (UpdateInformationDTO) o;
        return Objects.equals(email, that.email) && Objects.equals(fullName, that.fullName) && Objects.equals(phoneNumber, that.phoneNumber) && gender == that.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, fullName, phoneNumber, gender);
    }
}
