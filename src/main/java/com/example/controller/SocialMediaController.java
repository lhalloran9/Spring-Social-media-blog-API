package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.*;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        if(username == null || username.isBlank() || password == null || password.length() < 4){
            return ResponseEntity.badRequest().body("bad request");
        }
        if(accountService.findByUsername(username).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username exist");
        }
        Account newAccount = accountService.registerAccount(new Account(username, password));
        return ResponseEntity.ok(newAccount);

        }

        @PostMapping("/login")
        public ResponseEntity<?> loginAccount(@RequestBody Account account){
            String username = account.getUsername();
            String password = account.getPassword();
            if(username == null || username.isBlank() || password == null){
                return ResponseEntity.badRequest().build();
            }
            if(accountService.loginAccount(account) == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Account loginAccount = accountService.loginAccount(account);
            return ResponseEntity.ok(loginAccount);
    
            }


            @PostMapping("/messages")
            public ResponseEntity<?> createMessage(@RequestBody Message message){
                int postedBy = message.getPostedBy();
                String messageText = message.getMessageText();
                Long timePostedEpoch = message.getTimePostedEpoch();
                
                if(messageText == null || messageText.isBlank() ||  messageText.length() > 255){
                    return ResponseEntity.badRequest().build();
                }
                if(!messageService.findByPostedBy(postedBy).isPresent()){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
                Message newMessage = messageService.createMessage(new Message(postedBy,messageText,timePostedEpoch));
                return ResponseEntity.ok(newMessage);
        
                }

            @GetMapping("/messages")
            public ResponseEntity<?> retrieveAllMessages(){
                List<Message> messages = messageService.getAllMessages();
                if(messages == null){
                    return ResponseEntity.ok().build();
                }
                return ResponseEntity.ok(messages);
            }

            @GetMapping("/messages/{messageId}")
            public ResponseEntity<?> retrieveMessageById(@PathVariable int messageId){
                Optional<Message> message = messageService.getMessageById(messageId);
                if(message.isPresent()){
                    return ResponseEntity.ok(message.get());
                }
                return ResponseEntity.ok().build();
            }
            
            @DeleteMapping("/messages/{messageId}")
            public ResponseEntity<?> deleteMessageById(@PathVariable int messageId){
                int row = messageService.deleteByMessageid(messageId);
                if(row >= 1){
                    return ResponseEntity.ok(row);
                }
                return ResponseEntity.ok().build();

            }

            @PatchMapping("/messages/{messageId}")
            public ResponseEntity<?> updateMessageById(@PathVariable int messageId, @RequestBody Message messageText ){
                String text = messageText.getMessageText();
                if(text == null || text.length() > 255 || text.isBlank()){
                    return ResponseEntity.badRequest().build();
                }
                if(!messageService.getMessageById(messageId).isPresent()){
                    return ResponseEntity.badRequest().build();

                }
                
                int row = messageService.updateMessageById(text,messageId);


                 return ResponseEntity.ok(row);
            }

            @GetMapping("/accounts/{accountId}/messages")
            public ResponseEntity<?> retrieveAllMessagesByAccountId(@PathVariable int accountId){
                List<Message> messages = messageService.getAllMessagesByAccountId(accountId);
                if(messages == null){
                    return ResponseEntity.ok().build();
                }
                return ResponseEntity.ok(messages);
            }

    
    }
    


