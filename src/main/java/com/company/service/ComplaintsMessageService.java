package com.company.service;

import com.company.config.TelegramBotConfig;
import com.company.enums.LanguageCode;
import com.company.util.button.ButtonUtil;
import com.company.util.button.InlineButtonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class ComplaintsMessageService {

    private final TelegramBotConfig telegramBotConfig;

    public void buttonList(Message message, LanguageCode lang){
        var sendMessage = new SendMessage();

        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setReplyMarkup(InlineButtonUtil.complaintButtonList(lang));
        sendMessage.setText("Ozingizga tegishli bolimlarni belgilang");
        telegramBotConfig.sendMsg(sendMessage);/*
        var sendMessage2=new SendMessage();
        sendMessage2.setChatId(String.valueOf(message.getChatId()));
        sendMessage2.setReplyMarkup(ButtonUtil.comlaintsStop(lang));
        sendMessage2.setText("Ozingizga tegishli bolimlarni belgilang");
        telegramBotConfig.sendMsg(sendMessage2);*/
    }

    public void result(Message message, LanguageCode lang){
        var sendMessage = new SendMessage();

        var str= new StringBuilder();
        var list=TelegramBotConfig.USER_COMPLAINT.get(message.getChatId());
        switch (lang){
            case UZ -> {
                for (var complaits: list) {
                    str.append(complaits.getNameUz()).append("\n");
                }
            }
            case RU -> {
                for (var complaits: list) {
                    str.append(complaits.getNameRu()).append("\n");
                }
            }
        }

        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setReplyMarkup(InlineButtonUtil.confirmComplints(lang));
        sendMessage.setText(str.toString());
        telegramBotConfig.sendMsg(sendMessage);
    }
}