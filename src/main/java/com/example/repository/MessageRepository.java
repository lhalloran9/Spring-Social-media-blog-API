package com.example.repository;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import com.example.entity.*;
import java.util.*;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    Optional<Message> findByPostedBy(int postedBy);
    List<Message> findAllByPostedBy(int postedBy);
    
    @Modifying
    @Transactional
    @Query("delete from Message m where m.messageId = :messageId")
    int deleteBymessageId(@Param("messageId") int messageId);

    @Modifying
    @Transactional
    @Query("update Message m set m.messageText = :messageText where m.messageId = :messageId")
    int updateByMessageId(@Param("messageText") String messageText, @Param("messageId") int messageId);


    

    
} 
