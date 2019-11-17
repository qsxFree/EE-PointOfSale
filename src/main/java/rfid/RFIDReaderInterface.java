import com.fazecast.jSerialComm.*;
import java.io.*;
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
    private String lastStringRead = "";
    private byte[] bytesRead;
    private String RFIDcacheFilePath = "etc\\rfid-cache.file";
    private boolean writeDataToCache = false;
    private boolean deviceReady = false;
    private boolean serialCommDebugging = false; // Set to true when checking data sent/received through serial

    public RFIDReaderInterface() {
        /**
         * This constructor will prepare and establish a connection to allow you to communicate
         * with the Arduino with ease.
         */
        System.out.println("===== RFIDReaderInterface by Cyphred =====");

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
                    int numRead = (selectedPort.readBytes(bytesRead, bytesRead.length) - 2);
                    try {
                        lastStringRead = new String(bytesRead, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    // For checking data received through serial, uncomment the line below
                    if (serialCommDebugging) {
                        System.out.print("[RECEIVED] ");
                        System.out.println(numRead + " bytes read, " + lastStringRead.length() + " characters : \"" + lastStringRead + "\"");
                    }

                    // Writes the next received data to a cache file
                    if (writeDataToCache) {
                        writeDataToCache = false;
                        writeToCache(getLastStringRead(), RFIDcacheFilePath);
                    }

                    if (!deviceReady) {
                        if (getLastStringRead().equals("99")) {
                            serialPrint("start");
                        }
                        else if (getLastStringRead().equals("1")) {
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

    public void scan() {
        /**
         * This method tells the Arduino to ask the customer to scan their RFID card,
         * then writes the device's response to cache file.
         */
        writeDataToCache = true;
        serialPrint("scan");
    }

    public void challenge(String passcode) {
        /**
         * This method is for authentication of the ownership of a scanned RFID Card,
         * then writes the device's response to cache file.
         */
        writeDataToCache = true;
        serialPrint("challenge\n" + passcode);
    }

    public void newPasscode() {
        /**
         * This method will tell the Arduino to ask the customer to enter a 6-digit PIN.
         * This can be used when creating a new PIN, or changing an existing one.
         * It will ask the customer to enter the PIN twice for confirmation,
         * then writes the device's response to cache file.
         */
        writeDataToCache = true;
        serialPrint("newpass");
    }

    public void checkRFIDStatus() {
        /**
         * This method checks the RFID module's status with the Arduino,
         * then writes the device's response to cache file.
         */
        writeDataToCache = true;
        serialPrint("check\nnfc");
    }

    private void serialPrint(String input) {
        /**
         * This method sends a String to serial.
         * This is where Java will send commands to the Arduino.
         * @param input This will be the String that will be sent to serial.
         */
        if (serialCommDebugging) {
            System.out.println("[SENT] " + input);
        }
        serialWriter.print(input);
        serialWriter.flush();
    }

    private void writeToCache(String data, String path) {
        /**
         * This method writes data to a specified cache file.
         * @param data This will be the data written to the file.
         * @param path This is where the file is/will be located.
         */
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path));
            writer.write(data);
            writer.close();
            if (serialCommDebugging) {
                System.out.println("[WRITE] " + data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (serialCommDebugging) {
                System.out.println("[WRITE UNSUCCESSFUL]");
            }
        }
    }

    private String getLastStringRead() {
        /**
         * This method will fetch the last received data from the Arduino through serial.
         * It does not read the last two bytes directly read from the serial because they are line breaks.
         * @return String This returns the data last received through serial in the form of a String.
         */
        String returnValue = "";
        for (int x = 0; x < lastStringRead.length() - 2; x++) {
            returnValue += lastStringRead.toCharArray()[x];
        }
        return returnValue;
    }

    public void clearLastStringRead() {
        /**
         * Clears the last read string.
         */
        lastStringRead = "";
    }
}
