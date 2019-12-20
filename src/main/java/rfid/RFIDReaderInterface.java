package main.java.rfid;

import com.fazecast.jSerialComm.*;
import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * <h1>RFID Reader Interface</h1>
 * This program will allow you to communicate with the RFID Card Scanner
 * (to be implemented with a Point of Sale System) through serial commands,
 * without having to mess with the serial commands and communication yourself.
 * @author Jeremy Andrews Zantua
 * @version 1.0
 * @since 2019-11-5
 */

public class RFIDReaderInterface {
    private SerialPort selectedPort; // The selected port to be used
    private Scanner serialReader;
    private PrintWriter serialWriter;
    private byte[] bytesRead;
    private final String RFIDcacheFilePath = "etc\\rfid-cache.file";
    private boolean writeDataToCache = false;
    private boolean deviceReady = false;
    private boolean serialCommDebugging = true; // Set to true when checking data sent/received through serial
    private int lastCommand = 0;
    private long time;

    public RFIDReaderInterface() {
        /**
         * This constructor will prepare and establish a connection to allow you to communicate
         * with the Arduino with ease.
         */
        System.out.println("==== RFIDReaderInterface: Start of Initialization ====");

        System.out.println("> Fetching available COM Ports...");
        SerialPort availablePorts[] = SerialPort.getCommPorts(); // Gets all available COM Ports
        System.out.println("> " + availablePorts.length + " COM Port/s found");

        int portNumbers = 1; // gives port numbers when printed
        // lists all available ports to console
        for (SerialPort sp: availablePorts) {
            System.out.println("  " + portNumbers++ + ". " + sp.getDescriptivePortName());
        }

        // Enhanced for loop to iterate through all available COM ports
        // Each port will be tried 3 times before moving on to the next available port
        System.out.println("> Attempting to open serial port");
        boolean portOpened = false;
        availablePortsLoop:
        for (SerialPort sp: availablePorts) {
            System.out.println("> Attempting to open " + sp.getSystemPortName());
            sp.setComPortParameters(115200, 8, 1, 0); // default connection settings for arduino
            sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

            portOpenAttemptsLoop:
            // This loop will only iterate 3 times, or 3 attempts to open the currently selected COM port
            for (int attempt = 0; attempt < 3; attempt++) {
                if (sp.openPort()) {
                    // if port is successfully opened
                    System.out.println("> " + sp.getSystemPortName() + " successfully opened");
                    selectedPort = sp; // assigns opened port to selectedPort;
                    portOpened = true;
                    break portOpenAttemptsLoop; // stop attempting to open other ports
                }
                else {
                    // if port cannot be opened
                    System.out.println("[!] Cannot open \"" + sp.getSystemPortName() + "\". Will try again... (Attempt " + (attempt + 1) + ")");
                }
            }

            // after a port is opened, or all attempts for the current port are exhausted,
            // check if a port has been successfully opened
            if (portOpened) {
                break availablePortsLoop;
            }
            else {
                System.out.println("[!] Failed to open \"" + sp.getSystemPortName() + "\" after 3 attempts.");
            }
        }

        // if port is opened, initialize a data listener for receiving responses from the Arduino through serial
        if (portOpened) {
            selectedPort.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }

                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                        return;
                    bytesRead = new byte[selectedPort.bytesAvailable()];
                    int numRead = (selectedPort.readBytes(bytesRead, bytesRead.length));
                    // For checking data received through serial, uncomment the line below
                    if (serialCommDebugging) {
                        System.out.print("[RECEIVED] ");
                        System.out.print(numRead + " bytes, dec: ");
                        for (int x = 0; x < numRead; x++) {
                            System.out.print(bytesRead[x]);
                            if (x != (numRead - 1)) {
                                System.out.print(" ");
                            }
                        }
                        System.out.print(", char:\"");
                        for (int x = 0; x < numRead; x++) {
                            System.out.print((char)bytesRead[x]);
                            if (x != (numRead - 1)) {
                                System.out.print(" ");
                            }
                        }
                        System.out.println("\"");
                    }

                    // Writes the next received data to a cache file
                    if (writeDataToCache) {
                        writeDataToCache = false;
                        translateCacheData(bytesRead);
                    }

                    if (!deviceReady) {
                        if (bytesRead[0] == -128) {
                            sendByte(129);
                        }
                        else if (bytesRead[0] == 49)  {
                            deviceReady = true;
                        }
                    }
                }
            });
            serialReader = new Scanner(selectedPort.getInputStream()); // Start input stream for receiving data over serial
            serialWriter = new PrintWriter(selectedPort.getOutputStream()); // Start output stream for receiving data over serial
            // TODO Continue documenting code from here
            System.out.println("> Establishing connection with device...");
            while (!deviceReady) {
                System.out.print("");
            }
            System.out.println("> Connection Established!");
        }
        else {
            System.out.println("[!] Could not open any ports.");
        }

        System.out.println("===== RFIDReaderInterface: End of Initialization =====");
    }

    public void sendByte(int input) {
        /**
         * This method sends a byte to serial.
         * This is where Java will send commands to the Arduino.
         * @param intput This will be the byte that will be sent to serial.
         */
        if (serialCommDebugging) {
            System.out.println("[SENT] dec:" + input + ", char:\"" + (char)input + "\"");
        }
        serialWriter.print((char)input);
        serialWriter.flush();
    }

    public void cancelOperation() {
        sendByte(131);
        lastCommand = 0;
        writeDataToCache = false;
    }

    public void scanBasic() {
        sendByte(132);
        lastCommand = 132;
        writeDataToCache = true;
    }

    public void scanExtensive() {
        sendByte(133);
        lastCommand = 133;
        writeDataToCache = true;
    }

    public void gsmStatus() {
        sendByte(134);
        lastCommand = 134;
        writeDataToCache = true;
    }

    public void gsmSignal() {
        sendByte(135);
        lastCommand = 135;
        writeDataToCache = true;
    }

    public void gsmSendSMS(String number, String message) {
        sendByte(136);

        sendByte(2);
        for (char c: number.toCharArray()) {
            sendByte(c);
        }
        sendByte(3);

        sendByte(2);
        for (char c: message.toCharArray()) {
            sendByte(c);
        }
        sendByte(3);

        lastCommand = 136;
    }

    public void challenge(String passcode) {
        sendByte(139);
        sendByte(2);
        for (char c: passcode.toCharArray()) {
            sendByte(c);
        }
        sendByte(3);

        lastCommand = 139;
        writeDataToCache = true;
    }

    public void newPIN() {
        sendByte(141);
        lastCommand = 141;
        writeDataToCache = true;
    }

    public void testConnection() {
        sendByte(142);
        lastCommand = 142;
        writeDataToCache = true;
        time = System.currentTimeMillis();
    }

    private boolean translateCacheData(byte data[]) {
        boolean returnValue = true;

        // Basic Scan
        if (lastCommand == 132) {
            String tagID = "";
            for (byte b: data) {
                tagID += String.format("%02X", b);
            }
            writeToCache("scanBasic=" + tagID, RFIDcacheFilePath);
        }
        // Extensive Scan
        else if (lastCommand == 133) {
            String tagID = "";
            for (byte b: data) {
                tagID += String.format("%02X", b);
            }
            writeToCache("scanExtensive=" + tagID, RFIDcacheFilePath);
        }
        // Check GSM
        else if (lastCommand == 134) {
            switch ((char)data[0]) {
                case '0':
                    writeToCache("gsmStatus=0", RFIDcacheFilePath);
                    break;
                case '1':
                    writeToCache("gsmStatus=1", RFIDcacheFilePath);
                    break;
                default:
                    System.out.println("[translateCacheData] Invalid result \"" + data[0] + "\" for checkGSM()");
                    returnValue = false;
                    break;
            }
        }
        // Check GSM Signal
        else if (lastCommand == 135) {
            String signal = "";
            for (byte b: data) {
                signal += (char)b;
            }
            writeToCache("gsmSignal=" + signal,RFIDcacheFilePath);
        }
        // Challenge
        else if (lastCommand == 139) {
            switch ((char) data[0]) {
                case '0':
                    writeToCache("challengeResult=0", RFIDcacheFilePath);
                    break;
                case '1':
                    writeToCache("challengeResult=1", RFIDcacheFilePath);
                    break;
                default:
                    System.out.println("[translateCacheData] Invalid result \"" + data[0] + "\" for challenge()");
                    returnValue = false;
                    break;
            }
        }
        // New PIN
        else if (lastCommand == 141) {
            String pin = "";
            for (byte b: data) {
                pin += (char)b;
            }
            writeToCache("newPIN=" + pin, RFIDcacheFilePath);
        }
        // Test Connection
        else if (lastCommand == 142) {
            switch ((char) data[0]) {
                case '0':
                    writeToCache("connectionStatus=0", RFIDcacheFilePath);
                    break;
                case '1':
                    writeToCache("connectionStatus=1", RFIDcacheFilePath);
                    break;
                default:
                    System.out.println("[translateCacheData] Invalid result \"" + data[0] + "\" for testConnection()");
                    returnValue = false;
                    break;
            }
        }

        lastCommand = 0;
        return returnValue;
    }

    private boolean writeToCache(String data, String path) {
        /**
         * This method writes data to a specified cache file.
         * @param data This will be the data written to the file.
         * @param path This is where the file is/will be located.
         */
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path));
            writer.write(data);
            writer.flush();
            writer.close();
            if (serialCommDebugging) {
                System.out.println("[WRITE] " + data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (serialCommDebugging) {
                System.out.println("[WRITE UNSUCCESSFUL]");
            }
            return false;
        }
        return true;
    }

    public void clearCache(){
        writeToCache("",RFIDcacheFilePath);
    }
}
