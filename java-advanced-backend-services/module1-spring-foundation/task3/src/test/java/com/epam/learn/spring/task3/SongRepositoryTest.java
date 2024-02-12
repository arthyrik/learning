package com.epam.learn.spring.task3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class SongRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    @Test
    void whenCalledSave_thenCorrectNumberOfSongs() {
        var song = new Song();
        song.setName("Name");
        song.setAlbum("Album");
        song.setArtist("Artist");

        songRepository.save(song);
        List<Song> songs = songRepository.findAll();

        assertThat(songs.size()).isEqualTo(1);
    }
}

