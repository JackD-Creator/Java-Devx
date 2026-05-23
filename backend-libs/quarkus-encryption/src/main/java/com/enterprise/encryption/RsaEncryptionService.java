package com.enterprise.encryption;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

@ApplicationScoped
public class RsaEncryptionService {

    private static final String ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    @ConfigProperty(name = "encryption.rsa.public-key")
    Optional<String> base64PublicKey;

    @ConfigProperty(name = "encryption.rsa.private-key")
    Optional<String> base64PrivateKey;

    @ConfigProperty(name = "encryption.rsa.key-size", defaultValue = "2048")
    int keySize;

    public String encrypt(String plaintext) {
        return encrypt(plaintext, loadPublicKey(
            base64PublicKey.orElseThrow(() -> new EncryptionException("RSA public key not configured"))
        ));
    }

    public String encrypt(String plaintext, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.getBytes()));
        } catch (Exception e) {
            throw new EncryptionException("RSA encryption failed", e);
        }
    }

    public String decrypt(String ciphertext) {
        return decrypt(ciphertext, loadPrivateKey(
            base64PrivateKey.orElseThrow(() -> new EncryptionException("RSA private key not configured"))
        ));
    }

    public String decrypt(String ciphertext, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)));
        } catch (Exception e) {
            throw new EncryptionException("RSA decryption failed", e);
        }
    }

    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(keySize, new SecureRandom());
            return gen.generateKeyPair();
        } catch (Exception e) {
            throw new EncryptionException("RSA key generation failed", e);
        }
    }

    public PublicKey loadPublicKey(String base64) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(base64);
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(keyBytes));
        } catch (Exception e) {
            throw new EncryptionException("Invalid RSA public key", e);
        }
    }

    public PrivateKey loadPrivateKey(String base64) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(base64);
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        } catch (Exception e) {
            throw new EncryptionException("Invalid RSA private key", e);
        }
    }

    public String exportPublicKey(PublicKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public String exportPrivateKey(PrivateKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
