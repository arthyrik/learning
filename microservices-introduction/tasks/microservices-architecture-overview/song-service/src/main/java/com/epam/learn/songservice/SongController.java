package com.epam.learn.songservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongRepository songRepository;

    @GetMapping
    public Iterable<Song> getSongs() {
        return songRepository.findAll();
    }

    @GetMapping("/{id}")
    public Song getSong(@PathVariable Integer id) {
        var song = songRepository.findById(id);

        if (song.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no room with id " + id);
        }

        return song.get();
    }

    @DeleteMapping
    public Map<String, Iterable<Integer>> deleteSongs(@RequestParam("id") List<Integer> ids) {
        songRepository.deleteAllById(ids);
        return Map.of("ids", ids);
    }

    @PostMapping
    public Map<String, Integer> createSong(@RequestBody Song song) {
        return Map.of("id", songRepository.save(song).getId());
    }
}
