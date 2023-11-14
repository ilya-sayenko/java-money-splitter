package com.example.moneysplitter.telegram.bot;

import com.example.moneysplitter.telegram.bot.config.BotConfig;
import com.example.moneysplitter.telegram.bot.context.Context;
import com.example.moneysplitter.telegram.bot.context.state.ChatState;
import com.example.moneysplitter.telegram.bot.context.state.ChatStateData;
import com.example.moneysplitter.telegram.bot.context.state.type.ChatStateAwaitingPartyName;
import com.example.moneysplitter.telegram.bot.context.state.type.ChatStateStart;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.example.moneysplitter.telegram.bot.context.state.ChatStateType.AWAITING_PARTY_DESCRIPTION;
import static com.example.moneysplitter.telegram.bot.context.state.ChatStateType.AWAITING_PARTY_NAME;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class TelegramBot extends AbilityBot {
    @Getter
    private final Context context;

    private final KafkaTemplate<Long, String> kafkaTemplate;

    @Autowired
    public TelegramBot(
            BotConfig botConfig,
            Context context,
            KafkaTemplate<Long, String> kafkaTemplate
    ) {
        super(botConfig.getToken(), botConfig.getBotName());
        this.context = context;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public long creatorId() {
        return 0;
    }

    public Ability start() {
        return Ability
                .builder()
                .name("start")
                .info("Start bot")
                .locality(USER)
                .privacy(PUBLIC)
                .action(this::startAction)
                .build();
    }

    public Ability stop() {
        return Ability
                .builder()
                .name("stop")
                .info("Stop bot")
                .locality(USER)
                .privacy(PUBLIC)
                .action(this::stopAction)
                .build();
    }

    public Ability newParty() {
        return Ability
                .builder()
                .name("newparty")
                .info("Create new party")
                .locality(USER)
                .privacy(PUBLIC)
                .action(this::newPartyAction)
                .build();
    }

    public Reply reply() {
        return Reply.of(
                (bot, upd) -> replyAction(upd),
                Flag.TEXT,
                udp -> List.of(AWAITING_PARTY_NAME, AWAITING_PARTY_DESCRIPTION).contains(context.getChatState(getChatId(udp)).getType())
        );
    }

    private void startAction(MessageContext ctx) {
        silent.send("Hello! I am money splitter bot!", ctx.chatId());
        context.setChatState(ctx.chatId(), new ChatStateStart(ctx.chatId(), this, new ChatStateData()));
    }

    private void stopAction(MessageContext ctx) {
        silent.send("Stopped.", ctx.chatId());
        context.remove(ctx.chatId());
    }

    private void newPartyAction(MessageContext ctx) {
        Long chatId = ctx.chatId();
        silent.send("Enter party name:", chatId);
        context.setChatState(chatId, new ChatStateAwaitingPartyName(context.getChatState(chatId)));
    }

    private void replyAction(Update upd) {
        ChatState state = context.getChatState(getChatId(upd));
        if (state != null) {
            state.onReceived(upd);
        }
    }
}
