package com.shubhampatil34.BatwaExpenseManager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(1)
    @NotNull(message = "Transaction amount can't be null")
    private Double amount;

    private String description;

    @Min(1)
    @Max(3)
    private int type; // 1 -> income, 2 -> expense, 3 -> transfer

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "batwa_id", nullable = false, updatable = false)
    @JsonIgnore
    private Batwa batwa;
}