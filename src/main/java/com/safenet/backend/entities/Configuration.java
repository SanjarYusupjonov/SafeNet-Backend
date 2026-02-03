package com.safenet.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "configurations")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyName;
    private String value;
}
