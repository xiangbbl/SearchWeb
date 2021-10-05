package com.example.community.service;

import com.example.community.entity.Comment;
import com.example.community.entity.Question;
import com.example.community.entity.User;
import com.example.community.enums.CommentType;
import com.example.community.model.CommentDTO;
import com.example.community.repository.CommentRep;
import com.example.community.repository.QuestionRep;
import com.example.community.repository.UserRep;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRep commentRep;
    @Autowired
    private QuestionRep questionRep;
    @Autowired
    private UserRep userRep;

    @Transactional
    public void insert(Comment comment) {
        // This is Comment of comment, second LVL comment
        if (comment.getType() == CommentType.COMMENT_lvl2.getType()) {
            commentRep.save(comment);


            //Comment parentComment = new Comment();
            //parentComment.setId(comment.getParentId());
            //parentComment.setcommentcount(1);
            //commentExtMapper.incCommentCount(parentComment);

        }
        // This is Comment of question, first LVL comment
        else {
            comment.setCommentCount(0);
            commentRep.save(comment);
            CommentInc(comment.getParent_id());
        }

    }
    public void CommentInc(Long id) {
        Question question = questionRep.findById(id).orElse(null);
        question.setCommentCount(question.getCommentCount()+1);
        questionRep.save(question);
    }

    // Parent_id & comment type
    public List<CommentDTO> ListOfCommentById(Long id) {
        List<Comment> commentList =  commentRep.findCommentsByParent_idType(id);

        List<CommentDTO> commentDTOList = new ArrayList<>();

        for(Comment c: commentList){
            CommentDTO commentDTO = new CommentDTO();
            User user = userRep.findById(c.getCommentator()).orElse(null);
            BeanUtils.copyProperties(c, commentDTO);
            commentDTO.setUser(user);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
