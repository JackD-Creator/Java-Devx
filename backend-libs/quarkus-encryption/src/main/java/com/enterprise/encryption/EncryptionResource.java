package com.enterprise.encryption;

import com.enterprise.encryption.model.EncryptRequest;
import com.enterprise.encryption.model.EncryptResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/encryption")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Encryption", description = "Encryption & hashing endpoints")
public class EncryptionResource {

    @ConfigProperty(name = "encryption.endpoint.enabled", defaultValue = "true")
    boolean endpointEnabled;

    @Inject AesEncryptionService aes;
    @Inject HashingService       hashing;

    @POST
    @Path("/aes/encrypt")
    @Operation(summary = "Encrypt value with AES-256-GCM")
    public EncryptResponse aesEncrypt(EncryptRequest req) {
        checkEnabled();
        return EncryptResponse.of(aes.encrypt(req.value));
    }

    @POST
    @Path("/aes/decrypt")
    @Operation(summary = "Decrypt AES-256-GCM ciphertext")
    public EncryptResponse aesDecrypt(EncryptRequest req) {
        checkEnabled();
        return EncryptResponse.of(aes.decrypt(req.value));
    }

    @POST
    @Path("/hash/password")
    @Operation(summary = "Hash password with BCrypt")
    public EncryptResponse hashPassword(EncryptRequest req) {
        checkEnabled();
        return EncryptResponse.of(hashing.hashPassword(req.value));
    }

    @POST
    @Path("/hash/sha256")
    @Operation(summary = "Hash value with SHA-256")
    public EncryptResponse sha256(EncryptRequest req) {
        checkEnabled();
        return EncryptResponse.of(hashing.sha256(req.value));
    }

    @POST
    @Path("/hash/sha512")
    @Operation(summary = "Hash value with SHA-512")
    public EncryptResponse sha512(EncryptRequest req) {
        checkEnabled();
        return EncryptResponse.of(hashing.sha512(req.value));
    }

    private void checkEnabled() {
        if (!endpointEnabled) throw new ForbiddenException("Encryption endpoint is disabled");
    }
}
