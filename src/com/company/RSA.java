package com.company;

import java.math.*;
import java.nio.ByteBuffer;
import java.util.*;

public class RSA {
    public static void main(String args[])
    {
        int p, q, z, d = 0, e, i, n=33;
        BigInteger N;
        // The number to be encrypted and decrypted
        String textToEncrypt = "1";
        byte[] b = textToEncrypt.getBytes();

        System.out.println(Arrays.toString(b));

        //int intNumber = (ByteBuffer.wrap(b).getInt());
        BigInteger bigValue = new BigInteger(b);
        //System.out.println("int: " + intNumber);
        System.out.println("Big integer: " + bigValue);

        byte[] bytesFromBigInteger = bigValue.toByteArray();
        System.out.println(Arrays.toString(bytesFromBigInteger));

        int msg = 12;
        BigInteger c;
        //double c1;
        BigInteger msgback;

        // 1st prime number p
        p = 3;

        // 2nd prime number q
        q = 11;
        N = BigInteger.valueOf(p * q);
        z = (p - 1) * (q - 1);
        System.out.println("the value of z = " + z);
        //Public Key {e,n} Private Key {d,n}
        for (e = 2; e < z; e++) {

            // e is for public key exponent
            if (gcd(e, z) == 1) {
                break;
            }
        }
        System.out.println("the value of e = " + e);
        for (i = 0; i <= 9; i++) {
            int x = 1 + (i * z);

            // d is for private key exponent
            if (x % e == 0) {
                d = x / e;
                break;
            }
        }
        System.out.println("the value of d = " + d);
        System.out.println("Public key: (" + e + ", " + N + ")");
        System.out.println("Private key: (" + d + ", " + N + ")");
        //c1 = Math.pow(1685418081, e) % n;
        c = (bigValue.pow(e).mod(N));
        System.out.println("Encrypted message is : " + c);

        // converting int value of n to BigInteger
        //BigInteger N = BigInteger.valueOf(n);

        // converting float value of c to BigInteger
        BigInteger C = c;
        msgback = (C.pow(d)).mod(N);
        System.out.println("Decrypted message is : "
                + msgback);
    }

    static int gcd(int e, int z)
    {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }
}