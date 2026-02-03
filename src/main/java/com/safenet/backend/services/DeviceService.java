package com.safenet.backend.services;

import com.safenet.backend.dtos.ScanResult;
import com.safenet.backend.entities.Device;
import com.safenet.backend.entities.Network;
import com.safenet.backend.repositories.DeviceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device normalize(ScanResult scanResult, Network network) {

        return deviceRepository
                .findByIpAddressAndNetwork(scanResult.getIpAddress(), network)
                .map(device -> {
                    device.setLastSeen(LocalDateTime.now());
                    device.setActive(true);
                    device.setHostname(scanResult.getHostname());
                    return device;
                })
                .orElseGet(() -> {
                    Device device = new Device();
                    device.setIpAddress(scanResult.getIpAddress());
                    device.setHostname(scanResult.getHostname());
                    device.setFirstSeen(LocalDateTime.now());
                    device.setLastSeen(LocalDateTime.now());
                    device.setActive(true);
                    device.setNetwork(network);
                    return device;
                });
    }
}
