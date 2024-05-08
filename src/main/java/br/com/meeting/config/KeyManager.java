package br.com.meeting.config;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component
public class KeyManager {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public static final String ALGORITHM = "RSA";

    public static final String PATH_CHAVE_PRIVADA = "C:/Keys/private.key";

    public static final String PATH_CHAVE_PUBLICA = "C:/Keys/public.key";

    public KeyManager() {

        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(2048);
            final KeyPair key = keyGen.generateKeyPair();

            this.privateKey = key.getPrivate();
            this.publicKey = key.getPublic();

            File chavePrivadaFile = new File(PATH_CHAVE_PRIVADA);
            File chavePublicaFile = new File(PATH_CHAVE_PUBLICA);

            // Cria os arquivos para armazenar a chave Privada e a chave Publica
            if (chavePrivadaFile.getParentFile() != null) {
                chavePrivadaFile.getParentFile().mkdirs();
            }

            if (chavePublicaFile.getParentFile() != null) {
                chavePublicaFile.getParentFile().mkdirs();
            }

            try (ObjectOutputStream keyPublicOS = new ObjectOutputStream(
                    new FileOutputStream(chavePublicaFile))) {
                keyPublicOS.writeObject(key.getPublic());
            }

            try (ObjectOutputStream keyPrivateOS = new ObjectOutputStream(
                    new FileOutputStream(chavePrivadaFile))) {
                keyPrivateOS.writeObject(key.getPrivate());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] encrypt(String data) {
        byte[] cipherText = null;

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            cipherText = cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cipherText;
    }

}
