package cz.itnetwork.model.service;


import cz.itnetwork.model.dto.InvoiceDTO;
import cz.itnetwork.model.dto.filter.InvoiceFilter;

import java.util.List;

public interface InvoiceService {

    /**
     * Creates a new invoice
     *
     * @param data new invoice's data
     * @return newly created invoice
     */
    InvoiceDTO createInvoice(InvoiceDTO data);

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
     * @param Id ID of invoice being edited
     * @param editedData edited data
     * @return edited invoice
     */
    InvoiceDTO editInvoice(long Id, InvoiceDTO editedData);

    /**
     * Deletes an invoice
     *
     * @param id ID of invoice to delete
     */
    void deleteInvoice(long id);

    /**
     * Fetches all invoices
     *
     * @return list of all invoices
     */
    List<InvoiceDTO> getAllInvoices(InvoiceFilter invoiceFilter);

}
