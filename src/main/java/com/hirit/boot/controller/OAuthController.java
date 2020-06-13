package com.hirit.boot.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirit.boot.enttiy.Account;

import lombok.RequiredArgsConstructor;

@RestController
public class OAuthController {

    @GetMapping("/admin")
    public Account getAdminInfo() {
        return Account.builder()
            .age(30)
            .email("admin@admin.com")
            .userName("admin")
            .build();

    }

    @GetMapping("/user")
    public Account getUserInfo(Principal principal) {
        System.out.println("principal = " + principal);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return Account.builder()
            .age(30)
            .email("user@user.com")
            .userName("user")
            .build();
    }
}
