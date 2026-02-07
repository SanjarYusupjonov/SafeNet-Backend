package com.safenet.backend.services.device;

import com.safenet.backend.dtos.ScanResult;
import com.safenet.backend.entities.Device;
import com.safenet.backend.entities.Network;
import com.safenet.backend.repositories.DeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public Device findOrCreateDevice(ScanResult scanResult, Network network) {
        return deviceRepository
                .findByIpAddressAndNetwork(scanResult.getIpAddress(), network)
                .map(device -> {
                    device.setLastSeen(LocalDateTime.now());
                    device.setActive(true);
                    device.setMacAddress(scanResult.getMacAddress());
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
                    device.setMacAddress(scanResult.getMacAddress());
                    return device;
                });
    }

    public void save(Device device) {
        deviceRepository.save(device);
    }
}
