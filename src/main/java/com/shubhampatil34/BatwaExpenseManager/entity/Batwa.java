package com.shubhampatil34.BatwaExpenseManager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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
    @Size(min=2, max=30, message = "Name must have min 2 and max 30 characters")
    private String name;
    @Pattern(regexp = "^$|\\d{1,14}$", message = "Account no. can have at max 14 digits")
    private String accountNumber;
    @Size(max=100)
    private String description;
    @Min(1)
    @Max(3)
    private Integer priority;   // 1=High; 2=Medium; 3=Low
    @Digits(integer = 7, fraction = 4, message = "Balance must be a valid numeric value")
    @Pattern(regexp = "^\\d+(\\.\\d+)?$", message = "Balance must be a valid numeric value")
    private String currentBalance;
}
