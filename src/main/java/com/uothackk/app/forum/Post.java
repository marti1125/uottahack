package com.uothackk.app.forum;

import com.uothackk.app.watson.WatsonTone;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Post {

    private Long id;
    private String title;
    private String content;
    private String user;
    private String timeAgo;
    private String categories;
    private List<WatsonTone> tones;

}
