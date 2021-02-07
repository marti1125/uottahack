package com.uothackk.app.watson;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.tone_analyzer.v3.model.ToneOptions;
import com.ibm.watson.tone_analyzer.v3.model.ToneScore;
import com.uothackk.app.forum.persistance.PostEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class WatsonToneAnalyzer implements WatsonToneAnalyzerService {

    @Autowired
    WatsonToneAnalyzerSettings setting;

    @Autowired
    PostDocumentToneService postDocumentToneService;

    @Override
    public List<ToneScore> textAnalyzer(String content) {

        IamAuthenticator authenticator = new IamAuthenticator(setting.getApiKey());
        ToneAnalyzer toneAnalyzer = new ToneAnalyzer(setting.getVersion(), authenticator);
        toneAnalyzer.setServiceUrl(setting.getUrl());

        ToneOptions options = new ToneOptions.Builder().text(content).build();

        ToneAnalysis analysis = toneAnalyzer.tone(options).execute().getResult();

        log.info(analysis.toString());
        log.info(analysis.getDocumentTone().getTones().toString());

        if (analysis.getDocumentTone() != null && analysis.getDocumentTone().getTones().size() > 0) {
            return analysis.getDocumentTone().getTones();
        }

        return new ArrayList<>();

    }

    public void analyze(PostEntity postEntity, String content) {

        IamAuthenticator authenticator = new IamAuthenticator(setting.getApiKey());
        ToneAnalyzer toneAnalyzer = new ToneAnalyzer(setting.getVersion(), authenticator);
        toneAnalyzer.setServiceUrl(setting.getUrl());

        ToneOptions options = new ToneOptions.Builder().text(content).build();

        ToneAnalysis analysis = toneAnalyzer.tone(options).execute().getResult();

        if (analysis.getDocumentTone() != null && analysis.getDocumentTone().getTones().size() > 0) {
            postDocumentToneService.save(postEntity, analysis.getDocumentTone());
        }

        log.info(analysis.toString());
        log.info(analysis.getDocumentTone().getTones().toString());

    }


}
