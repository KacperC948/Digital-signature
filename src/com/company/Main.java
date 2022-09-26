package com.company;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

public class Main {

    private static Base64.Encoder encoder = Base64.getEncoder();

    public static void main(String[] args) throws NoSuchAlgorithmException, SignatureException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter text to encrypt: ");
        String textToEncrypt = sc.nextLine();
        System.out.println(textToEncrypt);
        System.out.println("Java program for RSA encryption");
        System.out.println("Start generating RSA key pair");
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(512);
        KeyPair pair = generator.generateKeyPair();
        System.out.println("Generated RSA key pair: " + pair.toString());
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();
        System.out.println("Private key: " + privateKey);
        System.out.println("Encoded private key: " + encoder.encodeToString(privateKey.getEncoded()));

        System.out.println("Public key: " + publicKey);
        System.out.println("Encoded public key: " + encoder.encodeToString(publicKey.getEncoded()));

        System.out.println("Creating a signature");
        Signature sign = Signature.getInstance("SHA256withRSA");

        try {
            sign.initSign(privateKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] bytes = textToEncrypt.getBytes();
        sign.update(bytes);
        byte[] signature = sign.sign();
        System.out.println("Digital signature for text " + textToEncrypt + " is" + new String(signature));

             for (byte b : signature) {
                 System.out.print(b);
             }



    }
}
