package org.example.b104.domain.chatbot.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.b104.domain.chatbot.service.command.ChatBotTextCommand;

@Data
public class ChatBotTextRequest {

    @JsonProperty("command_id")
    private Long command_id;
    private String message;
    private String bank;
    private String account;
    private Long money;
    @JsonProperty("ischatbot")
    private Long ischatbot;

    public ChatBotTextCommand toChatBotTextCommand() {
        return ChatBotTextCommand.builder()
                .command_id(command_id)
                .message(message)
                .bank(bank)
                .account(account)
                .money(money)
                .is_chatbot(ischatbot)
                .build();
    }
}
