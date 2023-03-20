package com.example.psp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ListVolumes {

    public static void main(String[] args) {
        String osName = System.getProperty("os.name").toLowerCase();

        try {
            if (osName.contains("win")) {
                getWindowsVolumeLabels();
            } else if (osName.contains("mac")) {
                getMacVolumeLabels();
            } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
                getLinuxVolumeLabels();
            } else {
                System.out.println("Unsupported operating system.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void getWindowsVolumeLabels () throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("wmic", "logicaldisk", "where", "drivetype=2", "get", "deviceid,volumename");
        Process process = pb.start();
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty() && !line.startsWith("DeviceID")) {
                System.out.println(line.trim());
            }
        }
    }

    private static void getMacVolumeLabels () throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("diskutil", "list");
        Process process = pb.start();
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                System.out.println(line.trim());
            }
        }
    }

    private static void getLinuxVolumeLabels () throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("lsblk", "-o", "NAME,LABEL");
        Process process = pb.start();
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                System.out.println(line.trim());
            }
        }
    }

}
