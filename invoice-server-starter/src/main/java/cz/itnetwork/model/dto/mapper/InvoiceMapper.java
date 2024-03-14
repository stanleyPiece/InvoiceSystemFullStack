package cz.itnetwork.model.dto.mapper;

import cz.itnetwork.database.entity.InvoiceEntity;
import cz.itnetwork.model.dto.InvoiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface InvoiceMapper {

    /**
     * Method for converting an invoice entity to an invoice DTO
     *
     * @param sourceInvoice source invoice
     * @return invoice DTO
     */
    InvoiceDTO toDTO(InvoiceEntity sourceInvoice);

    /**
     * Method for converting an invoice DTO to an invoice entity
     *
     * @param sourceInvoice source invoice
     * @return invoice entity
     */
    InvoiceEntity toEntity(InvoiceDTO sourceInvoice);

    /**
     * Method for updating an invoice entity in the database
     *
     * @param sourceInvoice source invoice
     * @param targetInvoice target invoice
     * @return updated entity
     */
    InvoiceEntity updateEntity(InvoiceDTO sourceInvoice, @MappingTarget InvoiceEntity targetInvoice);
}
