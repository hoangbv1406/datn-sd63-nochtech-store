package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDTO {

    @NotBlank(message = "Product name is required.")
    @Size(min = 3, max = 200, message = "Product name must be between 3 and 200 characters.")
    @JsonProperty("name")
    private String name;

    @Min(value = 0, message = "Price must be at least 0.")
    @JsonProperty("price")
    private Float price;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category_id")
    private Long categoryId;

}
