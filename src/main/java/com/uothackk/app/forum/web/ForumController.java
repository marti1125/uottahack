package com.uothackk.app.forum.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ForumController {

    @GetMapping
    public String home() {
        return "home";
    }

}
