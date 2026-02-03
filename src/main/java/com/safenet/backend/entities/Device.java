package com.safenet.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "devices",
        uniqueConstraints = @UniqueConstraint(columnNames = {"ipAddress", "network_id"}))
@Data
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ipAddress;

    private String macAddress;

    private String hostname;

    private boolean active;

    private LocalDateTime firstSeen;
    private LocalDateTime lastSeen;

    @ManyToOne
    @JoinColumn(name = "network_id", nullable = false)
    private Network network;
}
