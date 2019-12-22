package main.java.rfid;

import java.util.Scanner;

public class RFIDMain {
    public static Scanner sc = new Scanner(System.in);
    public static RFIDReaderInterface device = new RFIDReaderInterface();
    public static void main(String[] args) {
        while (true) {
            System.out.print("User > ");
            String input = sc.nextLine();

            if (validNumber(input)) {
                device.sendByte(Integer.parseInt(input));
            }
            else {
                String splitInput[] = input.split("\\s");
                switch (splitInput[0]) {
                    case "cancel":
                        device.cancelOperation();
                        break;

                    case "gsm":
                        if (splitInput.length == 2) {
                            if (splitInput[1].equals("status")) {
                                device.gsmStatus();
                            }
                            else if (splitInput[1].equals("signal")) {
                                device.gsmSignal();
                            }
                            else if (splitInput[1].equals("send")) {
                                System.out.print("gsm send > Enter phone number: ");
                                String number = sc.nextLine();
                                System.out.println("gsm send > Enter \"<END>\" when you are done typing your message. Pressing enter will create a new line.");
                                String message = "";
                                int lines = 1;
                                while (true) {
                                    System.out.print("gsm send > Message Line " + lines + ": ");
                                    String tempInput = sc.nextLine();
                                    if(tempInput.equals("<END>")) {
                                        break;
                                    }
                                    else {
                                        message += tempInput;
                                        message += "\n";
                                        lines++;
                                    }
                                }

                                device.gsmSendSMS(number, message);
                            }
                            else if (splitInput[1].equals("simstatus")) {
                                device.gsmSIMPresent();
                            }
                        }
                        else if (splitInput.length > 2) {
                            invalidCommand(input);
                        }
                        else {
                            invalidCommand(input);
                        }
                        break;

                    case "scan":
                        if (splitInput[1].equals("" +
                                "0")) {
                            device.scanBasic();
                        }
                        else if (splitInput[1].equals("1")) {
                            device.scanExtensive();
                        }
                        else {
                            invalidCommand(input);
                        }
                        break;

                    case "pin":
                        if (splitInput.length == 3) {
                            if (splitInput[1].equals("test")) {
                                device.challenge(splitInput[2]);
                            }
                            else {
                                invalidCommand(input);
                            }
                        }
                        else if (splitInput.length == 2) {
                            if (splitInput[1].equals("create")) {
                                device.newPIN();
                            }
                            else {
                                invalidCommand(input);
                            }
                        }
                        break;

                    case "test":
                        device.testConnection();
                        break;

                    case "quit":
                        device.disconnect();
                        System.exit(0);
                        break;

                    default:
                        invalidCommand(input);
                        break;
                }
            }


        }
    }

    private static void invalidCommand(String command) {
        System.out.println("[ERROR] \"" + command + "\" is not recognized as a valid command");
    }

    private static boolean validNumber(String input) {
        boolean returnValue = true;
        for (char c: input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return returnValue;
    }
}