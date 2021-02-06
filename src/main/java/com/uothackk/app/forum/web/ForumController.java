package com.uothackk.app.forum.web;

import com.uothackk.app.forum.CategoryService;
import com.uothackk.app.forum.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/")
public class ForumController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    ModelAndView home(@Param("category") Long categoryId) {
        log.info(String.valueOf(categoryId));
        return new ModelAndView("home", Map.of("posts", this.postService.posts(categoryId),
                "categories", categoryService.categories()));
    }

}
