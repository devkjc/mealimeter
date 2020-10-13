package com.toy.mealimeter.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FirebaseConfig {

    ClassPathResource resource = new ClassPathResource("mealimeter-75af5-firebase-adminsdk-f1xdd-025bf1c623.json");

    @Bean
    public FirebaseApp init() throws IOException {

        InputStream serviceAccount = resource.getInputStream();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://mealimeter-75af5.firebaseio.com")
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Component
    public static class FirebaseSecurity {

        public void hello() throws FirebaseAuthException {

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            UserRecord userRecord = firebaseAuth.getUserByEmail("wowuser8014@gmail.com");
            System.out.println("Successfully fetched user data: " + userRecord.getDisplayName());

            Map<String, Object> customToken = new HashMap<>();
            customToken.put("test", "hello");

            String token = firebaseAuth.createCustomToken("iBtmhqQDBkfhRvu54Z33vTaezzt2", customToken);

            System.out.println("token :: " + token);

        }

    }
}
