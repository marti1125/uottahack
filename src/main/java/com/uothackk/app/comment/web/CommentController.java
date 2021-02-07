package com.uothackk.app.comment.web;

import com.uothackk.app.comment.Comment;
import com.uothackk.app.comment.CommentService;
import com.uothackk.app.forum.PostService;
import com.uothackk.app.recaptchav3.ReCaptchaV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    ReCaptchaV3 captchaV3;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @GetMapping
    ModelAndView comments(@Param("postId") Long postId) {
        List<Comment> comments = commentService.byPostId(postId);
        return new ModelAndView("comment", Map.of("comments", comments,
                "post",  postService.findById(postId), "count_comments", comments.size()));
    }

    @PostMapping
    public String createNewPost(String recaptchaTkn, Long postId, String content) {
        Boolean valid = captchaV3.verify(recaptchaTkn);
        if (valid) {
            this.commentService.save(postId, content);
        }
        return "redirect:/comment?postId=" + postId;
    }

}
