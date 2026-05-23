package com.enterprise.encryption.model;

public class EncryptResponse {
    public String result;
    public boolean success;

    public static EncryptResponse of(String result) {
        EncryptResponse r = new EncryptResponse();
        r.result  = result;
        r.success = true;
        return r;
    }
}
