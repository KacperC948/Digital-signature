package com.company;
// Java Program to Implement the RSA Algorithm
import java.math.*;
import java.util.*;

class RSAClear {
    public static void main(String args[])
    {
        int i;
        BigInteger p, q, n, z, d , e;
        d = BigInteger.valueOf(0);
        // The number to be encrypted and decrypted
        BigInteger msg = BigInteger.valueOf(12);
        BigInteger c;
        BigInteger msgback;

        // 1st prime number p
        p = BigInteger.valueOf(3);

        // 2nd prime number q
        q = BigInteger.valueOf(11);
        n = p.multiply(q);
        z = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        System.out.println("the value of z = " + z);

        for (e = BigInteger.valueOf(2); e.compareTo(z) < 0; e = e.add(BigInteger.ONE)) {
            // e is for public key exponent
            if (gcd(e, z).compareTo(BigInteger.ONE) == 0) {
                break;
            }
        }
        System.out.println("the value of e = " + e);
        for (i = 0; i <= 9; i++) {
            BigInteger x = z.multiply(BigInteger.valueOf(i)).add(BigInteger.ONE);


            // d is for private key exponent
            if (x.mod(e).compareTo(BigInteger.ZERO) == 0) {
                d = x.divide(e);
                break;
            }
        }
        System.out.println("the value of d = " + d);

        c = msg.pow(e.intValue()).mod(n);
        System.out.println("Encrypted message is : " + c);

        // converting int value of n to BigInteger
        BigInteger N = n;

        // converting float value of c to BigInteger
        BigInteger C = c;
        msgback = (C.pow(d.intValue())).mod(N);
        System.out.println("Decrypted message is : "
                + msgback);
    }

    static BigInteger gcd(BigInteger e, BigInteger z)
    {
        if (e.compareTo(BigInteger.ZERO) == 0)
            return z;
        else
            return gcd(z.mod(e), e);
    }
}