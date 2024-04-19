package com.shubhampatil34.BatwaExpenseManager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Min(value = 1, message = "Amount must be greater to equal to 1")
    @NotNull(message = "Amount must be provided")
    @Positive(message = "Amount must be a positive numeric value")
    private Double amount;

    @Size(max = 60, message = "Description can contain at max 60 characters")
    private String description;

    @Min(1)
    @Max(3)
    private Integer type; // 1 -> income, 2 -> expense, 3 -> transfer

    @JsonFormat(pattern = "yyyy-mm-dd")
    @NotNull(message = "Date must be provided")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "batwa_id", nullable = false, updatable = false)
    @JsonIgnore
    private Batwa batwa;
}