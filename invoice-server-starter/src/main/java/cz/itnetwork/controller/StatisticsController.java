package cz.itnetwork.controller;

import cz.itnetwork.model.dto.InvoiceStatisticsDTO;
import cz.itnetwork.model.dto.PersonStatisticsDTO;
import cz.itnetwork.model.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * Endpoint for handling the retrieval of person statistics
     *
     * @return statistics for all persons in the database
     */
    @GetMapping({"/persons/statistics", "/persons/statistics/"})
    public List<PersonStatisticsDTO> getPersonStatistics() {
        return statisticsService.getPersonStatistics();
    }

    /**
     * Endpoint for handling the retrieval of invoice statistics
     *
     * @return statistics for all invoices in the database
     */
    @GetMapping({"/invoices/statistics", "/invoices/statistics/"})
    public InvoiceStatisticsDTO getInvoiceStatistics() {
        return statisticsService.getInvoiceStatistics();
    }
}
