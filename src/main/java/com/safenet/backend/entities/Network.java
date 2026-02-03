package com.safenet.backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "networks")
public class Network {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cidr; // e.g. 192.168.1.0/24

    private String description;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "network", cascade = CascadeType.ALL)
    private List<Device> devices = new ArrayList<>();
}
