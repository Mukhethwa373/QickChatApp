/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickchatpart2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class QuickChatPart2Test {
    @BeforeEach
    public void setUp() {
        Message.clearMessagesForTesting();
}
    
    public QuickChatPart2Test() {
    }

    @Test
        public void testMessageLength() {

        Message valid = new Message(
                "1234567890", 0,
                "+27691040419", "Hello Mukhethwa");

        assertEquals(
                "Message ready to send.",
                valid.validateMessage());

        String longText = "A".repeat(251);

        Message invalid = new Message(
                "1234567890", 0,
                "+27691040419", longText);

        assertEquals(
                "Message exceeds 250 characters by 1, "
                + "please reduce the size.",
                invalid.validateMessage());
    }

    @Test
    public void testRecipientNumber() {

        Message valid = new Message(
                "1234567890", 0,
                "+27691040419", "Hello");

        assertEquals(
                "Cell phone number successfully captured.",
                valid.checkRecipientCell());

        Message invalid = new Message(
                "1234567890", 0,
                "08575975889", "Hello");

        assertEquals(
                "Cell phone number is incorrectly formatted or "
                + "does not contain an international code. "
                + "Please correct the number and try again.",
                invalid.checkRecipientCell());
    }

    @Test
    public void testMessageHash() {

        Message testMessage = new Message(
                "0012345678", 0,
                "+27691040419",
                "Hi Mukhethwa, can you join us for dinner tonight?");

        assertEquals(
                "00:0:HITONIGHT",
                testMessage.createMessageHash());
    }

    @Test
    public void testMessageID() {

        Message validID = new Message(
                "1234567890", 0,
                "+27691040419", "Hello");

        assertTrue(validID.checkMessageID());

        Message invalidID = new Message(
                "12345678901", 0,
                "+27691040419", "Hello");

        assertFalse(invalidID.checkMessageID());
    }

    @Test
    public void testSendMessage() {

        Message message = new Message(
                "1111111111", 0,
                "+27691040419", "Hello");

        assertEquals(
                "Message successfully sent.",
                message.SentMessage(1));

        assertEquals(1, message.returnTotalMessages());
    }

    @Test
    public void testDisregardMessage() {

        Message message = new Message(
                "2222222222", 0,
                "+27691040419", "Hello");

        assertEquals(
                "Press 0 to delete the message.",
                message.SentMessage(2));

        assertEquals(0, message.returnTotalMessages());
    }

    @Test
    public void testStoreMessage() {

        Message message = new Message(
                "3333333333", 0,
                "+27691040419", "Hello");

        assertEquals(
                "Message successfully stored.",
                message.SentMessage(3));

        assertEquals(1, Message.getStoredMessageCount());

        assertEquals(0, message.returnTotalMessages());
    }
}
    
    

