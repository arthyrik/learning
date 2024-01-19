package com.epam.learn.resourceservice;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.convert.ValueConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    @Value("${songService.url}")
    private String songServiceUrl;

    private final RestTemplate restTemplate;
    private final ResourceRepository resourceRepository;

    @PostMapping
    public Map<String, Integer> create(@RequestParam("file") MultipartFile file) throws IOException {
        var resource = new Resource();
        resource.setData(file.getBytes());

        resource = resourceRepository.save(resource);

        Map<String, String> metadata = extractMetadata(resource.getData());
        saveMetadata(resource, metadata);

        return Map.of("id", resource.getId());
    }

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    public byte[] getById(@PathVariable Integer id) {
        var resource = resourceRepository.findById(id);

        if (resource.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no resource with id " + id);
        }

        return resource.get().getData();
    }

    @DeleteMapping
    public Map<String, Iterable<Integer>> deleteById(@RequestParam("id") List<Integer> ids) {
        resourceRepository.deleteAllById(ids);

        return Map.of("ids", ids);
    }

    private static Map<String, String> extractMetadata(byte[] audioData) throws IOException {
        Metadata metadata = new Metadata();
        new Tika().parse(new ByteArrayInputStream(audioData), metadata);

        return Arrays.stream(metadata.names())
                .collect(toMap(name -> name, metadata::get));
    }

    private void saveMetadata(Resource resource, Map<String, String> metadata) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Song song = new Song(
                metadata.get("xmpDM:name"),
                metadata.get("xmpDM:artist"),
                metadata.get("xmpDM:album"),
                metadata.get("xmpDM:duration"),
                String.valueOf(resource.getId()),
                metadata.get("xmpDM:releaseDate")
        );
        HttpEntity<Song> request = new HttpEntity<>(song, headers);

        restTemplate.postForObject(songServiceUrl, request, String.class);
    }
}
