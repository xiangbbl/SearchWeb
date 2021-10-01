package com.example.community.controller;

import com.example.community.entity.Question;
import com.example.community.model.QuestionDTO;
import com.example.community.repository.QuestionRep;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRep questionRep;
    @Autowired
    private QuestionService questionService;

    @GetMapping(path = "/question/{id}")
    public String question(@PathVariable Long id, Model model){
        QuestionDTO q = questionService.getById(id);
        model.addAttribute("question", q);
        return "question";
    }

}
