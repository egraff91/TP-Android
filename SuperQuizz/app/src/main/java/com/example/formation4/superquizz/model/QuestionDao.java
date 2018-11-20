package com.example.formation4.superquizz.model;

import com.example.formation4.superquizz.model.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> findAll();
    void save(Question question);
    void delete(Question question);

}
