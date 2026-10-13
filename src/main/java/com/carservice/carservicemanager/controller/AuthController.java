package com.carservice.carservicemanager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        var authorities = authentication.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .toList();

        System.out.println("DEBUG DASHBOARD - Username: " + authentication.getName());
        System.out.println("DEBUG DASHBOARD - Authorities: " + authorities);

        if (authorities.contains("ROLE_MANAGER")) {
            return "redirect:/manager/dashboard";
        } else if (authorities.contains("ROLE_RECEPTIONER")) {
            return "redirect:/receptionist/dashboard";
        } else if (authorities.contains("ROLE_MECANIC")) {
            return "redirect:/mechanic/dashboard";
        } else if (authorities.contains("ROLE_CLIENT")) {
            return "redirect:/client/dashboard";
        }
        return "redirect:/login";
    }
}