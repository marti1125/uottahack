package com.uothackk.app.forum.persistance;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PostCategoryRepository extends CrudRepository<PostCategoryEntity, Long> {

    List<PostCategoryEntity> findByCategory(CategoryEntity categoryEntity);

}
