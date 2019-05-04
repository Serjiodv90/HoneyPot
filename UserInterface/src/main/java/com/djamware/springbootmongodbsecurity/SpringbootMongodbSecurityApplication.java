package com.djamware.springbootmongodbsecurity;

import com.djamware.springbootmongodbsecurity.domain.Role;
import com.djamware.springbootmongodbsecurity.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/*
 *https://careydevelopment.us/2017/05/24/implement-form-field-validation-spring-boot-thymeleaf/ - example for validation 
 * 
 */


@SpringBootApplication
public class SpringbootMongodbSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMongodbSecurityApplication.class, args);
    }
    
    @Bean
    CommandLineRunner init(RoleRepository roleRepository) {

        return args -> {

            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);
            }
            
            Role userRole = roleRepository.findByRole("USER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("USER");
                roleRepository.save(newUserRole);
            }
        };

    }
}
