package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RefreshTokenDTO {

    @JsonProperty("refreshToken")
    private String refreshToken;

}
