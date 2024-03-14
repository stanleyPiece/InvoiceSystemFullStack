package cz.itnetwork.model.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InvoiceStatisticsDTO {

    @NotBlank
    private Long currentYearSum;

    @NotBlank
    private Long allTimeSum;

    @NotBlank
    private Long invoicesCount;

    public InvoiceStatisticsDTO(Long currentYearSum, Long allTimeSum, Long invoicesCount) {
        this.currentYearSum = currentYearSum;
        this.allTimeSum = allTimeSum;
        this.invoicesCount = invoicesCount;
    }
}
