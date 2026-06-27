package com.example.edevicestore;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.UUID;

@SpringBootApplication
public class DeviceStoreApplication {
    public static void main(String[] args) {
        String newId = UUID.randomUUID().toString();

        String password = "akowal123";

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashedPassword);

        //System.out.println(newId);
    }
    
}
