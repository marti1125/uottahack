package com.uothackk.app.watson;

import com.ibm.watson.tone_analyzer.v3.model.ToneScore;
import com.uothackk.app.forum.persistance.PostEntity;

import java.util.List;

public interface WatsonToneAnalyzerService {

    List<ToneScore> textAnalyzer(String content);

    void analyze(PostEntity postEntity, String content);

}
