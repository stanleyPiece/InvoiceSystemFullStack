package cz.itnetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.itnetwork.constant.Countries;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    @JsonProperty("_id")
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String identificationNumber;

    @NotBlank
    private String taxNumber;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String bankCode;

    @NotBlank
    private String iban;

    @NotBlank
    private String telephone;

    @NotBlank
    private String mail;

    @NotBlank
    private String street;

    @NotBlank
    private String zip;

    @NotBlank
    private String city;


    private Countries country;

    @NotBlank
    private String note;
}
