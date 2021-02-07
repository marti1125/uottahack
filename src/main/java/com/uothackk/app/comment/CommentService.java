package com.uothackk.app.comment;

import java.util.List;

public interface CommentService {

    List<Comment> byPostId(Long postId);

    void save(Long postId, String content);

}
