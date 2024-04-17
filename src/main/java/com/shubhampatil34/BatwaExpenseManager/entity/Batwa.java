package com.shubhampatil34.BatwaExpenseManager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Batwa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be Null")
    @NotBlank(message = "Name cannot be Blank")
    @Size(min = 2, max = 30, message = "Name must have min 2 and max 30 characters")
    private String name;

    @Pattern(regexp = "^$|\\d{1,14}$", message = "Account no. can have at max 14 digits")
    private String accountNumber;

    @Size(max = 100)
    private String description;

    @Min(1)
    @Max(3)
    @NotNull(message = "Priority must be provided")
    private Integer priority;   // 1=High; 2=Medium; 3=Low

    @NotNull(message = "Balance must be provided")
    private Double currentBalance;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "batwa", orphanRemoval = true)
    @JsonIgnore
    private List<Transaction> transactions;
}
