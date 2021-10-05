package com.example.community.controller;

import com.example.community.entity.Comment;
import com.example.community.entity.User;
import com.example.community.model.CommentIniDTO;
import com.example.community.model.ResultDTO;
import com.example.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//@Controller
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/comment")
    public ResultDTO addComment(@RequestBody CommentIniDTO commentIniDTO,
                                HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOfLogin();
        }

        if (commentIniDTO == null || StringUtils.isBlank(commentIniDTO.getComment())) {//TODO: check string empty or NULL
            return ResultDTO.errorOfEmpty();
        }

        Comment comment = new Comment();
        comment.setParent_id(commentIniDTO.getParent_id());
        comment.setComments(commentIniDTO.getComment());
        comment.setType(commentIniDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        ///comment.setCommentator(27L);
        comment.setLikeCount(0);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }

}
