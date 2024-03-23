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
    @NotNull(message = "FName cannot be Null")
    @NotEmpty(message = "Name cannot be Empty")
    @Size(min=2, max=30)
    private String name;
    @Size(min=2, max=30)
    private String accountNumber;
    @Size(max=100)
    private String description;
    @Min(1)
    @Max(3)
    private Integer priority;   // 1=High; 2=Medium; 3=Low
    private Double currentBalance;
    @PrePersist
    public void setBalance(){
        this.currentBalance = Double.valueOf(0);
    }
}
