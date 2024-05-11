package br.com.meeting.config;

import br.com.meeting.utils.Constantes;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.*;
import java.util.Base64;

@Configuration
public class KeyManager {

    KeyPair keyPair;
    PrivateKey privateKey;
    PublicKey publicKey;

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public KeyManager() throws Exception {
        try {

            keyPair = generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();

            File chavePrivadaFile = new File(Constantes.PATH_CHAVE_PRIVADA);
            File chavePublicaFile = new File(Constantes.PATH_CHAVE_PUBLICA);

            // Cria os arquivos para armazenar a chave Privada e a chave Publica
            if (chavePrivadaFile.getParentFile() != null) {
                chavePrivadaFile.getParentFile().mkdirs();
            }

            if (chavePublicaFile.getParentFile() != null) {
                chavePublicaFile.getParentFile().mkdirs();
            }

            try (ObjectOutputStream keyPublicOS = new ObjectOutputStream(
                    new FileOutputStream(chavePublicaFile))) {
                keyPublicOS.writeObject(keyPair.getPublic());
            }

            try (ObjectOutputStream keyPrivateOS = new ObjectOutputStream(
                    new FileOutputStream(chavePrivadaFile))) {
                keyPrivateOS.writeObject(keyPair.getPrivate());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String signAndEncrypt(String message) throws Exception {
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
