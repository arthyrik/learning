package com.epam.learn.spring.task1;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String artist;
    private String album;
}
