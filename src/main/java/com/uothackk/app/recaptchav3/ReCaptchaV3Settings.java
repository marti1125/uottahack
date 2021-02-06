package com.uothackk.app.recaptchav3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
@Getter
@Setter
public class ReCaptchaV3Settings {

    private String secret;
    private String site;

}
