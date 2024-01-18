package com.epam.learn.songservice;

import com.jayway.jsonpath.JsonPath;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class SongControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void createSong() throws Exception {
        mockMvc.perform(post("/songs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "We are the champions",
                                    "artist": "Queen",
                                    "album": "News of the world",
                                    "length": "2:59",
                                    "resourceId": "123",
                                    "year": "1977"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", isA(Integer.class)));
    }

    @Test
    void getSong() throws Exception {
        var songId = createEmptySongs(1).get(0);

        mockMvc.perform(get("/songs/" + songId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(songId)));
    }

    @Test
    void getSong_NotFound() throws Exception {
        mockMvc.perform(get("/songs/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSongs() throws Exception {
        List<Integer> ids = createEmptySongs(3);
        String idRequestParamValue = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        mockMvc.perform(delete("/songs?id=" + idRequestParamValue))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ids", hasSize(ids.size())));
    }

    private List<Integer> createEmptySongs(int count) throws Exception {
        List<Integer> ids = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            var result = mockMvc.perform(post("/songs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}")).andReturn();
            Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
            ids.add(id);
        }

        return ids;
    }


}
