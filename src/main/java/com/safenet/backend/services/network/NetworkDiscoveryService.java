package com.safenet.backend.services.network;

import com.safenet.backend.dtos.ScanResult;
import com.safenet.backend.entities.Network;
import com.safenet.backend.networkScanner.NetworkScanner;
import com.safenet.backend.services.device.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class NetworkDiscoveryService {

    private final NetworkScanner scanner;
    private final DeviceService deviceService;

    @Transactional
    public void discover(Network network) {

        List<ScanResult> results = scanner.scan(network.getCidr());

        System.out.println("Scanner result Size : " + results.size());

        results.stream()
                .filter(ScanResult::isReachable)
                .map(r -> deviceService.findOrCreateDevice(r, network))
                .forEach(deviceService::save);
    }
}
