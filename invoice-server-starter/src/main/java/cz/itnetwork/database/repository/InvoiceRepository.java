package cz.itnetwork.database.repository;

import cz.itnetwork.database.entity.InvoiceEntity;
import cz.itnetwork.model.dto.InvoiceStatisticsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long>, JpaSpecificationExecutor<InvoiceEntity> {



    /**
     * Method used in statistics, calculates the revenue of a given person
     *
     * @param id ID
     * @return summed price of all invoices of a given person
     */
    @Query(value = "SELECT SUM(price) FROM InvoiceEntity WHERE seller.id = :personId")
    Long sumRevenue(@Param("personId") long id);

    /**
     * Method used in statistics, sums the price of all invoices in a given year, sums the price of all invoices, and counts the number of invoices
     *
     * @return
     */
    @Query(value = "SELECT NEW cz.itnetwork.model.dto.InvoiceStatisticsDTO(" +
            "SUM(CASE WHEN YEAR(issued) = YEAR(CURRENT_DATE) THEN price ELSE 0 END), " +
            "SUM(price), " +
            "COUNT(*)) " +
            "FROM InvoiceEntity")
    InvoiceStatisticsDTO getInvoiceStatistics();
}
