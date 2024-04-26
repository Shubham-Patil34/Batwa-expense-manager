package com.shubhampatil34.BatwaExpenseManager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="TRANSACTION_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="AMOUNT", nullable = false)
    private Double amount;

    @Column(name = "DESCRIPTION", length = 60)
    private String description;

    @Column(name = "TYPE", nullable = false)
    private Integer type; // 1 -> income, 2 -> expense, 3 -> transfer

    @Column(name = "DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BATWA_ID", nullable = false)
    private Batwa batwa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_BATWA_ID")
    private Batwa toBatwa;
}