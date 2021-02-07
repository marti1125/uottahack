package com.uothackk.app.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {

    private Long postId;
    private String content;
    private String user;
    private String timeAgo;

}
