package com.uothackk.app.livechat.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/into-live-chat")
public class IntroLiveChatController {

    @GetMapping
    public String intro() {
        return "intro-live-chat";
    }

}
