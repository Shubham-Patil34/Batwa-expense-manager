package com.shubhampatil34.BatwaExpenseManager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "BATWA_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class Batwa {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ACCOUNT_NAME", nullable = false)
    private String name;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @Column(name = "DISPLAY_PRIORITY")
    private Integer priority;   // 1=High; 2=Medium; 3=Low

    @Column(name = "CURRENT_BALANCE", nullable = false)
    private Double currentBalance;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "batwa")
    @JsonIgnore
    private List<Transaction> transactions;
}
