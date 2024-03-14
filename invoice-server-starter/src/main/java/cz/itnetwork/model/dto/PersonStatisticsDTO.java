package cz.itnetwork.model.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonStatisticsDTO {

    @NotBlank
    private Long personId;

    @NotBlank
    private String personName;

    @NotBlank
    private Long revenue;
}
