package com.uothackk.app.watson;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ibm.tone.analyzer.key")
@Getter
@Setter
public class WatsonToneAnalyzerSettings {

    private String apiKey;
    private String version;
    private String url;

}
