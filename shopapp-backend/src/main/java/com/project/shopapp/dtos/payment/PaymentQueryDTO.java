package com.project.shopapp.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PaymentQueryDTO {

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("trans_date")
    private String transDate;

    @JsonProperty("ip_address")
    private String ipAddress;

}
