package com.example.repository;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.*;
import java.util.*;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    

    
} 
