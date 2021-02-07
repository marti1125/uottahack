package com.uothackk.app.warning;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/warning-post")
public class WarningPostController {

    @GetMapping
    public String warningPost() {
        return "warning-post";
    }

}
