package com.safenet.backend.networkScanner;

import com.safenet.backend.entities.Network;
import com.safenet.backend.repositories.NetworkRepository;
import com.safenet.backend.services.network.NetworkDiscoveryService;
import com.safenet.backend.services.network.NetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NetworkScanScheduler {

    private final NetworkService networkService;
    private final NetworkDiscoveryService networkDiscoveryService;

    // Runs every 5 minutes (cron or fixedDelay can be adjusted)
    @Scheduled(fixedDelay = 30_000)
    public void scanAllNetworks() {
        List<Network> networks = networkService.getAllNetworks();

        System.out.println("Starting scheduled scan for " + networks.size() + " networks...");

        for (Network network : networks) {
            try {
                networkDiscoveryService.discover(network);
            } catch (Exception e) {
                System.err.println("Failed to scan network " + network.getCidr() + ": " + e.getMessage());
            }
        }

        System.out.println("Scheduled scan completed.");
    }
}
