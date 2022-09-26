package com.company;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RsaString {

    private static void readPrivateKeyFromFile() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File privateKeyFile = new File("key.priv");
        byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());

        KeyFactory privateKeyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKeyGenerated = privateKeyFactory.generatePrivate(privateKeySpec);

        System.out.println("Private Key Generated: " + privateKeyGenerated);
    }

    private static void readPublicKeyFromFile() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File publicKeyFile = new File("key.pub");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

        KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKeyGenerated = publicKeyFactory.generatePublic(publicKeySpec);

        System.out.println("Public Key Generated: " + publicKeyGenerated);
    }

    private static String getRandomLine(String path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Random random = new Random();
        return lines.get(random.nextInt(lines.size()));
    }

    public static void main(String[] args) {

        try {
            File myObj = new File("liczby.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        String path = "liczby.txt";
        String randomNumber = RsaString.getRandomLine(path);

        System.out.println("Random number from file: " + randomNumber);

        String secretMessage = randomNumber;
        String encodedMessage = "";
        String decryptedMessage = "";

        byte[] encryptedMessageBytes = new byte[0];
        byte[] decryptedMessageBytes;
        byte[] secretMessageBytes = new byte[0];

        KeyPairGenerator generator;
        Cipher encryptCipher;
        Cipher decryptCipher;

        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            //GENERATING PAIR OF KEY
            KeyPair pair = generator.generateKeyPair();

            //DISPLAYING PRIVATE AND PUBLIC KEY
            System.out.println("Pair: " + pair.toString());
            PrivateKey privateKey = pair.getPrivate();
            System.out.println("Private key: " + privateKey);
            PublicKey publicKey = pair.getPublic();
            System.out.println("Public key: " + publicKey);

            //SAVING PRIVATE AND PUBLIC KEY TO FILES
            FileOutputStream outPrivate = new FileOutputStream("key.priv");
            outPrivate.write(privateKey.getEncoded());
            FileOutputStream outPublic = new FileOutputStream("key.pub");
            outPublic.write(privateKey.getEncoded());

            //Method to read private and public key from file
            RsaString.readPrivateKeyFromFile();

            encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            secretMessageBytes = secretMessage.getBytes();
            encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
            encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);

            decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
            decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        System.out.println("secretMessage: " + secretMessage);
        System.out.println("encodedMessage: " + encodedMessage);
        System.out.println("secretMessageBytes: " + secretMessageBytes.toString());
        System.out.println("encryptedMessageBytes: " + encryptedMessageBytes.toString());
        System.out.println("decryptedMessage: " + decryptedMessage);


    }
}
