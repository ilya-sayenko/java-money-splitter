package com.example.moneysplitter.telegram.bot.context.state;

public enum ChatStateType {
    UNKNOWN,
    STOP,
    START,
    NEW_PARTY,
    AWAITING_PARTY_NAME,
    AWAITING_PARTY_DESCRIPTION,
    AWAITING_PARTICIPANT_NAME
}
