package cz.itnetwork.controller;

import cz.itnetwork.model.dto.InvoiceDTO;
import cz.itnetwork.model.dto.filter.InvoiceFilter;
import cz.itnetwork.model.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
 @RequestMapping("/api")
public class InvoiceController {


    @Autowired
    private InvoiceService invoiceService;

    /**
     * Endpoint for fetching all the invoices
     * Uses a filter
     *
     * @param invoiceFilter filter
     * @return all invoices
     */
    @GetMapping({"/invoices", "/invoices/"})
    public List<InvoiceDTO> getInvoices(InvoiceFilter invoiceFilter) {
        return invoiceService.getAllInvoices(invoiceFilter);
    }

    /**
     * Endpoint for handling the creation of an invoice
     * Only Admin role has permission to create invoices
     *
     * @param invoiceDTO invoice DTO
     * @return created invoice
     */
    @Secured("ROLE_ADMIN")
    @PostMapping({"/invoices", "/invoices/"})
    public InvoiceDTO createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.createInvoice(invoiceDTO);
    }

    /**
     * Endpoint for handling the retrieval of an invoice's details
     *
     * @param invoiceId invoice ID
     * @return invoice's details
     */
    @GetMapping({"/invoices/{invoiceId}", "/invoices/{invoiceId}/"})
    public InvoiceDTO getInvoiceDetails(@PathVariable Long invoiceId) {
        return invoiceService.getInvoiceDetails(invoiceId);
    }

    /**
     * Endpoint for handling the editing of an invoice
     * Only Admin role has permission to edit invoices
     *
     * @param invoiceDTO invoice DTO
     * @param invoiceId invoice ID
     * @return edited invoice
     */
    @Secured("ROLE_ADMIN")
    @PutMapping({"/invoices/{invoiceId}", "/invoices/{invoiceId}/"})
    public InvoiceDTO editInvoice(@RequestBody InvoiceDTO invoiceDTO, @PathVariable Long invoiceId) {
        return invoiceService.editInvoice(invoiceDTO, invoiceId);
    }

    /**
     * Endpoint for handling the deletion of an invoice
     * Only Admin role has permission to delete invoices
     *
     * @param invoiceId invoice ID
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping({"/invoices/{invoiceId}", "/invoices/{invoiceId}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable Long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
    }
}
