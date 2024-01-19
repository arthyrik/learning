package com.epam.learn.resourceservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class Resource {

    @Id
    @GeneratedValue
    private Integer id;
    @Lob
    private byte[] data;
}
