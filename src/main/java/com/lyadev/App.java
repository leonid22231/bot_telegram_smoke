package com.lyadev;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;


import java.util.List;


/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        ApiContextInitializer.init();
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new Bot());
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
        System.err.println("Hello, logs!");
    }



    }

