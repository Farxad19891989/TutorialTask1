package com.example.spring.myprojectTask1.mapping;

import com.example.spring.myprojectTask1.dto.TutorialDTO;
import com.example.spring.myprojectTask1.model.Tutorial;

public final class TutorialMapper {

    public static final TutorialDTO toDto(Tutorial tutorial) {
        return TutorialDTO.builder().id(tutorial.getId())
                .title(tutorial.getTitle())
                .description(tutorial.getDescription())
                .published(tutorial.isPublished())
                .build();
    }
}
