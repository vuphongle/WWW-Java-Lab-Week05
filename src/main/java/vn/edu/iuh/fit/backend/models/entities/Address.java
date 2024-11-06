package vn.edu.iuh.fit.backend.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.backend.enums.CountryCode;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String city;

    @Convert(converter = vn.edu.iuh.fit.backend.converters.CountryCodeConverter.class)
    private CountryCode country;

    private String number;

    private String zipcode;
}
