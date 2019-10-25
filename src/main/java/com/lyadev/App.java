package com.lyadev;

import com.lyadev.BD.bd;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.Webhook;
import org.telegram.telegrambots.generics.WebhookBot;


import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;


/**
 * Hello world!
 *
 */
public class App
{

    public static void main( String[] args ) throws SQLException, ClassNotFoundException, URISyntaxException {
        System.out.println("Starting...");
        bd.Conn();
        bd.CreateDB();

        ApiContextInitializer.init();
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new Bot());
        }catch (TelegramApiRequestException e){
            e.printStackTrace();
        }
        System.out.println("Hello, logs!");
    }



    }

