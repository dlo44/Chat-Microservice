package edu.msudenver.chat.privatechat;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(path = "/chat/private")
public class PrivateChatController {

    @Autowired
    private PrivateChatService  privateChatService;

    @GetMapping(path = "/{senderId}/{receiverId}", produces = "application/json")
    public ResponseEntity<List<PrivateChat>> getPrivateChats(@PathVariable Long senderId, @PathVariable Long receiverId, @RequestParam(required = false) @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timeStamp)
    {
        try {
            List<PrivateChat> privateChats = privateChatService.getPrivateChats(senderId, receiverId, timeStamp);
            HttpStatus status = privateChats.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return new ResponseEntity<>(privateChats, status);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PrivateChat> createPrivateChat(@RequestBody PrivateChat privateChat) {
        try {
            PrivateChat savedPrivateChat = privateChatService.savePrivateChat(privateChat);
            return new ResponseEntity<>(savedPrivateChat, HttpStatus.CREATED);
        } catch (Exception e) { e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{senderId}/{receiverId}")
    public ResponseEntity<Void> deletePrivateChat(@PathVariable Long senderId, @PathVariable Long receiverId) {
        try {
            return privateChatService.deletePrivateChat(senderId, receiverId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }
}

