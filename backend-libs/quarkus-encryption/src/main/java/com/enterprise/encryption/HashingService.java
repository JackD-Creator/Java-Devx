package com.enterprise.encryption;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HexFormat;

@ApplicationScoped
public class HashingService {

    @ConfigProperty(name = "encryption.bcrypt.cost", defaultValue = "12")
    int bcryptCost;

    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(bcryptCost);
    }

    /** Hash password dengan BCrypt. */
    public String hashPassword(String rawPassword) {
        return encoder().encode(rawPassword);
    }

    /** Verifikasi password terhadap BCrypt hash. */
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return encoder().matches(rawPassword, hashedPassword);
    }

    /** SHA-256 → hex string. */
    public String sha256(String input) {
        return digest(input, "SHA-256");
    }

    /** SHA-512 → hex string. */
    public String sha512(String input) {
        return digest(input, "SHA-512");
    }

    /** HMAC-SHA256 → base64 string. */
    public String hmacSha256(String input, String secretKey) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return Base64.getEncoder().encodeToString(mac.doFinal(input.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new EncryptionException("HMAC-SHA256 failed", e);
        }
    }

    /** Perbandingan aman (constant-time) untuk mencegah timing attack. */
    public boolean safeEquals(String a, String b) {
        return MessageDigest.isEqual(
            a.getBytes(StandardCharsets.UTF_8),
            b.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String digest(String input, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new EncryptionException("Hashing failed: " + algorithm, e);
        }
    }
}
