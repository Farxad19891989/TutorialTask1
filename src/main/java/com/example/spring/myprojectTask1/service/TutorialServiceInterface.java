package com.example.spring.myprojectTask1.service;

import com.example.spring.myprojectTask1.dto.TutorialDTO;
import com.example.spring.myprojectTask1.model.Tutorial;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TutorialServiceInterface {
    ResponseEntity<List<TutorialDTO>> findAll();

    ResponseEntity<TutorialDTO> findById(long id);

    ResponseEntity<TutorialDTO> createNewTutorial(TutorialDTO dto);

    ResponseEntity<String> deleteTutorialById(long id);
    ResponseEntity<String> updateTutorial(long id, Tutorial tutorial);

    ResponseEntity<List<TutorialDTO>> getPublishedTutorials();

    ResponseEntity<String> deleteAll();
}


