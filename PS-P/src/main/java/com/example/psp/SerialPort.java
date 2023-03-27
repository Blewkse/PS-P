package com.example.psp;

public class SerialPort {
    public static void main(String[] args) {
        // List all available serial ports
        com.fazecast.jSerialComm.SerialPort[] ports = com.fazecast.jSerialComm.SerialPort.getCommPorts();
        for (com.fazecast.jSerialComm.SerialPort port : ports) {
            System.out.println(port.getSystemPortName());
        }

        // Connect to the correct serial port (replace "COM3" with your ESP32's port name)
        com.fazecast.jSerialComm.SerialPort esp32Port = com.fazecast.jSerialComm.SerialPort.getCommPort("tty.usbmodem01");
        esp32Port.setComPortParameters(115200, 8, 1, 0);
        esp32Port.setComPortTimeouts(com.fazecast.jSerialComm.SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        if (esp32Port.openPort()) {
            System.out.println("Connected to ESP32");

            // Send command to ESP32 to get firmware version (replace with the actual command)
            String command = "get_firmware_version\n";
            esp32Port.writeBytes(command.getBytes(), command.length());

            // Read response from ESP32
            StringBuilder response = new StringBuilder();
            while (esp32Port.bytesAvailable() == 0) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            while (esp32Port.bytesAvailable() > 0) {
                byte[] readBuffer = new byte[esp32Port.bytesAvailable()];
                int numRead = esp32Port.readBytes(readBuffer, readBuffer.length);
                response.append(new String(readBuffer, 0, numRead));
            }

            System.out.println("Firmware version: " + response.toString().trim());

            // Close the connection to the ESP32
            esp32Port.closePort();
        } else {
            System.out.println("Failed to connect to ESP32");
        }
    }
}