package com.uothackk.app.watson.persitance;

import com.uothackk.app.forum.persistance.PostEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostDocumentToneRepository extends CrudRepository<PostDocumentToneEntity, Long> {

    List<PostDocumentToneEntity> findByPost(PostEntity postEntity);

}
