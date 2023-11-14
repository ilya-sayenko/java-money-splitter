package com.example.moneysplitter.telegram.bot.context.state.type;

import com.example.moneysplitter.telegram.bot.context.state.ChatStateData;
import com.example.moneysplitter.telegram.bot.context.state.ChatState;
import com.example.moneysplitter.telegram.bot.context.state.ChatStateType;
import com.example.moneysplitter.telegram.bot.TelegramBot;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ChatStateAwaitingPartyName extends ChatState {
    public ChatStateAwaitingPartyName(ChatState chatState) {
        super(chatState.getChatId(), ChatStateType.AWAITING_PARTY_NAME, chatState.getBot(), chatState.getData());
    }
    public ChatStateAwaitingPartyName(long chatId, TelegramBot bot, ChatStateData data) {
        super(chatId, ChatStateType.AWAITING_PARTICIPANT_NAME, bot, data);
    }

    @Override
    public void onReceived(Update upd) {
        Long currentChatId = AbilityUtils.getChatId(upd);
        data.setPartyName(upd.getMessage().getText());
        bot.silent().send("Enter party description:", currentChatId);
        bot.getContext().setChatState(currentChatId, new ChatStateAwaitingPartyDescription(this));
    }

    @Override
    public String getMessageText() {
        return null;
    }
}
