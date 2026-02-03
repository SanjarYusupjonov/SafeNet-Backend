package com.safenet.backend.networkScanner;

import com.safenet.backend.dtos.ScanResult;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class IcmpNetworkScanner implements NetworkScanner {

    private final ExecutorService executor = Executors.newFixedThreadPool(50);

    @Override
    public List<ScanResult> scan(String cidr) {
        List<String> allIps = CidrUtils.getAllIps(cidr);

        List<CompletableFuture<ScanResult>> futures = allIps.stream()
                .map(ip -> CompletableFuture.supplyAsync(() -> scanIp(ip), executor))
                .collect(Collectors.toList());

        return futures.stream()
                .map(CompletableFuture::join)
                .filter(result -> result != null)
                .collect(Collectors.toList());
    }

    private ScanResult scanIp(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            boolean reachable = address.isReachable(2000);

            if (reachable) {
                ScanResult result = new ScanResult();
                result.setIpAddress(ip);
                result.setHostname(address.getCanonicalHostName());
                result.setReachable(true);
                return result;
            }
        } catch (IOException e) {
            // timeout/unreachable
        }
        return null;
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}