package com.uothackk.app.comment.persistance;

import com.uothackk.app.forum.persistance.PostEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String content;

    private String defaultUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    private Date placedAt;

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }

}
