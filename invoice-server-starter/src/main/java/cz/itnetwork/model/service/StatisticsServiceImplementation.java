package cz.itnetwork.model.service;

import cz.itnetwork.database.repository.InvoiceRepository;
import cz.itnetwork.database.repository.PersonRepository;
import cz.itnetwork.model.dto.InvoiceStatisticsDTO;
import cz.itnetwork.model.dto.PersonStatisticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImplementation implements StatisticsService{

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    /**
     * Method for retrieving statistics for each person in the database
     *
      * @return Statistics for all persons
     */
    @Override
    public List<PersonStatisticsDTO> getPersonStatistics() {
        // retrieves all PersonEntities from the repository and converts them to a stream
        return personRepository.findByHidden(false).stream()
                                         // maps each entity to a DTO
                                         .map(person -> new PersonStatisticsDTO(
                                         // sets the person ID in the DTO using the ID from the entity
                                         person.getId(),
                                         // sets the person name in the DTO using the name from the entity
                                         person.getName(),
                                         // calls the sumRevenue method from the invoiceRepository (calculates revenue) and passes the person ID as parameter
                                         //also, if the revenue is null (the person issued no invoices), we set it to 0
                                         invoiceRepository.sumRevenue(person.getId()) == null ? 0L : invoiceRepository.sumRevenue(person.getId())
                                         ))
                                         // collects the mapped DTOS into a list and returns
                                         .collect(Collectors.toList());

    }

    /**
     * Method for retrieving statistics for invoices
     *
     * @return invoice statistics
     */
    @Override
    public InvoiceStatisticsDTO getInvoiceStatistics() {

            InvoiceStatisticsDTO invoiceStatistics = invoiceRepository.getInvoiceStatistics();
            return invoiceStatistics;
    }

}
