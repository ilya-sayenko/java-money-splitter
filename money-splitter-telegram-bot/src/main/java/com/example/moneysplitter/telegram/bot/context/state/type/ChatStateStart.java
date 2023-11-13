package com.example.moneysplitter.telegram.bot.context.state.type;

import com.example.moneysplitter.telegram.bot.context.state.ChatState;
import com.example.moneysplitter.telegram.bot.context.state.ChatStateData;
import com.example.moneysplitter.telegram.bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.moneysplitter.telegram.bot.context.state.ChatStateType.START;

public class ChatStateStart extends ChatState {

    public ChatStateStart(long chatId, TelegramBot bot, ChatStateData data) {
        super(chatId, START, bot, data);
    }

    @Override
    public void onReceived(Update upd) {
    }

    @Override
    public String getMessageText() {
        return "start";
    }
}
