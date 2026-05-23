# quarkus-encryption

Library enkripsi enterprise untuk Quarkus — menyediakan AES-256-GCM, RSA-2048 OAEP, BCrypt, SHA-256/512, dan HMAC-SHA256.

## Fitur

| Algoritma        | Kelas                  | Kegunaan                          |
|------------------|------------------------|-----------------------------------|
| AES-256-GCM      | `AesEncryptionService` | Enkripsi simetris data sensitif   |
| RSA-2048 OAEP    | `RsaEncryptionService` | Enkripsi asimetris, pertukaran key|
| BCrypt           | `HashingService`       | Hash password                     |
| SHA-256 / SHA-512| `HashingService`       | Checksum, integritas data         |
| HMAC-SHA256      | `HashingService`       | Verifikasi integritas + autentikasi|

## Instalasi

```xml
<dependency>
  <groupId>com.enterprise</groupId>
  <artifactId>quarkus-encryption</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Penggunaan

### AES Enkripsi (simetris)

```java
@Inject AesEncryptionService aes;

// Generate key baru (simpan dengan aman!)
SecretKey key = AesEncryptionService.generateKey();
String keyBase64 = Base64.getEncoder().encodeToString(key.getEncoded());

// Enkripsi
String ciphertext = aes.encrypt("data rahasia", key);

// Dekripsi
String plaintext = aes.decrypt(ciphertext, key);
```

Format ciphertext: `Base64(IV[12 bytes] + ciphertext)`

### RSA Enkripsi (asimetris)

```java
@Inject RsaEncryptionService rsa;

// Generate key pair
KeyPair pair = RsaEncryptionService.generateKeyPair();

// Enkripsi dengan public key
String encrypted = rsa.encrypt("pesan", pair.getPublic());

// Dekripsi dengan private key
String decrypted = rsa.decrypt(encrypted, pair.getPrivate());
```

### Hashing Password

```java
@Inject HashingService hash;

// Hash password (BCrypt, cost=12)
String hashed = hash.hashPassword("passwordku");

// Verifikasi
boolean valid = hash.verifyPassword("passwordku", hashed);
```

### SHA & HMAC

```java
String checksum = hash.sha256("data");
String checksum512 = hash.sha512("data");

// HMAC untuk verifikasi integritas
String mac = hash.hmacSha256("data", "secret-key");

// Perbandingan aman (constant-time, mencegah timing attack)
boolean sama = hash.safeEquals(mac1, mac2);
```

## REST Endpoint (Opsional)

Endpoint dapat diaktifkan/dinonaktifkan:

```properties
# application.properties
encryption.endpoint.enabled=true   # default: false
```

Jika diaktifkan:

| Method | Path                          | Deskripsi              |
|--------|-------------------------------|------------------------|
| POST   | `/api/encryption/aes/encrypt` | Enkripsi AES           |
| POST   | `/api/encryption/aes/decrypt` | Dekripsi AES           |
| POST   | `/api/encryption/hash`        | Hash teks              |
| POST   | `/api/encryption/verify`      | Verifikasi hash        |

## Konfigurasi

```properties
# Konfigurasi BCrypt cost factor (default: 12)
encryption.bcrypt.cost=12

# Toggle endpoint REST
encryption.endpoint.enabled=false
```

## Build

```bash
mvn clean package
mvn clean package -Pnative   # GraalVM native
```
