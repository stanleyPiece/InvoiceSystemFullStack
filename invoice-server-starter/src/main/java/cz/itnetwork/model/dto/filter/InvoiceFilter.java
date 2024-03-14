package cz.itnetwork.model.dto.filter;

import lombok.Data;

@Data
public class InvoiceFilter {

    private Integer buyerID;
    private Integer sellerID;
    private String product = "";
    private Integer minPrice;
    private Integer maxPrice;
    private Integer limit = 10;

}
