package com.example.demo_webapp;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@SpringBootApplication
@RestController
public class DemoWebappApplication {


    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @GetMapping("/test")
    public ResponseEntity<String> testWithoutRoles(Principal principal, Model model) {
//        System.out.println("Principal name" + principal.getName());
//        System.out.println("Model" + model.toString());
        System.out.println("Test OK");
        return ResponseEntity.ok("Test OK, no roles checking ");
    }

    //this method can be accessed by user whose role is admin
    @GetMapping("/keycloak")
    //@RolesAllowed("myrole")
    public ResponseEntity<String> testSecurityRoles(Principal principal) {
        if (principal != null){
            System.out.println("principal = " + principal.getName());
            System.out.println("roles = " + ((KeycloakAuthenticationToken) principal).getAccount().getRoles());
        }
        return ResponseEntity.ok("Access with role myrole");
    }

    @GetMapping("/callback")
    public ResponseEntity<String> callback() {
        return ResponseEntity.ok("Callback ");
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }


    public static void main(String[] args) {
        SpringApplication.run(DemoWebappApplication.class, args);
    }

}
