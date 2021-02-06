package com.uothackk.app.recaptchav3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Component
public class ReCaptchaV3 {

    @Autowired
    ReCaptchaV3Settings reCaptchaV3Settings;

    public Boolean verify(String token) {

        RestTemplate restTemplate = new RestTemplate();

        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s",
                reCaptchaV3Settings.getSecret(), token));

        Response response = restTemplate.getForObject(verifyUri, Response.class);

        if (response.getErrorCodes() != null && response.getErrorCodes().length > 0) {
            for (String f : response.getErrorCodes()) {
                log.info(f);
            }
        }

        return response.getSuccess();

    }

}
