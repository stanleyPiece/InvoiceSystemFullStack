package cz.itnetwork.model.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceStatisticsDTO {

    @NotBlank
    private Long currentYearSum;

    @NotBlank
    private Long allTimeSum;

    @NotBlank
    private Long invoicesCount;
}
