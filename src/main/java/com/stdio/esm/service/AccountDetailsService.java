package com.stdio.esm.service;

import com.stdio.esm.model.Account;
import com.stdio.esm.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDetailsService.class);

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Started load an account by username {} in Account detail service", username);
        Account account = accountRepository.findByUsernameAndDeleteFlagIsFalse(username).orElseThrow(() -> new UsernameNotFoundException("Account is not existed"));
        LOGGER.info("khoa khoa{} {}",account.getUsername(),account.getPassword());
        Set<GrantedAuthority> authorities = account.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        LOGGER.info("Finished load an account by username {} in Account detail service", username);
        return new User(account.getUsername(), account.getPassword(), authorities);
    }

}
