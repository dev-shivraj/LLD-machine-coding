package com.machinecoding.bookmyshow.model;

import com.machinecoding.bookmyshow.model.enums.Language;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Movie extends BaseEntity {

    private String title;
    private int year;
    private String director;
    private String genre;
    private int rating;
    private String description;

    @ElementCollection
    private List<Language> languages;
}
