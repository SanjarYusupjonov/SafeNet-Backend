//package com.safenet.backend.services;
//
//import com.safenet.backend.dtos.ScanResult;
//import com.safenet.backend.entities.Alert;
//import com.safenet.backend.entities.Device;
//import com.safenet.backend.entities.IPActivityLog;
//import com.safenet.backend.entities.Network;
//import com.safenet.backend.repositories.AlertRepository;
//import com.safenet.backend.repositories.DeviceRepository;
//import com.safenet.backend.repositories.IPActivityLogRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class DeviceTrackingService {
//
//    private final DeviceRepository deviceRepository;
//    private final IPActivityLogRepository ipActivityLogRepository;
//    private final AlertRepository alertRepository;
//
//    public void processScannedResults(List<ScanResult> scanResults, Network network) {
//
//        if (deviceExists(scan, network)) {
//            return;
//        }
//
//        LocalDateTime now = LocalDateTime.now();
//
//        Device device = createNewDevice(scan, network, now);
//        logScanActivity(device, scan.ip(), now);
//        raiseNewDeviceAlert(device, scan, now);
//    }
//
//    private boolean deviceExists(ScannedDevice scan, Network network) {
//        return deviceRepository
//                .findByMacAddressAndNetwork(scan.mac(), network)
//                .isPresent();
//    }
//
//    private Device createNewDevice(
//            ScannedDevice scan,
//            Network network,
//            LocalDateTime now
//    ) {
//        Device device = new Device();
//        device.setMacAddress(scan.mac());
//        device.setIpAddress(scan.ip());
//        device.setHostname(scan.hostname());
//        device.setActive(true);
//        device.setFirstSeen(now);
//        device.setLastSeen(now);
//        device.setNetwork(network);
//
//        return deviceRepository.save(device);
//    }
//
//    private void logScanActivity(
//            Device device,
//            String ipAddress,
//            LocalDateTime now
//    ) {
//        IPActivityLog log = new IPActivityLog();
//        log.setIpAddress(ipAddress);
//        log.setActivityType("SCAN");
//        log.setActivityTime(now);
//        log.setDevice(device);
//
//        ipActivityLogRepository.save(log);
//    }
//
//    private void raiseNewDeviceAlert(
//            Device device,
//            ScannedDevice scan,
//            LocalDateTime now
//    ) {
//        Alert alert = new Alert();
//        alert.setSeverity("MEDIUM");
//        alert.setMessage(
//                "New device detected: MAC=" + scan.mac() + ", IP=" + scan.ip()
//        );
//        alert.setResolved(false);
//        alert.setCreatedAt(now);
//        alert.setDevice(device);
//
//        alertRepository.save(alert);
//    }
//
//
//}
