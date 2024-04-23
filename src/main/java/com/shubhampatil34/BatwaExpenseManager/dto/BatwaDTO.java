package com.shubhampatil34.BatwaExpenseManager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatwaDTO {

    private Long id;

    @NotNull(message = "Name must be provided")
    @NotBlank(message = "Name must be provided")
    @Size(min = 2, max = 30, message = "Name must have min 2 and max 30 characters")
    private String name;

    @Pattern(regexp = "^$|\\d{1,14}$", message = "Account no. can have at max 14 digits")
    private String accountNumber;

    @Size(max = 100, message = "Description can contain at max 100 characters")
    private String description;

    @Min(value = 1, message = "Priority must be selected")
    @Max(value = 3, message = "Priority must be selected")
    @NotNull(message = "Priority must be provided")
    private Integer priority;   // 1=High; 2=Medium; 3=Low

    @NotNull(message = "Balance must be provided")
    private Double currentBalance;
}
