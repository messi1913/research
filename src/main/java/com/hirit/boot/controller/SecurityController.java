package com.hirit.boot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class SecurityController {

    @GetMapping("/index")
    public String showAnyone() {
        return "index";
    }

    @GetMapping("/users")
    public ModelAndView showAuthenticatedUser(Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("name", user.getUsername());
        return modelAndView;
    }

    @GetMapping("/admins")
    public ModelAndView showAdminUser(Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        Collection<SimpleGrantedAuthority> authorities =
                (Collection< SimpleGrantedAuthority>)authentication.getAuthorities();
        SimpleGrantedAuthority auth = authorities.iterator().next();
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("name", user.getUsername());
        modelAndView.addObject("grant", auth.getAuthority());
        return modelAndView;
    }
}
