package com.enterprise.encryption;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@ApplicationScoped
public class AesEncryptionService {

    private static final String ALGORITHM  = "AES/GCM/NoPadding";
    private static final int    IV_LENGTH  = 12;
    private static final int    TAG_LENGTH = 128;

    @ConfigProperty(name = "encryption.aes.key")
    String base64Key;

    private SecretKey secretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        if (keyBytes.length != 32) {
            throw new IllegalStateException("AES key must be 256-bit (32 bytes base64-encoded)");
        }
        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * Encrypts plaintext with AES-256-GCM.
     * Output format: Base64(iv + ciphertext)
     */
    public String encrypt(String plaintext) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey(), new GCMParameterSpec(TAG_LENGTH, iv));

            byte[] encrypted = cipher.doFinal(plaintext.getBytes());

            byte[] combined = new byte[IV_LENGTH + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, IV_LENGTH);
            System.arraycopy(encrypted, 0, combined, IV_LENGTH, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new EncryptionException("Encryption failed", e);
        }
    }

    /**
     * Decrypts ciphertext produced by {@link #encrypt}.
     */
    public String decrypt(String ciphertext) {
        try {
            byte[] combined = Base64.getDecoder().decode(ciphertext);

            byte[] iv   = new byte[IV_LENGTH];
            byte[] data = new byte[combined.length - IV_LENGTH];
            System.arraycopy(combined, 0, iv, 0, IV_LENGTH);
            System.arraycopy(combined, IV_LENGTH, data, 0, data.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey(), new GCMParameterSpec(TAG_LENGTH, iv));

            return new String(cipher.doFinal(data));
        } catch (Exception e) {
            throw new EncryptionException("Decryption failed", e);
        }
    }

    /** Generate a new random 256-bit key (base64-encoded). */
    public static String generateKey() {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
