package cz.itnetwork.model.dto.mapper;

import cz.itnetwork.database.entity.InvoiceEntity;
import cz.itnetwork.model.dto.InvoiceDTO;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
@Component
public class InvoiceMapperImpl implements InvoiceMapper {

    @Autowired
    private PersonMapper personMapper;

    @Override
    public InvoiceDTO toDTO(InvoiceEntity sourceInvoice) {
        if ( sourceInvoice == null ) {
            return null;
        }

        InvoiceDTO invoiceDTO = new InvoiceDTO();

        invoiceDTO.setId( sourceInvoice.getId() );
        invoiceDTO.setInvoiceNumber( sourceInvoice.getInvoiceNumber() );
        invoiceDTO.setSeller( personMapper.toDTO( sourceInvoice.getSeller() ) );
        invoiceDTO.setBuyer( personMapper.toDTO( sourceInvoice.getBuyer() ) );
        invoiceDTO.setIssued( sourceInvoice.getIssued() );
        invoiceDTO.setDueDate( sourceInvoice.getDueDate() );
        invoiceDTO.setProduct( sourceInvoice.getProduct() );
        invoiceDTO.setPrice( sourceInvoice.getPrice() );
        invoiceDTO.setVat( sourceInvoice.getVat() );
        invoiceDTO.setNote( sourceInvoice.getNote() );

        return invoiceDTO;
    }

    @Override
    public InvoiceEntity toEntity(InvoiceDTO sourceInvoice) {
        if ( sourceInvoice == null ) {
            return null;
        }

        InvoiceEntity invoiceEntity = new InvoiceEntity();

        if ( sourceInvoice.getId() != null ) {
            invoiceEntity.setId( sourceInvoice.getId() );
        }
        invoiceEntity.setInvoiceNumber( sourceInvoice.getInvoiceNumber() );
        invoiceEntity.setSeller( personMapper.toEntity( sourceInvoice.getSeller() ) );
        invoiceEntity.setBuyer( personMapper.toEntity( sourceInvoice.getBuyer() ) );
        invoiceEntity.setIssued( sourceInvoice.getIssued() );
        invoiceEntity.setDueDate( sourceInvoice.getDueDate() );
        invoiceEntity.setProduct( sourceInvoice.getProduct() );
        invoiceEntity.setPrice( sourceInvoice.getPrice() );
        invoiceEntity.setVat( sourceInvoice.getVat() );
        invoiceEntity.setNote( sourceInvoice.getNote() );

        return invoiceEntity;
    }

    @Override
    public InvoiceEntity updateEntity(InvoiceDTO sourceInvoice, InvoiceEntity targetInvoice) {
        if ( sourceInvoice == null ) {
            return targetInvoice;
        }

        if ( sourceInvoice.getId() != null ) {
            targetInvoice.setId( sourceInvoice.getId() );
        }
        targetInvoice.setInvoiceNumber( sourceInvoice.getInvoiceNumber() );
        targetInvoice.setSeller( personMapper.toEntity( sourceInvoice.getSeller() ) );
        targetInvoice.setBuyer( personMapper.toEntity( sourceInvoice.getBuyer() ) );
        targetInvoice.setIssued( sourceInvoice.getIssued() );
        targetInvoice.setDueDate( sourceInvoice.getDueDate() );
        targetInvoice.setProduct( sourceInvoice.getProduct() );
        targetInvoice.setPrice( sourceInvoice.getPrice() );
        targetInvoice.setVat( sourceInvoice.getVat() );
        targetInvoice.setNote( sourceInvoice.getNote() );

        return targetInvoice;
    }
}
