package com.uothackk.app.recaptchav3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    private Boolean success;

    @JsonProperty("error-codes")
    private String[] errorCodes;

}
