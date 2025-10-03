package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDetailDTO {

    @Min(value = 1, message = "Order ID must be at least 1.")
    @JsonProperty("order_id")
    private Long orderId;

    @Min(value = 1, message = "Product ID must be at least 1.")
    @JsonProperty("product_id")
    private Long productId;

    @Min(value = 0, message = "Price must be at least 0.0.")
    @JsonProperty("price")
    private Float price;

    @Min(value = 1, message = "Number of products must be at least 1.")
    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @Min(value = 0, message = "Total money must be at least 0.")
    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("color")
    private String color;

}
