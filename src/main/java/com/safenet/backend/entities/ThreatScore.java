package com.safenet.backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "threat_scores")
public class ThreatScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score; // 0–100

    private String reason;

    private LocalDateTime calculatedAt;

    @OneToOne
    private Device device;
}
