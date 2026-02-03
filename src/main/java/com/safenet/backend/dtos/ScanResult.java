package com.safenet.backend.dtos;

import lombok.Data;

@Data
public class ScanResult {

    private String ipAddress;
    private String macAddress;
    private String hostname;
    private boolean reachable;
}
