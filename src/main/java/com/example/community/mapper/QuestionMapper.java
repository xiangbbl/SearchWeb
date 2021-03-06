package com.example.community.mapper;

import com.example.community.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question " +
            "(title,description,gmt_create,gmt_modified,creator,tag, view_count, comment_count, like_count) " +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag},#{viewCount},#{commentCount},#{likeCount})")
    void create(Question question);

    @Select("select * from question")
    List<Question> list();
}
