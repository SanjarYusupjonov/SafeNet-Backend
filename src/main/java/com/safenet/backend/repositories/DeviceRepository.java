package com.safenet.backend.repositories;

import com.safenet.backend.entities.Device;
import com.safenet.backend.entities.Network;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByIpAddressAndNetwork(String ipAddress, Network network);

    List<Device> findAllByNetwork(Network network);

    Optional<Device> findByMacAddressAndNetwork(String macAddress, Network network);
}
