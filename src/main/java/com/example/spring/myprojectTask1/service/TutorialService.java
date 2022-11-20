package com.example.spring.myprojectTask1.service;

import com.example.spring.myprojectTask1.dto.TutorialDTO;
import com.example.spring.myprojectTask1.mapping.TutorialMapper;
import com.example.spring.myprojectTask1.model.Tutorial;
import com.example.spring.myprojectTask1.repository.JDBCTutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class TutorialService implements TutorialServiceInterface {
    @Autowired
    private JDBCTutorialRepository jdbcTutorialRepository;

    @Override
    public ResponseEntity<List<TutorialDTO>> findAll() {
        List<TutorialDTO> tutorialsDTOS = new ArrayList<>();
        List<Tutorial> tutorials = jdbcTutorialRepository.findAll();

        try {

            if (tutorials.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                for (Tutorial tutorial : tutorials) {
                    tutorialsDTOS.add(TutorialMapper.toDto(tutorial));
                }
                return new ResponseEntity<>(tutorialsDTOS, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<TutorialDTO> createNewTutorial(@RequestBody TutorialDTO dto) {
        var tutorial = new Tutorial(dto.getTitle(), dto.getDescription(), dto.isPublished());
        jdbcTutorialRepository.save(tutorial);
        return new ResponseEntity<>(TutorialMapper.toDto(tutorial), HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteTutorialById(long id) {
        int deleted;
        deleted=jdbcTutorialRepository.deleteById(id);
        if(deleted==1) {
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);

        }else{
            return new ResponseEntity<>("Data not found",HttpStatus.NOT_FOUND);
        }


    }


    public ResponseEntity<TutorialDTO> findById(long id) {
        Tutorial tutorial = jdbcTutorialRepository.findById(id);
        if(tutorial==null) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        TutorialDTO tutorialDTO = TutorialMapper.toDto(tutorial);
        return new ResponseEntity<>(tutorialDTO, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> updateTutorial(long id,Tutorial tutorial) {
        int updated;
        updated=jdbcTutorialRepository.update(id,tutorial);
        if(updated==1) {
            return new  ResponseEntity<>("Updated was succesfully",HttpStatus.OK);
        }else
            return new ResponseEntity<>("Updates wasnt succesfully",HttpStatus.NOT_FOUND);


    }

    @Override
    public ResponseEntity<List<TutorialDTO>> getPublishedTutorials() {
        List<Tutorial> tutorials=jdbcTutorialRepository.findByPublished(true);
        List<TutorialDTO> tutorialsDTO=new ArrayList<>();
        for(Tutorial tutorial:tutorials) {
            tutorialsDTO.add(TutorialMapper.toDto(tutorial));
        }

        return new ResponseEntity<>(tutorialsDTO,HttpStatus.OK);
    }

    @Override
   public   ResponseEntity<String> deleteAll(){
        int isDeleted= jdbcTutorialRepository.deleteAll();
        if(isDeleted>0) {
            return new  ResponseEntity<>("All data is deleted",HttpStatus.OK);
        }
        else{
            return new  ResponseEntity<>("There is no data for deleting",HttpStatus.OK);
        }

    }
}