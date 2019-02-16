package com.sysprog.laba.controller;

import com.sysprog.laba.annotation.BotPath;
import com.sysprog.laba.bot.TelegramBot;
import com.sysprog.laba.enums.Stage;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;

@Service
@Data
public class BotController {

    @Autowired
    private TelegramBot telegramBot;

    private Stage currentStage = Stage.NEW;


    @BotPath(requiredStage = Stage.NEW, path = "/start")
    public void start(Update update) {
        sendText(update, "Welcome! Use /input command for entering value and we will send you this value in the tenth degree.");
        currentStage = Stage.STARTED;
    }

    @BotPath(requiredStage = Stage.STARTED, path = "/input")
    public void input(Update update) {
        sendText(update, "Enter value:");
        currentStage = Stage.INPUT;
    }

    @BotPath(requiredStage = Stage.INPUT)
    public void processInput(Update update) {
        sendText(update, new BigDecimal(update.getMessage().getText()).pow(10).toString());
        currentStage = Stage.END;
    }

    @BotPath(requiredStage = Stage.INPUT, path = "/cancel")
    public void cancel(Update update) {
        sendText(update, "Canceled");
        start(update);
    }

    @BotPath(requiredStage = Stage.END)
    public void end(Update update) {
        sendText(update, "You've already ended");
    }

    @SneakyThrows
    private void sendText(Update update, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText(text);
        telegramBot.execute(message);
    }
}
