package com.toy.mealimeter.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp init() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("mealimeter-75af5-firebase-adminsdk-f1xdd-025bf1c623.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://mealimeter-75af5.firebaseio.com")
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
