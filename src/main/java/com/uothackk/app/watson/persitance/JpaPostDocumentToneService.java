package com.uothackk.app.watson.persitance;

import com.ibm.watson.tone_analyzer.v3.model.DocumentAnalysis;
import com.ibm.watson.tone_analyzer.v3.model.ToneScore;
import com.uothackk.app.forum.persistance.PostEntity;
import com.uothackk.app.watson.PostDocumentToneService;
import org.springframework.stereotype.Component;

@Component
public class JpaPostDocumentToneService implements PostDocumentToneService {

    private final PostDocumentToneRepository postDocumentToneRepository;

    public JpaPostDocumentToneService(PostDocumentToneRepository postDocumentToneRepository) {
        this.postDocumentToneRepository = postDocumentToneRepository;
    }

    @Override
    public void save(PostEntity postEntity, DocumentAnalysis documentAnalysis) {
        for (ToneScore t: documentAnalysis.getTones()) {
            PostDocumentToneEntity entity = new PostDocumentToneEntity();
            entity.setPost(postEntity);
            entity.setToneId(t.getToneId());
            entity.setScore(t.getScore());
            entity.setToneName(t.getToneName());
            this.postDocumentToneRepository.save(entity);
        }
    }
}
