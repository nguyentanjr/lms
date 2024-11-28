package com.example.demo.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
public class FireBaseConfig {
    public void initialize() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("static/Json/lmsnguyentan-firebase-adminsdk-7hn6r-fb33b9945a.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(com.google.auth.oauth2.GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
