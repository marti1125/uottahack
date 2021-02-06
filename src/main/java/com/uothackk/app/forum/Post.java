package com.uothackk.app.forum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Post {

    private Long id;
    private String title;
    private String content;
    private String user;
    private String timeAgo;
    private String categories;

}
