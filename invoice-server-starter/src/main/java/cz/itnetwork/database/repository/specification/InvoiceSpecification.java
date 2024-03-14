package cz.itnetwork.database.repository.specification;

import cz.itnetwork.database.entity.InvoiceEntity;
import cz.itnetwork.database.entity.InvoiceEntity_;
import cz.itnetwork.database.entity.PersonEntity;
import cz.itnetwork.database.entity.PersonEntity_;
import cz.itnetwork.model.dto.filter.InvoiceFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InvoiceSpecification implements Specification<InvoiceEntity> {

    private final InvoiceFilter invoiceFilter;

    /**
     * Method used for filtering invoices
     *
     * @param root invoice entity
     * @param criteriaQuery filtering criteria
     * @param criteriaBuilder
     * @return
     */
    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (invoiceFilter.getBuyerID() != null) {
            Join<PersonEntity, InvoiceEntity> buyerJoin = root.join(InvoiceEntity_.BUYER);
            predicates.add(criteriaBuilder.equal(buyerJoin.get(PersonEntity_.ID), invoiceFilter.getBuyerID()));
        }

        if (invoiceFilter.getSellerID() != null) {
            Join<PersonEntity, InvoiceEntity> sellerJoin = root.join(InvoiceEntity_.SELLER);
            predicates.add(criteriaBuilder.equal(sellerJoin.get(PersonEntity_.ID), invoiceFilter.getSellerID()));
        }

        if (!invoiceFilter.getProduct().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get(InvoiceEntity_.PRODUCT), "%" + invoiceFilter.getProduct() + "%"));
        }

        if (invoiceFilter.getMinPrice() != null) {
            predicates.add((criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.PRICE), invoiceFilter.getMinPrice())));
        }

        if (invoiceFilter.getMaxPrice() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.PRICE), invoiceFilter.getMaxPrice()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
