package com.toy.mealimeter;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MealimeterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MealimeterApplication.class, args);
    }

}
