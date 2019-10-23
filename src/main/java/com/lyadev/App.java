package com.lyadev;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


/**
 * Hello world!
 *
 */
public class App extends TelegramLongPollingBot
{
    public static void main( String[] args )
    {
        ApiContextInitializer.init();
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new App());
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
        System.err.println("Hello, logs!");
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage(); // Это нам понадобится
        String txt = msg.getText();
        if (txt.equals("/start")) {
            sendMsg(msg, "Hello, world! This is simple bot!");
        }
        System.out.println("test");
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {

    }

    @Override
    public String getBotUsername() {
        return "Tobacconist_bot";
    }

    @Override
    public String getBotToken() {
        return "994401230:AAEm-n77rSuFvFgneOkbZsexpEVUAMpbP_Y";
    }
    @SuppressWarnings("deprecation")
    private void sendMsg(Message msg,String text){
        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId());
        s.setText(text);
        try {
execute(s);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
