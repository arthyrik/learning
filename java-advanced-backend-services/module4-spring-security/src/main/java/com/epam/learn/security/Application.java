package com.epam.learn.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        var info = new User();
        info.setUsername("info");
        info.setPassword("{bcrypt}$2a$12$RDtULujuFESorXrZGUmZtuFfAuiHgoVHUOWY722.QvP4lEsvIyosO");

        var admin = new User();
        admin.setUsername("admin");
        admin.setPassword("{bcrypt}$2a$12$8NmV2iD0poIbQOGF01BEve1n0Yi06jGtqeYcTecudWY7ga8s8o8.G");

        var full = new User();
        full.setUsername("full");
        full.setPassword("{bcrypt}$2a$12$8NmV2iD0poIbQOGF01BEve1n0Yi06jGtqeYcTecudWY7ga8s8o8.G");

        userRepository.save(info);
        userRepository.save(admin);
        userRepository.save(full);


        authorityRepository.save(new Authority("VIEW_INFO", info));
        authorityRepository.save(new Authority("VIEW_ADMIN", admin));
        authorityRepository.save(new Authority("VIEW_INFO", full));
        authorityRepository.save(new Authority("VIEW_ADMIN", full));

    }
}
