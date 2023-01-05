package com.openwaygroup.ipsgateway.controllers;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Security;

@RestController
public class EncryptController {
    @PostMapping("/kcv")
    public ResponseEntity<String> getKcv(@RequestBody String key) throws GeneralSecurityException {
      /*  String key = "BCD66B9B01A1CE8313AE984025204FE9";*/
        byte[] bytes = Hex.decode(key);
        System.out.println("bytes=" + bytes);
        String kcv = desEncrypt(bytes);
        System.out.println("key=" + key + ", kcv=" + kcv);
        return ResponseEntity.ok(kcv);
    }
    /**
     * Generate KCV for 3DES key
     */
    private static String desEncrypt(byte[] key) throws GeneralSecurityException {
        // Add Bouncy Castle Security Provider
        Security.addProvider(new BouncyCastleProvider());
        // Construct a Secret Key from the given key
        SecretKey skey = new SecretKeySpec(key, "DESede");
        // Instantiate a DESede Cipher
        Cipher encrypter = Cipher.getInstance("DESede/ECB/NoPadding", "BC");
        // Initialize the cipher with the key in Encrypt mode
        encrypter.init(Cipher.ENCRYPT_MODE, skey);
        // Encrypt an 8-byte null array with the cipher and return the first 6 Hex digits of the result
        return Hex.toHexString(encrypter.doFinal(new byte[8])).substring(0, 6).toUpperCase();
    }

}
