package com.uothackk.app.forum.web;

import com.uothackk.app.forum.CategoryService;
import com.uothackk.app.forum.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Lob;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    ModelAndView post() {
        return new ModelAndView("post", Map.of("categories", categoryService.categories()));
    }

    @PostMapping
    public String createNewPost(String title, String content, Long[] listCategories) {
        if (title.length() == 0 || content.length() == 0 || listCategories.length == 0) {
            return "redirect:/post";
        }
        this.postService.save(title, content, listCategories);
        return "redirect:/";
    }

}
