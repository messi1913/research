package com.hirit.boot.account.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hirit.boot.account.service.AccountService;
import com.hirit.boot.enttiy.Account;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public @ResponseBody List<Account> getAccounts() {
        return accountService.getAccounts();
    }

    @PostMapping
    public Account saveAccount() {
        return Account.builder().build();
    }

    @GetMapping("/sample")
    public String showSamplePage() {
        return "sample";
    }

    @GetMapping("/sample2")
    public ModelAndView showSamplePageWithData() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sample");
        mv.addObject("name", "sangmessi");
        return mv;
    }
}
