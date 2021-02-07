package com.uothackk.app.forum.web;

import com.ibm.watson.tone_analyzer.v3.model.ToneScore;
import com.uothackk.app.forum.CategoryService;
import com.uothackk.app.forum.PostService;
import com.uothackk.app.recaptchav3.ReCaptchaV3;
import com.uothackk.app.watson.WatsonToneAnalyzer;
import com.uothackk.app.watson.WatsonToneAnalyzerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    ReCaptchaV3 captchaV3;

    @Autowired
    PostService postService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    WatsonToneAnalyzerService watsonToneAnalyzerService;

    @GetMapping
    ModelAndView post() {
        return new ModelAndView("post", Map.of("categories", categoryService.categories()));
    }

    @PostMapping
    public String createNewPost(String recaptchaTkn, String title, String content, Long[] listCategories) {
        List<ToneScore> scores = this.watsonToneAnalyzerService.textAnalyzer(content);
        for (ToneScore t : scores) {
            if (t.getToneName().equalsIgnoreCase("Anger") && t.getScore() > 0.5) {
                return "redirect:/warning-post";
            }
        }
        Boolean valid = captchaV3.verify(recaptchaTkn);
        if (!valid || title.length() == 0 || content.length() == 0 || listCategories.length == 0) {
            return "redirect:/post";
        }
        this.postService.save(title, content, listCategories);
        return "redirect:/";
    }

}
