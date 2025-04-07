package com.example.service;
import com.example.entity.*;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Optional<Account> findByUsername(String username){
        return accountRepository.findByUsername(username);
    }

    public Account registerAccount(Account account){
        return accountRepository.save(account);
    }

    public Account loginAccount(Account account){
        Optional<Account> a = accountRepository.findByUsername(account.getUsername());
        if(a.isPresent()){
            Account getAccount = a.get();
            if(account.getPassword().equals(getAccount.getPassword())){
                return getAccount;
            }
        }
        
        return null;
    }

}
