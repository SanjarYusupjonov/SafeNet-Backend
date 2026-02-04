package com.safenet.backend.services;

import com.safenet.backend.dtos.ScanResult;
import com.safenet.backend.entities.Network;
import com.safenet.backend.networkScanner.NetworkScanner;
import com.safenet.backend.repositories.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NetworkDiscoveryService {

    private final NetworkScanner scanner;
    private final DeviceService deviceService;
    private final DeviceRepository deviceRepository;

    public NetworkDiscoveryService(
            NetworkScanner scanner,
            DeviceService deviceService,
            DeviceRepository deviceRepository) {
        this.scanner = scanner;
        this.deviceService = deviceService;
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public void discover(Network network) {

        List<ScanResult> results = scanner.scan(network.getCidr());

        System.out.println("Scanner result Size : " + results.size());

        results.stream()
                .filter(ScanResult::isReachable)
                .map(r -> deviceService.normalize(r, network))
                .forEach(deviceRepository::save);
    }
}
