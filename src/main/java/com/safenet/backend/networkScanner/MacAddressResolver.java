package com.safenet.backend.networkScanner;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class MacAddressResolver {

    protected String getMacAddress(String ip) {
        try {
            // Run the system command "arp -a <IP>"
            Process process = Runtime.getRuntime().exec("arp -a " + ip);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(ip)) {
                    // Extract MAC address from the ARP output
                    String mac = line.replaceAll(".*((?:[0-9a-fA-F]{2}[:-]){5}[0-9a-fA-F]{2}).*", "$1");
                    System.out.println("MAC address of " + ip + " is: " + mac);
                    return mac;
                }
            }
            System.out.println("MAC address not found. Make sure the device is reachable and in the ARP table.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
