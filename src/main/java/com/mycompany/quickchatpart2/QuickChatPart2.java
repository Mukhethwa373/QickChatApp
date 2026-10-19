/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchatpart2;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Student
 */
public class QuickChatPart2 {
static Scanner input = new Scanner(System.in);

    private static Random random = new Random();
    private static Set<String> usedIDs = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("==============================");
        System.out.println("        QUICKCHAT");
        System.out.println("==============================");

        
        String correctUsername = "Mukhethwa";
        String correctPassword = "40419";

        boolean loggedIn = false;

        while (!loggedIn) {

            System.out.print("\nEnter username: ");
            String username = input.nextLine();

            System.out.print("Enter password: ");
            String password = input.nextLine();

            if (username.equals(correctUsername)
                    && password.equals(correctPassword)) {

                loggedIn = true;
                System.out.println("Login successful!");

            } else {

                System.out.println(
                        "Incorrect details. Please try again.");
            }
        }

        System.out.println("\nWelcome to QuickChat.");

        int numberOfMessages;

        do {
            System.out.print(
                    "How many messages would you like to enter? ");

            numberOfMessages = readNumber();

            if (numberOfMessages <= 0) {
                System.out.println(
                        "Enter a number greater than zero.");
            }

        } while (numberOfMessages <= 0);

        Message messageManager = new Message(
                "0000000000", 0, "+27691040419", "Example");

        int messagesProcessed = 0;
        int choice;

        do {

            System.out.println("\n========== MENU ==========");
            System.out.println("1. Send Messages");
            System.out.println("2. Show Recently Sent Messages");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");

            choice = readNumber();

            switch (choice) {

                case 1:

                    if (messagesProcessed >= numberOfMessages) {

                        System.out.println(
                                "You have entered all your messages.");

                        break;
                    }

                    while (messagesProcessed < numberOfMessages) {

                        System.out.println("\n--- New Message ---");

                        String messageID = generateMessageID();

                        int messageNumber = messagesProcessed;
                       
                        String recipient;

                        while (true) {

                            System.out.print(
                                    "Enter recipient number "
                                    + "with international code: ");

                            recipient = input.nextLine();

                            Message recipientCheck = new Message(
                                    messageID,
                                    messageNumber,
                                    recipient,
                                    "Test message");

                            String result =
                                    recipientCheck.checkRecipientCell();

                            if (result.equals(
                                    "Cell phone number successfully captured.")) {

                                System.out.println(result);
                                break;

                            } else {

                                System.out.println(result);
                            }
                        }

                        
                        String messageText;

                        while (true) {

                            System.out.print("Enter your message: ");
                            messageText = input.nextLine();

                            if (messageText.trim().isEmpty()) {

                                System.out.println(
                                        "Please enter a message.");

                            } else if (messageText.length() > 250) {

                                Message check = new Message(
                                        messageID,
                                        messageNumber,
                                        recipient,
                                        messageText);

                                System.out.println(
                                        check.validateMessage());

                            } else {

                                break;
                            }
                        }

                        Message newMessage = new Message(
                                messageID,
                                messageNumber,
                                recipient,
                                messageText);

                        System.out.println(
                                "Message ID generated: "
                                + newMessage.getMessageID());

                        System.out.println(
                                "Message Hash: "
                                + newMessage.getMessageHash());
                        
                        String actionResult = newMessage.SentMessage();

                        System.out.println(actionResult);
                        if (actionResult.equals(
                                "Message successfully sent.")) {

                            System.out.println("\nMessage Details");
                            System.out.println(
                                    "Message ID: "
                                    + newMessage.getMessageID());

                            System.out.println(
                                    "Message Hash: "
                                    + newMessage.getMessageHash());

                            System.out.println(
                                    "Recipient: "
                                    + newMessage.getRecipient());

                            System.out.println(
                                    "Message: "
                                    + newMessage.getMessage());
                        }

                        messagesProcessed++;

                        System.out.println(
                                "\nMessages processed: "
                                + messagesProcessed + " of "
                                + numberOfMessages);

                        if (messagesProcessed >= numberOfMessages) {
                            break;
                        }

                        System.out.print(
                                "Enter another message? (yes/no): ");

                        String answer = input.nextLine();

                        if (!answer.equalsIgnoreCase("yes")) {
                            break;
                        }
                    }

                    System.out.println(
                            "\nTotal messages successfully sent: "
                            + messageManager.returnTotalMessages());

                    break;

                case 2:
                    System.out.println(
                            messageManager.printMessages());
                    break;

                case 3:
                    System.out.println(
                            "Thank you for using QuickChat.");

                    System.out.println(
                            "Total messages successfully sent: "
                            + messageManager.returnTotalMessages());
                    break;

                default:

                    System.out.println(
                            "Invalid option. Choose 1, 2 or 3.");
            }

        } while (choice != 3);

        input.close();
    }

    public static String generateMessageID() {

        String id;
        do {

            long number = 1000000000L
                    + (long) (random.nextDouble()
                    * 9000000000L);

            id = String.valueOf(number);
        } while (usedIDs.contains(id));
        
        usedIDs.add(id);
        return id;
    }

    public static int readNumber() {

        while (true) {
            String value = input.nextLine();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.print(
                        "Please enter a valid number: ");
            }
        }
    }
}

      
      

