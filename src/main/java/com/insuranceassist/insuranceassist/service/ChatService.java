package com.insuranceassist.insuranceassist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatClient chatClient;

    public String askQuery(@RequestBody String myQuery) {
        return chatClient.prompt(myQuery).call().content();

    }
}
