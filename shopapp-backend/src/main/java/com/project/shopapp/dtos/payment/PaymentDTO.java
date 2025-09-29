package com.project.shopapp.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PaymentDTO {

    @JsonProperty("amount")
    private Long amount;

    @JsonProperty("bankCode")
    private String bankCode;

    @JsonProperty("language")
    private String language;

}
