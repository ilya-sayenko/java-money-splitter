package com.example.moneysplitter.telegram.bot.context.state;

import com.example.moneysplitter.telegram.bot.TelegramBot;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public abstract class ChatState {
    protected final long chatId;
    protected final ChatStateType type;
    protected final TelegramBot bot;
    protected final ChatStateData data;

    public ChatState(long chatId, ChatStateType type, TelegramBot bot, ChatStateData data) {
        this.chatId = chatId;
        this.type = type;
        this.bot = bot;
        this.data = data;
    }

    public abstract void onReceived(Update upd);

    public abstract String getMessageText();
}
