package com.project.shopapp.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PaymentRefundDTO {

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("amount")
    private Long amount;

    @JsonProperty("transaction_date")
    private String transactionDate;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("ip_address")
    private String ipAddress;

}
