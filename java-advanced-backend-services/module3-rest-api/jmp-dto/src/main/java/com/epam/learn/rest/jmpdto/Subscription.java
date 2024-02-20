package com.epam.learn.rest.jmpdto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Subscription {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    private LocalDate startDate;
}
