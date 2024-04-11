package com.epam.learn.songservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.instancio.Select.field;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class SongControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        var song = Instancio.of(Song.class)
                .ignore(field(Song::getId))
                .create();

        mockMvc.perform(post("/songs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(song)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", isA(Integer.class)));
    }

    @Test
    void getById() throws Exception {
        var songId = createSongs(1).get(0);

        mockMvc.perform(get("/songs/" + songId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(songId)));
    }

    @Test
    void getById_NotFound() throws Exception {
        mockMvc.perform(get("/songs/12312"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        List<Integer> ids = createSongs(3);
        String idRequestParamValue = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        mockMvc.perform(delete("/songs?id=" + idRequestParamValue))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ids", hasSize(ids.size())));
    }

    private List<Integer> createSongs(int count) throws Exception {
        List<Integer> ids = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            var song = Instancio.of(Song.class)
                    .ignore(field(Song::getId))
                    .create();
            var result = mockMvc.perform(post("/songs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(song)))
                    .andReturn();
            Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
            ids.add(id);
        }

        return ids;
    }
}
