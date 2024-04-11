package com.epam.learn.songservice;

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
    private String length;
    @Column(unique = true)
    private String resourceId;
    private String year;
}
