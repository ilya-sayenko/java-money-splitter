package com.example.moneysplitter.telegram.bot.context.state;

import com.example.moneysplitter.telegram.bot.TelegramBot;

public class ChatStateFactory {
//    public static ChatStateStart start(TelegramBot bot, ChatStateData data) {
//        return new ChatStateStart(bot, data);
//    }

//    public static ChatStateUnknown unknown(TelegramBot bot, ChatStateData data) {
//        return new ChatStateUnknown(bot);
//    }

//    public static ChatStateNewParty newParty(TelegramBot bot, ChatStateData data) {
//        return new ChatStateNewParty(bot);
//    }

    public static ChatState createByType(TelegramBot bot, ChatStateType type) {
        switch (type) {
            case START:
                return null;
            case NEW_PARTY:
                return null;
        }
        return null;
    }
}
