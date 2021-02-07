package com.uothackk.app.watson;

import com.ibm.watson.tone_analyzer.v3.model.DocumentAnalysis;
import com.uothackk.app.forum.persistance.PostEntity;

public interface PostDocumentToneService {

    void save(PostEntity postEntity, DocumentAnalysis documentAnalysis);

}
