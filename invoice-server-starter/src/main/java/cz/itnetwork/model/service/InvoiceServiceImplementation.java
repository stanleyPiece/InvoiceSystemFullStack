package cz.itnetwork.model.service;

import cz.itnetwork.database.entity.InvoiceEntity;
import cz.itnetwork.database.entity.PersonEntity;
import cz.itnetwork.database.repository.InvoiceRepository;
import cz.itnetwork.database.repository.PersonRepository;
import cz.itnetwork.database.repository.specification.InvoiceSpecification;
import cz.itnetwork.model.dto.InvoiceDTO;
import cz.itnetwork.model.dto.filter.InvoiceFilter;
import cz.itnetwork.model.dto.mapper.InvoiceMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImplementation implements InvoiceService{

    @Autowired //repository injection
    private PersonRepository personRepository;

    @Autowired //mapper injection
    private InvoiceMapper invoiceMapper;

    @Autowired //repository injection
    private InvoiceRepository invoiceRepository;

    /**
     * Method for creating an invoice
     *
     * @param invoiceDTO invoice DTO
     * @return newly created invoice
     */
    @Override
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity newInvoice = invoiceMapper.toEntity(invoiceDTO);

        newInvoice.setBuyer(getBuyer(invoiceDTO));
        newInvoice.setSeller(getSeller(invoiceDTO));

        InvoiceEntity createdInvoice = invoiceRepository.save(newInvoice);
        return invoiceMapper.toDTO(createdInvoice);

    }

    /**
     * Method for editing an invoice
     *
     * @param editedInvoiceDTO edited invoice
     * @param invoiceID invoice ID
     * @return modified invoice
     */
    @Override
    public InvoiceDTO editInvoice(InvoiceDTO editedInvoiceDTO, long invoiceID) {
            editedInvoiceDTO.setId(invoiceID);
            InvoiceEntity modifiedInvoice = fetchInvoiceById(invoiceID);

            invoiceMapper.updateEntity(editedInvoiceDTO, modifiedInvoice);

            modifiedInvoice.setBuyer(getBuyer(editedInvoiceDTO));
            modifiedInvoice.setSeller(getSeller(editedInvoiceDTO));

            InvoiceEntity savedInvoice = invoiceRepository.save(modifiedInvoice);
            return invoiceMapper.toDTO(savedInvoice);
    }

    //region private methods for obtaining buyer and seller

    /**
     * Method for fetching the invoice's buyer
     *
     * @param invoiceDTO invoice DTO
     * @throws EntityNotFoundException In case the buyer does not exist
     * @return buyer
     */
    private PersonEntity getBuyer(InvoiceDTO invoiceDTO) {
        return personRepository.findById(invoiceDTO.getBuyer().getId())
                .orElseThrow(() -> new EntityNotFoundException("Odběratel s ID: " + invoiceDTO.getBuyer().getId() + " nebyl nalezen."));
    }

    /**
     * Method for fetching the invoice's seller
     *
     * @param invoiceDTO invoice DTO
     * @throws EntityNotFoundException In case the seller does not exist
     * @return seller
     */
    private PersonEntity getSeller(InvoiceDTO invoiceDTO) {
        return personRepository.findById(invoiceDTO.getSeller().getId())
                .orElseThrow(() -> new EntityNotFoundException("Dodavatel s ID: " + invoiceDTO.getBuyer().getId() + " nebyl nalezen."));
    }

    //endregion

    /**
     * Method for retrieving an invoice's details
     *
     * @param invoiceId invoice ID
     * @return invoice's details
     */
    @Override
    public InvoiceDTO getInvoiceDetails(long invoiceId) {
        InvoiceEntity fetchedInvoice = fetchInvoiceById(invoiceId);

        return invoiceMapper.toDTO(fetchedInvoice);

    }

    /**
     * Method for fetching an invoice
     *
     * @param invoiceId Invoice to fetch
     * @return Fetched invoice
     * @throws EntityNotFoundException In case an invoice with given ID does not exist
     */
    private InvoiceEntity fetchInvoiceById(long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new EntityNotFoundException("Faktura s ID " + invoiceId + " nebyla v databázi nalezena."));
    }

    /**
     * Method for deleting an invoice
     *
     * @param invoiceId invoice ID
     */
    @Override
    public void deleteInvoice(Long invoiceId) {
        invoiceRepository.delete(fetchInvoiceById(invoiceId));
    }

    /**
     * Fetches all invoices
     * Uses a filter
     *
     * @return all invoices
     */
    @Override
    public List<InvoiceDTO> getAllInvoices(InvoiceFilter invoiceFilter) {
        InvoiceSpecification invoiceSpecification = new InvoiceSpecification(invoiceFilter);

        return invoiceRepository.findAll(invoiceSpecification, PageRequest.of(0, invoiceFilter.getLimit()))
                .stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }
}
