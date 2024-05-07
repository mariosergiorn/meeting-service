package br.com.meeting.model;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class DecryptionAndVerification {

    public static String decryptAndVerify(String signedAndEncryptedMessage, PrivateKey privateKey, PublicKey publicKey) throws Exception {
        // Decodificar mensagem
        byte[] signedAndEncryptedMessageBytes = Base64.getDecoder().decode(signedAndEncryptedMessage);

        // Separar assinatura e mensagem criptografada
        int signatureLength = 256; // Tamanho da assinatura (256 bytes para RSA com SHA-256)
        byte[] signedMessage = new byte[signatureLength];
        byte[] encryptedMessage = new byte[signedAndEncryptedMessageBytes.length - signatureLength];
        System.arraycopy(signedAndEncryptedMessageBytes, 0, signedMessage, 0, signatureLength);
        System.arraycopy(signedAndEncryptedMessageBytes, signatureLength, encryptedMessage, 0, encryptedMessage.length);

        // Verificar assinatura
        Signature verifySignature = Signature.getInstance("SHA256withRSA");
        verifySignature.initVerify(publicKey);
        verifySignature.update(encryptedMessage);
        boolean isValidSignature = verifySignature.verify(signedMessage);

        // Descriptografar mensagem
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedMessage);

        return new String(decryptedMessage);
    }

}
