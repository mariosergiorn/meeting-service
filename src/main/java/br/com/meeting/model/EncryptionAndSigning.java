package br.com.meeting.model;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

public class EncryptionAndSigning {

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static String signAndEncrypt(String message, PrivateKey privateKey, PublicKey publicKey) throws Exception {
        // Assinar mensagem
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(message.getBytes());
        byte[] signedMessage = signature.sign();

        // Criptografar mensagem
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedMessage = cipher.doFinal(message.getBytes());

        // Concatenar assinatura e mensagem criptografada
        byte[] signedAndEncryptedMessage = new byte[signedMessage.length + encryptedMessage.length];
        System.arraycopy(signedMessage, 0, signedAndEncryptedMessage, 0, signedMessage.length);
        System.arraycopy(encryptedMessage, 0, signedAndEncryptedMessage, signedMessage.length, encryptedMessage.length);

        return Base64.getEncoder().encodeToString(signedAndEncryptedMessage);
    }

}
