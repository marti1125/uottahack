package com.uothackk.app.forum;

public interface PostService {

    void save(String title, String content, Long[] listCategories);

    Iterable<Post> posts(Long categoryId);

    Post findById(Long id);

}
