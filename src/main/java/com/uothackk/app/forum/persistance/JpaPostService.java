package com.uothackk.app.forum.persistance;

import com.uothackk.app.forum.Post;
import com.uothackk.app.forum.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Component
public class JpaPostService implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final PostCategoryRepository postCategoryRepository;

    public JpaPostService(PostRepository postRepository, CategoryRepository categoryRepository,
                          PostCategoryRepository postCategoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.postCategoryRepository = postCategoryRepository;
    }

    @Override
    public void save(String title, String content, Long[] listCategories) {

        List<CategoryEntity> categoryEntities = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for (Long id : listCategories) {
            CategoryEntity categoryEntity = this.categoryRepository.findById(id).get();
            categoryEntities.add(categoryEntity);
            builder.append(categoryEntity.getName()).append(", ");
        }

        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(title);
        postEntity.setContent(content);
        postEntity.setHelperCategories(builder.deleteCharAt(builder.length() - 1).toString());
        postEntity.setDefaultUser("Anonymous");
        postRepository.save(postEntity);

        for (CategoryEntity category : categoryEntities) {
            PostCategoryEntity postCategoryEntity = new PostCategoryEntity();
            postCategoryEntity.setPost(postEntity);
            postCategoryEntity.setCategory(category);
            this.postCategoryRepository.save(postCategoryEntity);
        }

    }

    @Override
    public Iterable<Post> posts(Long categoryId) {

        if (categoryId != null && categoryId > 0) {
            log.info("here....");
            CategoryEntity categoryEntity = this.categoryRepository.findById(categoryId).get();
            List<PostCategoryEntity> pc = this.postCategoryRepository.findByCategory(categoryEntity);

            return StreamSupport.stream(pc.spliterator(), false)
                    .map(this::mapPostFromCategory)
                    .collect(Collectors.toList());

        } else {
            return StreamSupport.stream(postRepository.findAll().spliterator(), false)
                    .map(this::mapPost)
                    .collect(Collectors.toList());
        }

    }

    Post mapPostFromCategory(PostCategoryEntity postCategoryEntity) {
        return new Post(postCategoryEntity.getPost().getId(), postCategoryEntity.getPost().getTitle(),
                postCategoryEntity.getPost().getContent(),
                postCategoryEntity.getPost().getDefaultUser(),
                "", postCategoryEntity.getPost().getHelperCategories());
    }

    Post mapPost(PostEntity entity) {

        long today = new Date().getTime();
        long diff = today - entity.getPlacedAt().getTime();

        String timeAgo = "";

        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffDays > 0) {
            timeAgo = "" + diffDays + " days ago";
        } else if (diffDays == 0 && diffHours > 0) {
            timeAgo = "" + diffHours + " hours ago";
        } else {
            timeAgo = "" + diffMinutes + " minutes ago";
        }

        return new Post(entity.getId(), entity.getTitle(), entity.getContent(),
                entity.getDefaultUser(), timeAgo, entity.getHelperCategories());
    }

}
