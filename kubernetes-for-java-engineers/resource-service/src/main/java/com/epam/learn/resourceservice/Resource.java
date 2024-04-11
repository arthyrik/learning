package com.epam.learn.resourceservice;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Lob
    private byte[] data;
}
