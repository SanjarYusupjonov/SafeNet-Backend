package com.safenet.backend;

import com.safenet.backend.entities.Network;
import com.safenet.backend.repositories.NetworkRepository;
import com.safenet.backend.services.NetworkDiscoveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupTestRunner implements CommandLineRunner {

    private final NetworkRepository networkRepository;
    private final NetworkDiscoveryService networkDiscoveryService;

    @Override
    public void run(String... args) {
        String cidr = "192.168.1.0/24";

        Network network = networkRepository
                .findByCidr(cidr)
                .orElseGet(() -> {
                    Network n = new Network();
                    n.setCidr(cidr);
                    n.setDescription("Test Network");
                    return networkRepository.save(n);
                });

        networkDiscoveryService.discover(network);


        System.out.println("Saved networks count: " + networkRepository.count());
    }
}
