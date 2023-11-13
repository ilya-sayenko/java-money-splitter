package com.example.moneysplitter.telegram.bot.context.state;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatStateData {
    private String partyName;
    private String partyDescription;
    private String participantName;
}
