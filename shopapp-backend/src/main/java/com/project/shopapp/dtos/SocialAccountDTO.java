package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.SuperBuilder;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public abstract class SocialAccountDTO {

    @JsonProperty("facebook_account_id")
    protected String facebookAccountId;

    @JsonProperty("google_account_id")
    protected String googleAccountId;

    public boolean isFacebookAccountIdValid() {
        return facebookAccountId != null && !facebookAccountId.isEmpty();
    }

    public boolean isGoogleAccountIdValid() {
        return googleAccountId != null && !googleAccountId.isEmpty();
    }

    public boolean isSocialLogin() {
        return isFacebookAccountIdValid() || isGoogleAccountIdValid();
    }

}
