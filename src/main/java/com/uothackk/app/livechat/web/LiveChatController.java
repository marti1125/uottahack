package com.uothackk.app.livechat.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/live-chat")
public class LiveChatController {

    @GetMapping
    public String liveChat() {
        return "live-chat";
    }

}
