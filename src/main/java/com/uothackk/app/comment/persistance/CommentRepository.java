package com.uothackk.app.comment.persistance;

import com.uothackk.app.forum.persistance.PostEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CommentRepository extends CrudRepository<CommentEntity, Long> {

    List<CommentEntity> findByPost(PostEntity postEntity);

}
