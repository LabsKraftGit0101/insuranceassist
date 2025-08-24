package com.insuranceassist.insuranceassist.controller;

import com.insuranceassist.insuranceassist.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chatWithAI")
    public String askQuery(@RequestParam String myQuery) {
        return chatService.askQuery(myQuery);

    }
}
