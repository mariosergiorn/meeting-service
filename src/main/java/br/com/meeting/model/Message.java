package br.com.meeting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import java.security.Signature;
import java.util.Base64;
import java.security.*;
import javax.crypto.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Message {

    KeyPair keyPair = EncryptionAndSigning.generateKeyPair();
    PrivateKey privateKey = keyPair.getPrivate();
    PublicKey publicKey = keyPair.getPublic();

    private Long idMeeting;

    private String status;

    // Mensagem a ser enviada
    String message = "Mensagem secreta para teste";

    // Criar assinatura e criptografar mensagem
    String signedAndEncryptedMessage = EncryptionAndSigning.signAndEncrypt(message, privateKey, publicKey);
        System.out.println("Mensagem criptografada e assinada: " + signedAndEncryptedMessage);

    // Decriptar mensagem e verificar assinatura
    String decryptedAndVerifiedMessage = DecryptionAndVerification.decryptAndVerify(signedAndEncryptedMessage, privateKey, publicKey);
        System.out.println("Mensagem decriptada e verificada: " + decryptedAndVerifiedMessage);


}
