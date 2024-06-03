package cz.itnetwork.model.service;

import cz.itnetwork.model.dto.InvoiceStatisticsDTO;
import cz.itnetwork.model.dto.PersonStatisticsDTO;

import java.util.List;


public interface StatisticsService {

    /**
     * Gets statistics for each person in the database
     *
     * @return Person statistics
     */
    List<PersonStatisticsDTO> getPersonStatistics();

    /**
     * Gets statistics for all invoices
     *
     * @return Invoice statistics
     */
    InvoiceStatisticsDTO getInvoiceStatistics();

}
