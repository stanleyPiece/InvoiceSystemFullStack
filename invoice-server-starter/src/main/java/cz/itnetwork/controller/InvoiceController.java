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
@RequestMapping("/api/invoices")
public class InvoiceController {


    @Autowired
    private InvoiceService invoiceService;

    /**
     * Endpoint for handling the creation of an invoice
     * Only Admin role has permission to create invoices
     *
     * @param data new invoice's data
     * @return created invoice
     */
    @Secured("ROLE_ADMIN")
    @PostMapping({"", "/"})
    public InvoiceDTO createInvoice(@RequestBody InvoiceDTO data) {
        return invoiceService.createInvoice(data);
    }

    /**
     * Endpoint for handling the retrieval of an invoice's details
     *
     * @param invoiceId ID of invoice for which to retrieve details
     * @return invoice's details
     */
    @GetMapping({"/{invoiceId}", "/{invoiceId}/"})
    public InvoiceDTO getInvoiceDetails(@PathVariable Long invoiceId) {
        return invoiceService.getInvoiceDetails(invoiceId);
    }

    /**
     * Endpoint for handling the editing of an invoice
     * Only Admin role has permission to edit invoices
     *
     * @param invoiceId ID of invoice being edited
     * @param data edited data
     * @return edited invoice
     */
    @Secured("ROLE_ADMIN")
    @PutMapping({"/{invoiceId}", "/{invoiceId}/"})
    public InvoiceDTO editInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO data) {
        return invoiceService.editInvoice(invoiceId, data);
    }

    /**
     * Endpoint for handling the deletion of an invoice
     * Only Admin role has permission to delete invoices
     *
     * @param invoiceId invoice ID
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping({"/{invoiceId}", "/{invoiceId}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
    }

    /**
     * Endpoint for fetching all the invoices
     * Uses a filter
     *
     * @param invoiceFilter filter
     * @return all invoices
     */
    @GetMapping({"", "/"})
    public List<InvoiceDTO> getInvoices(InvoiceFilter invoiceFilter) {
        return invoiceService.getAllInvoices(invoiceFilter);
    }
}
