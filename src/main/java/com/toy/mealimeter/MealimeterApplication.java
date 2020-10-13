package com.toy.mealimeter;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MealimeterApplication {

    public static void main(String[] args) throws FirebaseAuthException {
        SpringApplication.run(MealimeterApplication.class, args);

        hello();
    }

    public static void hello() throws FirebaseAuthException {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        UserRecord userRecord = firebaseAuth.getUserByEmail("wowuser8014@gmail.com");
        System.out.println("Successfully fetched user data: " + userRecord.getDisplayName());

        Map<String, Object> customToken = new HashMap<>();
        customToken.put("test", "hello");

        firebaseAuth.setCustomUserClaims("iBtmhqQDBkfhRvu54Z33vTaezzt2", customToken);
        String token = firebaseAuth.createCustomToken("iBtmhqQDBkfhRvu54Z33vTaezzt2");
        System.out.println("token :: " + token);

        // Verify the ID token first.
//        FirebaseToken decoded = FirebaseAuth.getInstance().verifyIdToken(idToken);
//        if (Boolean.TRUE.equals(decoded.getClaims().get("test"))) {
//            // Allow access to requested admin resource.
//        }
    }

}
