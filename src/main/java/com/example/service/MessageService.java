package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import java.util.*;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Optional<Message> findByPostedBy(int id){
        return messageRepository.findByPostedBy(id);
    }

     public Message createMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int id){
        return messageRepository.findById(id);
    }

    public int deleteByMessageid(int messageId){

        return messageRepository.deleteBymessageId(messageId);
    }

    public int updateMessageById(String messageText,int messageId){
        return messageRepository.updateByMessageId(messageText,messageId);
    }

    public List<Message> getAllMessagesByAccountId(int id){
        List<Message> message = messageRepository.findAllByPostedBy(id);
        return message;
    }


}
