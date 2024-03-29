package com.company.bot.service;

import com.company.bot.config.TelegramBotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.company.bot.config.TelegramBotConfig.USER_COMPLAINT_INFO;
import static com.company.bot.config.TelegramBotConfig.USER_LIST;
import static com.company.bot.enums.LanguageCode.UZ;
import static com.company.bot.enums.UserQuestionnaireStatus.COMPLAINTS_STARTED_TIME;



@Component
@RequiredArgsConstructor
public class AudioService {
    private final TelegramBotConfig telegramBotConfig;
    @Value("${channel.storage.name}")
    private String channelId;

    public void  getAudio(Message message) {
        var user = TelegramBotConfig.USER_LIST.get(message.getChatId());

        var voice = message.getVoice();


        var voiceMsg = new SendVoice();
        voiceMsg.setChatId("-100"+channelId);
        voiceMsg.setCaption("name: "+user.getName());
        voiceMsg.setDuration(voice.getDuration());
        voiceMsg.setVoice(new InputFile(voice.getFileId()));


        Message tempMessage;
        try {
            tempMessage = telegramBotConfig.execute(voiceMsg);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        String link = "https://t.me/c/"+channelId+"/"+tempMessage.getMessageId();

        var dto = USER_COMPLAINT_INFO.get(message.getChatId());
        dto.setCauseOfComplaint(link);

        USER_COMPLAINT_INFO.put(message.getChatId(), dto);

        user.setQuestionnaireStatus(COMPLAINTS_STARTED_TIME);
        USER_LIST.put(message.getChatId(), user);

        var sendMsg = new SendMessage();
        sendMsg.setChatId(String.valueOf(message.getChatId()));
        if (user.getLanguageCode().equals(UZ))
            sendMsg.setText("Shikoyatlar qachon boshlandi?");
        else
            sendMsg.setText("Когда начались жалобы?");
        telegramBotConfig.sendMsg(sendMsg);
    }
}
