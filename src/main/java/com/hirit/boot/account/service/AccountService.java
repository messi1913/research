package com.hirit.boot.account.service;

import com.hirit.boot.enttiy.Account;
import com.hirit.boot.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
//  이건 아래 생성자 패턴 주입을 위해
public class AccountService implements UserDetailsService{
    private final AccountRepository accountRepository;

    // 이게 스프링 부트에서 시큐리티 적용하면 특별한 설정 없으면 자동으로 UserDe~~ 이거 상속받은애 찾아서 override method call.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(account.getEmail(), account.getPassword(), getAuthorities(account.getEmail()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String email) {
        if("admin@gmail.com".equals(email)) {
            return Set.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return Set.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }
}
