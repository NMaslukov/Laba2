package com.sysprog.laba.bot;

import com.sysprog.laba.TelegramDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private TelegramDispatcher telegramDispatcher;

    @Override
    public void onUpdateReceived(Update update) {
        telegramDispatcher.processUpdate(update);
    }

    @Override
    public String getBotUsername() {
        return "EXRATES NODE NOTIFICATION";
    }

    @Override
    public String getBotToken() {
        return "739487263:AAE5xPADM-Q5lmKIaoYijyO-LU8-wCOp21E";
    }

}