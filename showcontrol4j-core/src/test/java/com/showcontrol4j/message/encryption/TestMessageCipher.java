package com.showcontrol4j.message.encryption;

import com.showcontrol4j.message.SCFJMessage;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Test for the {@link MessageCipher} class.
 *
 * @author James Hare
 */
public class TestMessageCipher {

    private final String TEST_MESSAGE_STRING = "TEST:1234567891234";
    private final SCFJMessage TEST_SCFJ_MESSAGE = new SCFJMessage.Builder()
            .withCommand("TEST")
            .withStartTime(1234567891234L).build();

    @Test
    public void testEncryptMessageToString() {
        String testEncryptedMessageString = null;
        try {
            testEncryptedMessageString = MessageCipher.encryptMessageToString(TEST_SCFJ_MESSAGE);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException |
                NoSuchPaddingException | InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
            System.out.println("An error occurred while Encrypting the message." + e);
        }
        assertEquals("e0FMINTSH1780MOGuiFJ1QI6j9INeLHE3Qoq+hkIF1k=", testEncryptedMessageString);
    }

    @Test
    public void testEncryptMessageStringToString() {
        String testEncryptedMessageString = null;
        try {
            testEncryptedMessageString = MessageCipher.encryptMessageStringToString(TEST_MESSAGE_STRING);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException |
                NoSuchPaddingException | InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
            System.out.println("An error occurred while Encrypting the message." + e);
        }
        assertEquals("e0FMINTSH1780MOGuiFJ1QI6j9INeLHE3Qoq+hkIF1k=", testEncryptedMessageString);
    }

    @Test
    public void testDecryptStringToMessage() {
        String testDecryptedMessageString = null;
        try {
            testDecryptedMessageString = MessageCipher.decryptStringToMessageString("e0FMINTSH1780MOGuiFJ1QI6j9INeLHE3Qoq+hkIF1k=");
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException |
                NoSuchPaddingException | InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
            System.out.println("An error occurred while decrypting the message." + e);
        }
        assertEquals(TEST_MESSAGE_STRING, testDecryptedMessageString);
    }

    @Test
    public void testDecryptStringToMessageString() {
        SCFJMessage testDecryptedMessage = null;
        try {
            testDecryptedMessage = MessageCipher.decryptStringToMessage("e0FMINTSH1780MOGuiFJ1QI6j9INeLHE3Qoq+hkIF1k=");
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException |
                NoSuchPaddingException | InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
            System.out.println("An error occurred while decrypting the message." + e);
        }
        assertThat(TEST_SCFJ_MESSAGE, instanceOf(SCFJMessage.class));
        assertEquals(TEST_SCFJ_MESSAGE.getCommand(), testDecryptedMessage.getCommand());
        assertEquals(TEST_SCFJ_MESSAGE.getStartTime(), testDecryptedMessage.getStartTime());
        assertEquals(TEST_MESSAGE_STRING, testDecryptedMessage.toString());
    }

}
