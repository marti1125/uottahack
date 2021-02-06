package com.uothackk.app.forum.web;

import com.uothackk.app.forum.CategoryService;
import com.uothackk.app.forum.PostService;
import com.uothackk.app.recaptchav3.ReCaptchaV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    ReCaptchaV3 captchaV3;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    ModelAndView post() {
        return new ModelAndView("post", Map.of("categories", categoryService.categories()));
    }

    @PostMapping
    public String createNewPost(String recaptchaTkn, String title, String content, Long[] listCategories) {
        log.info(recaptchaTkn);
        //ReCaptchaV3 captchaV3 = new ReCaptchaV3();
        Boolean valid = captchaV3.verify(recaptchaTkn);

        if (!valid || title.length() == 0 || content.length() == 0 || listCategories.length == 0) {
            return "redirect:/post";
        }
        this.postService.save(title, content, listCategories);
        return "redirect:/";
    }

}
