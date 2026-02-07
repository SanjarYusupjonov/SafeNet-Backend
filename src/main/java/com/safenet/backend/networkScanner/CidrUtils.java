package com.safenet.backend.networkScanner;

import org.apache.commons.net.util.SubnetUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;
import java.util.stream.Collectors;

public class CidrUtils {

    public static List<String> getAllIps(String cidr) {
        SubnetUtils subnetUtils = new SubnetUtils(cidr);
        subnetUtils.setInclusiveHostCount(true);

        String myIp = getLocalIp();

        return Arrays.stream(subnetUtils.getInfo().getAllAddresses())
                .filter(ip -> !ip.equals(myIp))
                .collect(Collectors.toList());
    }

    private static String getLocalIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();

                if (!ni.isUp() || ni.isLoopback()) continue;

                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof java.net.Inet4Address) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get local IP", e);
        }

        throw new RuntimeException("No IPv4 address found");
    }
}
