/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchatpart2;

import com.mycompany.quickchatpart2.QuickChatPart2;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Student
 */
class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static ArrayList<Message> storedMessages = new ArrayList<>();

    public Message(String messageID, int messageNumber,
                   String recipient, String message) {

        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }

    public boolean checkMessageID() {
        return messageID != null
                && messageID.length() <= 10;
    }

    public String checkRecipientCell() {

        if (recipient != null
                && recipient.matches("\\+[1-9][0-9]{7,14}")) {
            
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or "
                + "does not contain an international code. "
                + "Please correct the number and try again.";
    }

    public String validateMessage() {

        if (message != null && message.length() <= 250) {
            return "Message ready to send.";
        }

        int length = message == null ? 0 : message.length();
        int extra = length - 250;

        return "Message exceeds 250 characters by "
                + extra + ", please reduce the size.";
    }

    // Create the message hash
    public String createMessageHash() {

        String firstTwoDigits = messageID.substring(0, 2);

        String cleanedMessage = message.trim();

        if (cleanedMessage.isEmpty()) {
            return (firstTwoDigits + ":"
                    + messageNumber + ":").toUpperCase();
        }

        String[] words = cleanedMessage.split("\\s+");

        String firstWord = words[0].replaceAll(
                "[^a-zA-Z0-9]", "");

        String lastWord = words[words.length - 1].replaceAll(
                "[^a-zA-Z0-9]", "");

        return (firstTwoDigits + ":"
                + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    public String SentMessage() {

        System.out.println("\nChoose what to do with the message:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");
        System.out.print("Enter your choice: ");

        int choice = QuickChatPart2.readNumber();

        return SentMessage(choice);
    }

    public String SentMessage(int choice) {

        switch (choice) {
            case 1:
                sentMessages.add(this);
                return "Message successfully sent.";

            case 2:
                return "Press 0 to delete the message.";

            case 3:
                storedMessages.add(this);

                if (storeMessage()) {
                    return "Message successfully stored.";
                }

                storedMessages.remove(this);
                return "Message could not be stored.";

            default:
                return "Invalid choice. Please try again.";
        }
    }

    // Display all messages sent during the current session
    public String printMessages() {

        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }

        StringBuilder output = new StringBuilder();

        for (Message msg : sentMessages) {

            output.append("\nMessage ID: ")
                    .append(msg.messageID);

            output.append("\nMessage Hash: ")
                    .append(msg.messageHash);

            output.append("\nRecipient: ")
                    .append(msg.recipient);

            output.append("\nMessage: ")
                    .append(msg.message);

            output.append("\n--------------------------\n");
        }

        return output.toString();
    }

    public int returnTotalMessages() {
        return sentMessages.size();
    }

    public boolean storeMessage() {

        try (FileWriter writer =
                     new FileWriter("messages.json")) {

            writer.write("{\n  \"messages\": [\n");

            for (int i = 0; i < storedMessages.size(); i++) {

                Message msg = storedMessages.get(i);

                writer.write("    {\n");
                writer.write("      \"messageID\": \""
                        + escapeJSON(msg.messageID) + "\",\n");
                writer.write("      \"messageHash\": \""
                        + escapeJSON(msg.messageHash) + "\",\n");
                writer.write("      \"recipient\": \""
                        + escapeJSON(msg.recipient) + "\",\n");
                writer.write("      \"message\": \""
                        + escapeJSON(msg.message) + "\"\n");
                writer.write("    }");

                if (i < storedMessages.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("  ]\n}\n");
            return true;

        } catch (IOException e) {

            System.out.println(
                    "Error storing message: " + e.getMessage());
            return false;
        }
    }

    private String escapeJSON(String text) {

        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    static void clearMessagesForTesting() {
        sentMessages.clear();
        storedMessages.clear();
    }

    static int getStoredMessageCount() {
        return storedMessages.size();
    }
}



