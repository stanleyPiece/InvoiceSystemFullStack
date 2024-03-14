package cz.itnetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    @JsonProperty("_id")
    private Long id;

    @NotBlank
    private String invoiceNumber;

    @NotBlank
    private PersonDTO seller;

    @NotBlank
    private PersonDTO buyer;

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") //date format
    private LocalDate issued;

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") //date format
    private LocalDate dueDate;

    @NotBlank
    private String product;

    @NotNull
    private Long price;

    @NotBlank
    private int vat;

    private String note;

}
