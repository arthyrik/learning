package com.epam.learn.resourceservice;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@SequenceGenerator(name="resource_id_seq", sequenceName = "resource_id_seq", allocationSize = 1)
@Data
public class Resource {

    @Id
    @GeneratedValue(generator = "resource_id_seq")
    private Integer id;
    @Lob
    private byte[] data;
}
