package vn.edu.iuh.fit.backend.models.dto;

import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.backend.enums.CountryCode;

import java.time.LocalDate;

@Getter
@Setter
public class CandidateForm {

    // Thông tin ứng viên
    private String email;
    private String fullName;
    private String phone;
    private LocalDate dob;

    // Thông tin địa chỉ
    private String street;
    private String city;
    private CountryCode country;
    private String number;
    private String zipcode;
}
