package com.uothackk.app.forum.persistance;

import com.uothackk.app.forum.Post;
import com.uothackk.app.forum.PostService;
import com.uothackk.app.util.Util;
import com.uothackk.app.watson.WatsonTone;
import com.uothackk.app.watson.WatsonToneAnalyzer;
import com.uothackk.app.watson.persitance.PostDocumentToneEntity;
import com.uothackk.app.watson.persitance.PostDocumentToneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Component
public class JpaPostService implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final WatsonToneAnalyzer watsonToneAnalyzer;
    private final PostDocumentToneRepository postDocumentToneRepository;

    public JpaPostService(PostRepository postRepository, CategoryRepository categoryRepository,
                          PostCategoryRepository postCategoryRepository, WatsonToneAnalyzer watsonToneAnalyzer,
                          PostDocumentToneRepository postDocumentToneRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.postCategoryRepository = postCategoryRepository;
        this.watsonToneAnalyzer = watsonToneAnalyzer;
        this.postDocumentToneRepository = postDocumentToneRepository;
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

        this.watsonToneAnalyzer.analyze(postEntity, content);

        for (CategoryEntity category : categoryEntities) {
            PostCategoryEntity postCategoryEntity = new PostCategoryEntity();
            postCategoryEntity.setPost(postEntity);
            postCategoryEntity.setCategory(category);
            this.postCategoryRepository.save(postCategoryEntity);
        }

    }

    @Override
    public Iterable<Post> posts(Long categoryId) {
        Sort s = Sort.by(Sort.Direction.DESC, "id");
        if (categoryId != null && categoryId > 0) {
            CategoryEntity categoryEntity = this.categoryRepository.findById(categoryId).get();
            List<PostCategoryEntity> pc = this.postCategoryRepository.findByCategory(categoryEntity, s);

            return StreamSupport.stream(pc.spliterator(), false)
                    .map(this::mapPostFromCategory)
                    .collect(Collectors.toList());

        } else {
            return StreamSupport.stream(postRepository.findAll(s).spliterator(), false)
                    .map(this::mapPost)
                    .collect(Collectors.toList());
        }

    }

    @Override
    public Post findById(Long id) {
        return mapPost(this.postRepository.findById(id).get());
    }

    Post mapPostFromCategory(PostCategoryEntity postCategoryEntity) {
        return new Post(postCategoryEntity.getPost().getId(), postCategoryEntity.getPost().getTitle(),
                postCategoryEntity.getPost().getContent(),
                postCategoryEntity.getPost().getDefaultUser(),
                Util.timeAgo(postCategoryEntity.getPost().getPlacedAt()),
                postCategoryEntity.getPost().getHelperCategories(), tones(postCategoryEntity.getPost()));
    }

    Post mapPost(PostEntity entity) {
        return new Post(entity.getId(), entity.getTitle(), entity.getContent(),
                entity.getDefaultUser(), Util.timeAgo(entity.getPlacedAt()), entity.getHelperCategories(),
                tones(entity));
    }

    List<WatsonTone> tones(PostEntity entity) {
        List<PostDocumentToneEntity> postDocumentToneEntities = this.postDocumentToneRepository.findByPost(entity);

        List<WatsonTone> tones = new ArrayList<>();

        if (postDocumentToneEntities.size() > 0) {
            for (PostDocumentToneEntity pdt : postDocumentToneEntities) {
                tones.add(new WatsonTone(pdt.getScore(), pdt.getToneName()));
            }
        }
        return tones;
    }

}
