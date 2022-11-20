package com.example.spring.myprojectTask1.repository;

import com.example.spring.myprojectTask1.model.Tutorial;

import java.util.List;

public interface TutorialRepository {
    void save(Tutorial book);
    int update(long id,Tutorial book);
    Tutorial findById(long id);
    int deleteById(long id);
    List<Tutorial> findAll();
    List<Tutorial> findByPublished(boolean published);
    int deleteAll();
}
