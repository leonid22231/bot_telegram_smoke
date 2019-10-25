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
import java.net.URISyntaxException;
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
        System.out.println(update.getMessage().getFrom().getFirstName());

        if(run) {
            int l = 0;
            if (update.hasMessage()) {
                String message = update.getMessage().getText();
                String idchat = update.getMessage().getChatId().toString();
                System.out.println("IDCHAT = " + idchat);
//                for(int k = 0 ; k < 1000 ;k++ ){
//                    sendMsg(idchat, "Саси"+"["+k+"]");
//                }
//                if (admin != null){
//                    sendMsg(admin, message);
//            }
                if (message.equals("Dev")) {
                    key = false;
                    try {
                        bd.changestate(bd.getID(Integer.valueOf(idchat)),9);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    sendMsg(idchat, "Enter Pass:");

                    }


                if (message.equals("/start")) {
                    try {
                        bd.CloseDB();
                        bd.Conn();
                        bd.CreateDB();
                        ArrayList<ArrayList<String>> data = bd.getUsers();
                        if(data.isEmpty()) {
                            user = false;
System.out.println("user = false");
                        }else {
                            for(int i = 0 ; i<data.size();i++){
                                for(int k = 0 ; k < data.get(i).size(); k++){
                                    System.out.println("I = "+i);
                                    System.out.println("K = "+k);
                                    System.out.println(update.getMessage().getFrom().getFirstName() +"||"+ String.valueOf(data.get(i).get(k)));
                                }
System.out.println(data);

                                String firstusername = update.getMessage().getFrom().getFirstName();
                                String secondusername;
                                if(update.getMessage().getFrom().getLastName()!=null) {
                                    secondusername = update.getMessage().getFrom().getLastName();
                                }else{secondusername = "null";}
                                String firstname = data.get(i).get(0);
                                String secondname;
                                if(data.get(i).get(1)!=null) {
                                    secondname = data.get(i).get(1);
                                }else{secondname = "null";}
                                String iduser = data.get(i).get(2);

                                if(secondusername != null) {
                                    System.out.println("ID : > " + idchat.equals(iduser) + " NAME 1 : > " + firstusername.equals(firstname) + " NAME 2 : > " + secondusername.equals(secondname));
                                }else{
                                    System.out.println("ID : > " + idchat.equals(iduser) + " NAME 1 : > " + firstusername.equals(firstname) + " NAME 2 : > " + null);
                                }
                                if(String.valueOf(idchat).equals(iduser)==true){
                                    user = true;
                                    if(secondusername.equals(secondname)){
                                    }else {
                                        bd.changesname(i,secondusername);
                                        System.out.println("Фамилия была изменена c "+ secondname+" на "+secondusername);
                                    }
                                    if(firstusername.equals(firstname)){

                                    }else {
                                        bd.changename(i,firstusername);
                                        System.out.println("Имя было изменено с"+ firstname+" на "+firstusername);
                                    }
                                    System.out.println("User : > "+firstname);
                                    break ;
                                }

//                                if(secondusername != null) {
//                                    if (((firstusername.equals(firstname) && secondusername.equals(secondname)) && String.valueOf(idchat).equals(iduser))==true) {
//                                        user = true;
//                                        System.out.println("User = true");
//                                    }else{
//                                        if ((firstusername.equals(firstname) && idchat.equals(iduser))==true){
//                                            user = true;
//                                            System.out.println("User = true");
//                                        }
//                                    }
//                                }
                            }
                        }
                        bd.CloseDB();
                        bd.Conn();
                        bd.CreateDB();

                        if((user==false && !data.isEmpty()) | (user == false && data.isEmpty())){
                            if(update.getMessage().getFrom().getLastName()!=null) {
                                bd.AddUser(update.getMessage().getFrom().getFirstName() , update.getMessage().getFrom().getLastName(), update.getMessage().getChatId());
                            }else{
                                bd.AddUser(update.getMessage().getFrom().getFirstName(),null , update.getMessage().getChatId());
                            }
                            System.out.println("User " + update.getMessage().getFrom().getFirstName() +" is create");

                            sendMsg(idchat,"Привет , новый пользователь !");
                        }else {
                            System.out.println("User " + update.getMessage().getFrom().getFirstName() + "существуе");
                            sendMsg(idchat,"Ты уже существуешь )");
                        }
                    } catch (SQLException | ClassNotFoundException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                    sendMsg(idchat, "Ну пиздец конечно " + new Date().toString());
                    try {
                        bd.changestate(bd.getID(Integer.valueOf(idchat)),0);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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
                    try {
                        bd.changestate(bd.getID(Integer.valueOf(idchat)),1);
                    } catch (SQLException e) {
                        e.printStackTrace();
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
