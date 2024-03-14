package cz.itnetwork.model.service;


import cz.itnetwork.model.dto.InvoiceDTO;
import cz.itnetwork.model.dto.filter.InvoiceFilter;

import java.util.List;

public interface InvoiceService {

    /**
     * Creates a new invoice
     *
     * @param invoiceDTO invoice DTO
     * @return newly created invoice
     */
    InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);

    /**
     * Returns the details of an invoice
     *
     * @param id ID of invoice for which to get details
     * @return invoice details
     */
    InvoiceDTO getInvoiceDetails(long id);

    /**
     * Edits an invoice
     *
     * @param invoice edited invoice
     * @param Id ID of invoice to edit
     * @return saved (modified) invoice
     */
    InvoiceDTO editInvoice(InvoiceDTO invoice, long Id);

    /**
     * Deletes an invoice
     *
     * @param id ID of invoice to delete
     */
    void deleteInvoice(Long id);

    /**
     * Fetches all invoices
     *
     * @return list of all invoices
     */
    List<InvoiceDTO> getAllInvoices(InvoiceFilter invoiceFilter);
}
