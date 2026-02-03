package com.safenet.backend.networkScanner;

import org.apache.commons.net.util.SubnetUtils;
import java.util.Arrays;
import java.util.List;

public class CidrUtils {

    public static List<String> getAllIps(String cidr) {
        SubnetUtils subnetUtils = new SubnetUtils(cidr);
        subnetUtils.setInclusiveHostCount(true); // Include network and broadcast addresses

        String[] addresses = subnetUtils.getInfo().getAllAddresses();
        return Arrays.asList(addresses);
    }
}