package com.lyadev;

import com.lyadev.BD.bd;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.LongPollingBot;
import org.telegram.telegrambots.generics.WebhookBot;
import org.telegram.telegrambots.logging.BotLogger;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import static org.telegram.telegrambots.logging.BotLogger.log;

public class Bot extends TelegramLongPollingBot {
    boolean key = true;
    //boolean admin = false;
    String admin = null;
    boolean run = true;
    @Override
    public void onUpdateReceived(Update update) {
       boolean user = false;
        try {
            bd.CreateDB();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(update.getMessage().getFrom().getFirstName());

        if(run) {
            int l = 0;

            if (update.hasMessage()) {
                String message = update.getMessage().getText();
                String idchat = update.getMessage().getChatId().toString();
                System.out.println(": " + message);
//                for(int k = 0 ; k < 1000 ;k++ ){
//                    sendMsg(idchat, "Саси"+"["+k+"]");
//                }
//                if (admin != null){
//                    sendMsg(admin, message);
//            }
                if (message.equals("Dev")) {
                    key = false;
                    sendMsg(idchat, "Enter Pass:");

                   if(l==0){
                       admin = idchat;
                   }
                    l = 1;
                    }


                if (message.equals("/start")) {
                    try {
                        bd.CloseDB();
                        bd.CreateDB();
                        System.out.println(bd.getUsers().isEmpty());
                        if(bd.getUsers().isEmpty()==true) {
                            if(update.getMessage().getFrom().getLastName()!=null) {
                                bd.AddUser(update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName(), update.getMessage().getChatId());
                            }else{
                                bd.AddUser(update.getMessage().getFrom().getFirstName() , update.getMessage().getChatId());
                            }

                        }else {
                            for(int i = 0 ; i<bd.getUsers().size();i++){
                                if(update.getMessage().getFrom().getFirstName() == String.valueOf(bd.getUsers().get(i))){
                                    user = true;
                                }
                            }
                        }
                        if(user==false){
                            if(update.getMessage().getFrom().getLastName()!=null) {
                                bd.AddUser(update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName(), update.getMessage().getChatId());
                            }else{
                                bd.AddUser(update.getMessage().getFrom().getFirstName() , update.getMessage().getChatId());
                            }
                            System.out.println("User " + update.getMessage().getFrom().getFirstName() +" is create");
                        }else {
                            System.out.println("User " + update.getMessage().getFrom().getFirstName() + "существуе");
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    sendMsg(idchat, "Текст ещё не придуман , но ты новый пользователь , поздравляю )24.10.2019 4:21");

                }
                if (message.equals("Поддержать бота")) {
                    sendMsg(idchat, "Карта Сбербанк : 2202-2010-0225-4700");
                }
                if (message.equals("Сигареты")) {
                    String url = "https://www.tabacum.ru/info/cigarette";
                    Document doc = null;
                    try {
                        doc = Jsoup.connect(url).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Elements listbrand = doc.select("div.brand-selector");
                    String[] a = listbrand.text().split(" ");
                    for (int i = 0; i < a.length; i++) {
                        sendMsg(idchat, a[i]);
                    }
                }
            } else if (update.hasCallbackQuery()) {
                String message = update.getCallbackQuery().getData();
                System.out.println(message);
                if (message.equals("Text")) {

                    System.out.println("KEKEKEKKEKE");
                }
            }

        }else{
            String message = update.getMessage().getText();
            String idchat = update.getMessage().getChatId().toString();
            Date date = new Date();
            sendMsg(idchat,"Bot is offline , Sorry "+ "Date : "+date.toString() );}
    }


    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        if(key) {
            setButtons(sendMessage);
        }
        try {
           sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            log(Level.SEVERE, "Exception: ", e.toString());
        }
    }
    private synchronized void setInline(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
sendMessage.setText(s);
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(new InlineKeyboardButton().setText("{}{}").setCallbackData("test"));
        buttons.add(buttons1);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);

    sendMessage.setReplyMarkup(markupKeyboard);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            log(Level.SEVERE, "Exception: ", e.toString());
        }

    }
    @Override
    public String getBotUsername() {
        return "Tobacconist_bot";
    }

    @Override
    public String getBotToken() {
        return "994401230:AAGkS0Fwg5qNaPzLsfy-9WC_QuV0flJ6MjY";
    }

    public synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("Сигареты"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("Обзоры"));
        //3
        KeyboardRow keyboardRowthree = new KeyboardRow();
        keyboardRowthree.add(new KeyboardButton("Связь"));

        KeyboardRow keyboardRowthre = new KeyboardRow();
        keyboardRowthre.add(new KeyboardButton("Поддержать бота"));
        // Добавляем все строчки клавиатуры в список
        KeyboardRow dev_button = new KeyboardRow();
        dev_button.add(new KeyboardButton("Dev"));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardRowthree);
        keyboard.add(keyboardRowthre);
        keyboard.add(dev_button);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
    public synchronized void answerCallbackQuery(String callbackId, String message) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackId);
        answer.setText(message);
        answer.setShowAlert(true);
        try {
            answerCallbackQuery(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
