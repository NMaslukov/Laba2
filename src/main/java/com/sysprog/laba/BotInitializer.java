package com.sysprog.laba;

import com.sysprog.laba.bot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

@Service
public class BotInitializer {

    @Autowired
    private TelegramBot telegramBot;

    static {
        ApiContextInitializer.init();
    }

    @PostConstruct
    public void init() throws TelegramApiRequestException {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(telegramBot);
    }
}
