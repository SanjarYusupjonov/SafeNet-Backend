package com.safenet.backend.networkScanner;

import com.safenet.backend.dtos.ScanResult;

import java.util.List;

public interface NetworkScanner {

    List<ScanResult> scan(String cidr);
}
