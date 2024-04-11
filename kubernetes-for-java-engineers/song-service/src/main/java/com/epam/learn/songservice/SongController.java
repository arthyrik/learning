package com.epam.learn.songservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
@Slf4j
public class SongController {

    private final SongRepository songRepository;

    @PostMapping
    public Map<String, Integer> create(@RequestBody Song song) {
        log.info("Create song request");
        return Map.of("id", songRepository.save(song).getId());
    }

    @GetMapping("/{id}")
    public Song getById(@PathVariable Integer id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no song with id " + id));
    }

    @DeleteMapping
    public Map<String, Iterable<Integer>> deleteById(@RequestParam("id") List<Integer> ids) {
        songRepository.deleteAllById(ids);
        return Map.of("ids", ids);
    }
}
