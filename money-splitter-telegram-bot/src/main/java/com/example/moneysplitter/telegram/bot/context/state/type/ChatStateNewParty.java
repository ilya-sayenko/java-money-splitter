package com.example.moneysplitter.telegram.bot.context.state.type;

import com.example.moneysplitter.telegram.bot.context.state.ChatStateData;
import com.example.moneysplitter.telegram.bot.context.state.ChatState;
import com.example.moneysplitter.telegram.bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.moneysplitter.telegram.bot.context.state.ChatStateType.NEW_PARTY;

public class ChatStateNewParty extends ChatState {
    public ChatStateNewParty(long chatId, TelegramBot bot) {
        super(chatId, NEW_PARTY, bot, new ChatStateData());
    }

    @Override
    public void onReceived(Update upd) {

    }

    @Override
    public String getMessageText() {
        return "newparty";
    }
}
