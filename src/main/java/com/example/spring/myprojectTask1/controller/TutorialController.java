package com.example.spring.myprojectTask1.controller;


import com.example.spring.myprojectTask1.dto.TutorialDTO;
import com.example.spring.myprojectTask1.model.Tutorial;
import com.example.spring.myprojectTask1.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TutorialController {
    @Autowired
    TutorialService tutorialService;

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialDTO>> getAllTutorials() {
        return tutorialService.findAll();
    }

    @PostMapping("/tutorials")
    public ResponseEntity<TutorialDTO> createNewTutorial(@RequestBody TutorialDTO dto) {
        return tutorialService.createNewTutorial(dto);
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<String> deleteTutorialById(@PathVariable("id") long id) {
        return tutorialService.deleteTutorialById(id);
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutorialDTO> findById(@PathVariable("id") long id) {
        return tutorialService.findById(id);
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<String> updateTutorial(@PathVariable("id") long id,@RequestBody
    Tutorial tutorial) {

        return tutorialService.updateTutorial(id,tutorial);

    }
    @GetMapping("/tutorials/published")
    public ResponseEntity<List<TutorialDTO>> getPublished() {
        return tutorialService.getPublishedTutorials();

    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<String> deleteAll() {
       return tutorialService.deleteAll();
    }
}
