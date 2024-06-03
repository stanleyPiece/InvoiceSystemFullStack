package cz.itnetwork.database.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "invoice")
@Entity
@Getter
@Setter
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String invoiceNumber;

    @JoinColumn(name = "seller")
    @ManyToOne
    private PersonEntity seller;

    @JoinColumn(name = "buyer")
    @ManyToOne
    private PersonEntity buyer;

    @Column
    private LocalDate issued;

    @Column
    private LocalDate dueDate;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private int vat;

    @Column
    private String note;
}
