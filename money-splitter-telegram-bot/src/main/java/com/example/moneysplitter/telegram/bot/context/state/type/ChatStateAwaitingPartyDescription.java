package com.example.moneysplitter.telegram.bot.context.state.type;

import com.example.moneysplitter.telegram.bot.context.state.ChatStateData;
import com.example.moneysplitter.telegram.bot.context.state.ChatState;
import com.example.moneysplitter.telegram.bot.context.state.ChatStateType;
import com.example.moneysplitter.telegram.bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ChatStateAwaitingPartyDescription extends ChatState {
    public ChatStateAwaitingPartyDescription(ChatState chatState) {
        super(chatState.getChatId(), ChatStateType.AWAITING_PARTY_DESCRIPTION, chatState.getBot(), chatState.getData());
    }

    public ChatStateAwaitingPartyDescription(long chatId, TelegramBot bot, ChatStateData data) {
        super(chatId, ChatStateType.AWAITING_PARTY_DESCRIPTION, bot, data);
    }

    @Override
    public void onReceived(Update upd) {
        data.setPartyDescription(upd.getMessage().getText());
    }

    @Override
    public String getMessageText() {
        return "";
    }
}
