package com.showcontrol4j.message.encryption;

import com.showcontrol4j.exchange.MessageExchange;
import com.showcontrol4j.message.SCFJMessage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * A helper class with static methods to allow for the encryption and decryption of a
 * {@link SCFJMessage} object.
 *
 * @author James Hare
 */
public class MessageCipher {

    // private constructor so that the class cannot be instantiated.
    private MessageCipher() {}

    /**
     * Encrypts a {@link SCFJMessage} to an encrypted String.
     *
     * @param scfjMessage a {@link SCFJMessage} object.
     * @return {@link String} a {@link SCFJMessage} object encrypted as a String.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     */
    public static String encryptMessageToString(SCFJMessage scfjMessage) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        final String encryptionKey = "ABCDEFGHIJKLMNOP";
        byte[] key = encryptionKey.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivparameterspec = new IvParameterSpec(key);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
        byte[] cipherText = cipher.doFinal(scfjMessage.toString().getBytes("UTF8"));
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(cipherText);
    }

    /**
     * Encrypts a {@link SCFJMessage} as a String to an encrypted String.
     *
     * @param scfjMessageAsString A {@link SCFJMessage} object as a String.
     * @return {@link String} a {@link SCFJMessage} object encrypted as a String.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     */
    public static String encryptMessageStringToString(String scfjMessageAsString) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        final String encryptionKey = "ABCDEFGHIJKLMNOP";
        byte[] key = encryptionKey.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivparameterspec = new IvParameterSpec(key);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
        byte[] cipherText = cipher.doFinal(scfjMessageAsString.getBytes("UTF8"));
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(cipherText);
    }

    /**
     * Decrypts an encrypted {@link SCFJMessage} object as a String to a {@link SCFJMessage} object.
     *
     * @param encryptedMessage a {@link SCFJMessage} object.
     * @return a {@link SCFJMessage} object.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     */
    public static SCFJMessage decryptStringToMessage(String encryptedMessage) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        final String encryptionKey = "ABCDEFGHIJKLMNOP";
        byte[] key = encryptionKey.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivparameterspec = new IvParameterSpec(key);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] cipherText = decoder.decode(encryptedMessage.getBytes("UTF8"));
        String decryptedMessageString = new String(cipher.doFinal(cipherText), "UTF-8");
        String[] deconstructedMessageString = decryptedMessageString.split(":");
        return new SCFJMessage.Builder()
                .withCommand(deconstructedMessageString[0])
                .withStartTime(Long.parseLong(deconstructedMessageString[1]))
                .build();
    }

    /**
     * Decrypts an encrypted {@link SCFJMessage} object as a String to a {@link SCFJMessage} object
     * as a String.
     *
     * @param encryptedMessage {@link String} a {@link SCFJMessage} object as a String.
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     */
    public static String decryptStringToMessageString(String encryptedMessage) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        final String encryptionKey = "ABCDEFGHIJKLMNOP";
        byte[] key = encryptionKey.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivparameterspec = new IvParameterSpec(key);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] cipherText = decoder.decode(encryptedMessage.getBytes("UTF8"));
        return new String(cipher.doFinal(cipherText), "UTF-8");
    }

}
