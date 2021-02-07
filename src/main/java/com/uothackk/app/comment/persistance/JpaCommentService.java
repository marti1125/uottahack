package com.uothackk.app.comment.persistance;

import com.uothackk.app.comment.Comment;
import com.uothackk.app.comment.CommentService;
import com.uothackk.app.forum.persistance.PostEntity;
import com.uothackk.app.forum.persistance.PostRepository;
import com.uothackk.app.util.Util;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class JpaCommentService implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public JpaCommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public List<Comment> byPostId(Long postId) {
        PostEntity postEntity = this.postRepository.findById(postId).get();
        return StreamSupport.stream(this.commentRepository.findByPost(postEntity).spliterator(), false)
                .map(this::mapComment)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Long postId, String content) {
        PostEntity postEntity = this.postRepository.findById(postId).get();
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setPost(postEntity);
        commentEntity.setContent(content);
        commentEntity.setDefaultUser("Anonymous");
        this.commentRepository.save(commentEntity);
    }

    Comment mapComment(CommentEntity entity) {
        return new Comment(entity.getPost().getId(),
                entity.getContent(), entity.getDefaultUser(), Util.timeAgo(entity.getPlacedAt()));
    }

}
