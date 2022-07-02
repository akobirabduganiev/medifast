package com.company.controller;

import com.company.config.TelegramBotConfig;
import com.company.dto.BotUsersDTO;
import com.company.service.ComplaintsMessageService;
import com.company.util.button.InlineButtonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.company.config.TelegramBotConfig.USER_COMPLAINT;
import static com.company.config.TelegramBotConfig.USER_LIST;
import static com.company.service.ComplaintsService.COMPLAINTS_LIST;

@RequiredArgsConstructor
@Component
public class ComplaintsMessageController {

    private final TelegramBotConfig telegramBotConfig;

    private final ComplaintsMessageService complaintsMessageService;

    public void complaintsForm(String text, Message message) {
        System.out.println(message.getChatId());
        var lis = USER_COMPLAINT.get(message.getChatId());
        System.out.println(USER_COMPLAINT);

        for (var complaint : COMPLAINTS_LIST) {
            if (text.equals(complaint.getKey())) {
                lis.add(complaint);
                System.out.println(complaint);
                USER_COMPLAINT.put(message.getChatId(), lis);
                break;
            }
        }
        System.out.println(USER_COMPLAINT);

        var lang = USER_LIST.get(message.getChatId()).getLanguageCode();

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(String.valueOf(message.getChatId()));
        editMessageText.setReplyMarkup(InlineButtonUtil.complaintButtonListSendAgain(lang, message.getChatId()));
        editMessageText.setText("belgilab bolganingizdan song tugatish tugamsini bosing");

        /*var sendMessage=new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setReplyMarkup(InlineButtonUtil.complaintButtonListSendAgain(lang, message.getChatId()));
        sendMessage.setText("belgilab bolganingizdan song tugatish tugamsini bosing");*/

        telegramBotConfig.sendMsg(editMessageText);

    }

    public void complentsButtonList(Message message, BotUsersDTO user, Integer integer) {
        complaintsMessageService.buttonList(message, user.getLanguageCode(), integer);
    }

    public void result(Message message, BotUsersDTO user) {
        complaintsMessageService.result(message, user.getLanguageCode());
    }

    public void nextComplaint(Message message) {

    }
}
