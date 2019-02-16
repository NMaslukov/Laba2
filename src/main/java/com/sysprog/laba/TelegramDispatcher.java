package com.sysprog.laba;

import com.sysprog.laba.annotation.BotPath;
import com.sysprog.laba.controller.BotController;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TelegramDispatcher {
    @Autowired
    private  BotController botController;
    private Map<BotPath, Method> methodMap = new HashMap<>();

    @PostConstruct
    public void init(){
        Method[] methods = BotController.class.getDeclaredMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(BotPath.class)){
                BotPath botPath = method.getAnnotation(BotPath.class);
                methodMap.put(botPath, method);
            }
        }
    }

    @SneakyThrows
    public void processUpdate(Update update) {
        String text = update.getMessage().getText();
        Optional<Map.Entry<BotPath, Method>> first = methodMap.entrySet().stream().filter(e -> e.getKey().path().equals(text)).findFirst();
        if (first.isPresent() && first.get().getKey().requiredStage().equals(botController.getCurrentStage())){
            first.get().getValue().invoke(botController, update);
            return;
        }

        Optional<Map.Entry<BotPath, Method>> second = methodMap.entrySet().stream().filter(e -> e.getKey().requiredStage() == botController.getCurrentStage() && e.getKey().path().equals("")).findFirst();
        if(second.isPresent()){
            second.get().getValue().invoke(botController, update);
            return;
        }

        SendMessage message = new SendMessage();
        message.setText("Can not find command!");
        message.setChatId(update.getMessage().getChatId());
        botController.getTelegramBot().execute(message);

    }
}
