package com.epam.learn.resourceservice;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class SongService {

    @Value("${songService.url}")
    private String songServiceUrl;

    private final RestTemplate restTemplate;

    public static Map<String, String> extractMetadata(byte[] audioData) throws IOException {
        Metadata metadata = new Metadata();
        new Tika().parse(new ByteArrayInputStream(audioData), metadata);

        return Arrays.stream(metadata.names())
                .collect(toMap(name -> name, metadata::get));
    }

    public void saveMetadata(Resource resource, Map<String, String> metadata) {
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
