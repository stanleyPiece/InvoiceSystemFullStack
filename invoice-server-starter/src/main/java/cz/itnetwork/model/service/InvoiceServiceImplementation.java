package cz.itnetwork.model.service;

import cz.itnetwork.database.entity.InvoiceEntity;
import cz.itnetwork.database.repository.InvoiceRepository;
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

    @Autowired
    private PersonService personService;

    @Autowired //mapper injection
    private InvoiceMapper invoiceMapper;

    @Autowired //repository injection
    private InvoiceRepository invoiceRepository;

    /**
     * Method for creating an invoice
     *
     * @param data new invoice's data
     * @return newly created invoice
     */
    @Override
    public InvoiceDTO createInvoice(InvoiceDTO data) {

        InvoiceEntity newInvoice = invoiceMapper.toEntity(data);

        setBuyerAndSeller(data, newInvoice);

        InvoiceEntity createdInvoice = invoiceRepository.save(newInvoice);
        return invoiceMapper.toDTO(createdInvoice);

    }

    /**
     * Method for retrieving an invoice's details
     *
     * @param invoiceId ID of the invoice for which to retrieve details
     * @return invoice's details
     */
    @Override
    public InvoiceDTO getInvoiceDetails(long invoiceId) {

        InvoiceEntity fetchedInvoice = fetchInvoiceById(invoiceId);

        return invoiceMapper.toDTO(fetchedInvoice);

    }

    /**
     * Method for editing an invoice
     *
     * @param invoiceID ID of the invoice being edited
     * @param editedData edited data
     * @return edited invoice
     */
    @Override
    public InvoiceDTO editInvoice(long invoiceID, InvoiceDTO editedData) {

        editedData.setId(invoiceID);
        InvoiceEntity invoiceToEdit = fetchInvoiceById(invoiceID);

        invoiceMapper.updateEntity(editedData, invoiceToEdit);
        setBuyerAndSeller(editedData, invoiceToEdit);

        InvoiceEntity editedInvoice = invoiceRepository.save(invoiceToEdit);
        return invoiceMapper.toDTO(editedInvoice);

    }

    //region private method for setting buyer and seller
    /**
     * Method for setting the invoice's buyer and seller
     * It is necessary to manually set the buyer and seller objects because we only receive their IDs
     *
     * @param sourceDTO
     * @param targetEntity
     */
    private void setBuyerAndSeller(InvoiceDTO sourceDTO, InvoiceEntity targetEntity) {

        long buyerId = sourceDTO.getBuyer().getId();
        long sellerId = sourceDTO.getSeller().getId();

        targetEntity.setBuyer(personService.fetchPersonById(buyerId));
        targetEntity.setSeller(personService.fetchPersonById(sellerId));

    }
    //endregion

    /**
     * Method for deleting an invoice
     *
     * @param invoiceId ID of the invoice to delete
     */
    @Override
    public void deleteInvoice(long invoiceId) {

        invoiceRepository.delete(fetchInvoiceById(invoiceId));

    }

    /**
     * Method for fetching an invoice
     *
     * @param invoiceId ID of the invoice to fetch
     * @return Fetched invoice
     * @throws EntityNotFoundException In case an invoice with given ID does not exist
     */
    private InvoiceEntity fetchInvoiceById(long invoiceId) {

        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new EntityNotFoundException("Invoice with ID " + invoiceId + " was not found in the database."));

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
