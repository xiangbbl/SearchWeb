package com.example.community.model;

import com.example.community.entity.User;
import lombok.Data;

// Comments under one comment
@Data
public class CommentDTO {
    private Long id;
    private Long parent_id;
    private String comments;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private User user;
}
