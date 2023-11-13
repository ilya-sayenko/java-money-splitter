package com.example.moneysplitter.telegram.bot.context;

import com.example.moneysplitter.telegram.bot.context.state.ChatState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Context {
    private final Map<Long, ChatState> contextMap = new HashMap<>();

    public void setChatState(long chatId, ChatState state) {
        contextMap.put(chatId, state);
    }

    public ChatState getChatState(long chatId) {
        return contextMap.get(chatId);
    }

    public void remove(long chatId) {
        contextMap.remove(chatId);
    }
}
