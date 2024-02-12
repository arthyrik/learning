package com.epam.learn.songservice;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@SequenceGenerator(name="song_id_seq", sequenceName = "song_id_seq", allocationSize = 1)
public class Song {
    @Id
    @GeneratedValue(generator = "song_id_seq")
    private Integer id;
    private String name;
    private String artist;
    private String album;
    private String length;
    @Column(unique = true)
    private String resourceId;
    private String year;
}
