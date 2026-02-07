package com.safenet.backend.services.network;

import com.safenet.backend.entities.Network;
import com.safenet.backend.repositories.NetworkRepository;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NetworkService {
    private final NetworkRepository networkRepository;

    public List<Network> getAllNetworks() {
        List<Network> networks = networkRepository.findAll();

        return networks;
    }
}
