package com.epam.learn.resourceservice;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ResourceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    void beforeEach() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void create() throws Exception {
        byte[] audioData = getClass().getClassLoader().getResourceAsStream("test.mp3").readAllBytes();
        MockMultipartFile file = new MockMultipartFile("file", "test.mp3", "audio/mpeg",
                audioData);

        mockRestServiceServer
                .expect(requestTo("http://localhost:8080/songs"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"id\": 7}", MediaType.APPLICATION_JSON));

        mockMvc.perform(multipart("/resources").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", isA(Integer.class)));

        mockRestServiceServer.verify();
    }

    @Test
    void getById() throws Exception {
        var id = createResources(1).get(0);

        mockMvc.perform(get("/resources/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(not(emptyOrNullString())));
    }

    @Test
    void getById_notFound() throws Exception {
        mockMvc.perform(get("/resources/234234"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        List<Integer> ids = createResources(3);
        String idRequestParamValue = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        mockMvc.perform(delete("/resources?id=" + idRequestParamValue))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ids", hasSize(ids.size())));
    }

    private List<Integer> createResources(int count) throws Exception {
        List<Integer> ids = new ArrayList<>();

        mockRestServiceServer
                .expect(times(count), requestTo("http://localhost:8080/songs"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess());

        for (int i = 0; i < count; i++) {
            var file = new MockMultipartFile("file", "test.mp3", "audio/mpeg",
                    "la-la-la".getBytes());
            var result = mockMvc.perform(multipart("/resources").file(file)).andReturn();
            Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
            ids.add(id);
        }

        return ids;
    }
}