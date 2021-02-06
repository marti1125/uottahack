package com.uothackk.app.forum.web;

import com.uothackk.app.forum.CategoryService;
import com.uothackk.app.forum.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/")
public class ForumController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    ModelAndView home() {
        return new ModelAndView("home", Map.of("posts", this.postService.posts(),
                "categories", categoryService.categories()));
    }

}
