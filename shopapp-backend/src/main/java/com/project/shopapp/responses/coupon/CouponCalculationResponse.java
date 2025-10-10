package com.project.shopapp.responses.coupon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CouponCalculationResponse {

    @JsonProperty("result")
    private Double result;

}
