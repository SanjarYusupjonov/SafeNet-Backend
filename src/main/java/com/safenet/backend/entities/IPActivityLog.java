package com.safenet.backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "ip_activity_logs")
public class IPActivityLog {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String ipAddress;

    private LocalDateTime activityTime;

    private String activityType; // SCAN, CONNECTION, PORT_CHECK

    @ManyToOne
    private Device device;
}
