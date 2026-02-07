package com.safenet.backend.services.network;

import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

@Component
public class NetworkUtils {

    public static String getLocalCidr() throws Exception {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            if (!ni.isUp() || ni.isLoopback()) continue;

            for (var addr : ni.getInterfaceAddresses()) {
                InetAddress ip = addr.getAddress();
                if (ip.isSiteLocalAddress()) {
                    int prefix = addr.getNetworkPrefixLength();
                    return ip.getHostAddress() + "/" + prefix;  // e.g., 192.168.1.101/24
                }
            }
        }
        throw new RuntimeException("No local network found");
    }
}
