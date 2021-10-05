package com.example.community.model;

import lombok.Data;

@Data
//parent Comments
public class CommentIniDTO {
    private Long parent_id;
    private String comment;
    private Integer type;
}
