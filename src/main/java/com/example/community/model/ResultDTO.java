package com.example.community.model;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
public class ResultDTO {
    private String message;
    private Integer code;

    public static ResultDTO errorOfLogin(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(1000);
        resultDTO.setMessage("Log in first!");
        return resultDTO;
    }
    public static ResultDTO errorOfEmpty(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(1001);
        resultDTO.setMessage("Comment cannot be empty!");
        return resultDTO;
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(100);
        resultDTO.setMessage("Success!");
        return resultDTO;
    }
}
