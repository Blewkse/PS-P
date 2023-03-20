package com.example.psp;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WriteInFile {
    public static void main(String[] args) {
        String targetVolumeLabel = "GALAXIA"; // Replace this with the volume label of your target device
        String fileName = "/labite.py";
        String fileContent = "# This is a Python file created by Java\nprint('Hello, world!')";
        String osName = System.getProperty("os.name").toLowerCase();
        boolean isDeviceFound = false;

        try {
            List<Path> removableDevices = new ArrayList<>();

            if (osName.contains("win")) {
                removableDevices = getWindowsRemovableDevices(targetVolumeLabel);
            } else if (osName.contains("mac")) {
                removableDevices = getMacRemovableDevices(targetVolumeLabel);
            } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
                removableDevices = getLinuxRemovableDevices();
            } else {
                System.out.println("Unsupported operating system.");
                return;
            }

            for (Path deviceRoot : removableDevices) {
                String deviceLabel = deviceRoot.toString();

               // if (deviceLabel.equalsIgnoreCase(targetVolumeLabel)) {
                    isDeviceFound = true;
                    System.out.println("Device found: " + deviceRoot);

                    try {
                        FileWriter writer = new FileWriter(deviceRoot + fileName);
                        writer.write(fileContent);
                        writer.close();
                        System.out.println("Python file created successfully!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
           // }

            if (!isDeviceFound) {
                System.out.println("No removable device found with volume label: " + targetVolumeLabel);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        /*try {
            String portName = getPortName(osName);
            //Path Mac Selmene
            String firmwarePath = "/Users/selmene/Developer/www/ynov/Desktop/PS-P/FirmToFlash/firmware-1-0-beta-28-pcb-1-0-3.bin";
            String[] command = {"esptool.py", "--port", "/dev/" + portName, "write_flash", "0x0000", firmwarePath};
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true); // Combine standard output and error output
            Process process = pb.start();
            process.waitFor(); // Wait for the process to finish executing
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getPortName(String osName) throws IOException {
        String portName = "";

        if (osName.contains("mac")) {
            ProcessBuilder pb = new ProcessBuilder("ls", "/dev/");
            pb.redirectErrorStream(true); // Combine standard output and error output
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("tty.usbmodem")) {
                    portName = line.trim();
                    break;
                }
            }
        } else if (osName.contains("win")) {
            // Windows-specific code to get the port name
            ProcessBuilder pb = new ProcessBuilder("mode");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("COM")) {
                    portName = line.split(":")[0].trim();
                    break;
                }
            }
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            // Linux-specific code to get the port name
            ProcessBuilder pb = new ProcessBuilder("ls", "/dev/tty*");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ttyUSB") || line.contains("ttyACM")) {
                    portName = line;
                    break;
                }
            }
        } else {
            System.out.println("Unsupported operating system.");
        }
        return portName;
*/
    }

    private static List<Path> getWindowsRemovableDevices(String targetVolumeLabel) throws IOException, InterruptedException {
        List<Path> devices = new ArrayList<>();
        ProcessBuilder pb = new ProcessBuilder("wmic", "logicaldisk", "where", "drivetype=2", "get", "deviceid,volumename");
        Process process = pb.start();
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty() && !line.startsWith("DeviceID") && line.contains(targetVolumeLabel)) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 2) {
                    devices.add(Paths.get(parts[0]));
                }
            }
        }
        return devices;
    }

    private static List<Path> getMacRemovableDevices(String targetVolumeLabel) throws IOException, InterruptedException {
        List<Path> devices = new ArrayList<>();
        ProcessBuilder pb = new ProcessBuilder("df", "-H");
        Process process = pb.start();
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("msdos://disk8s1/") || line.startsWith("msdos://disk5s1/") && line.contains(targetVolumeLabel)) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 9) {
                    String deviceRoot = parts[0];
                    String mountPoint = parts[8];
                    devices.add(Paths.get(deviceRoot).resolve(mountPoint));
                    break;
                }
            }
        }
        return devices;
    }


    private static List<Path> getLinuxRemovableDevices() throws IOException, InterruptedException {
        List<Path> devices = new ArrayList<>();
        ProcessBuilder pb = new ProcessBuilder("lsblk", "-o", "MOUNTPOINT,LABEL");
        Process process = pb.start();
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.trim().split("\\s+", 2);
                if (parts.length == 2) {
                    devices.add(Paths.get(parts[0], parts[1]));
                }
            }
        }
        return devices;
    }
}
