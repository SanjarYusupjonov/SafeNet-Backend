package com.safenet.backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String severity; // LOW, MEDIUM, HIGH, CRITICAL

    private String message;

    private boolean resolved;

    private LocalDateTime createdAt;

    @ManyToOne
    private Device device;
}
