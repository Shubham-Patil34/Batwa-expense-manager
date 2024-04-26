package com.shubhampatil34.BatwaExpenseManager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {
    Long id;

    @NotNull(message = "Amount must be provided")
    @Positive(message = "Amount must be a positive numeric value")
    private Double amount;

    @Size(max = 60, message = "Description can contain at max 60 characters")
    private String description;

    @Min(1)
    @Max(3)
    private Integer type; // 1 -> income, 2 -> expense, 3 -> transfer

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "Date must be provided")
    private Date date;

    private Long batwaId;

    private Long toBatwaId;

    @AssertTrue(message = "Target a/c must be selected for transfer")
    private boolean isToBatwaIdValid() {
        return !(type != null && type == 3 && toBatwaId == null);
    }

    @AssertTrue(message = "Source a/c must be selected")
    private boolean isFromBatwaIdValid() {
        return batwaId != null;
    }

    @AssertTrue(message = "Target a/c should only be selected for transfer")
    private boolean isToBatwaIdInValid() {
        return !(type != null && type != 3 && toBatwaId != null);
    }
}
