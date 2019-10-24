package com.lyadev.BD;

import java.net.*;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;

public class bd {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws URISyntaxException, ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        URI jdbUri = new URI(System.getenv("pwcspfbyl73eccbn.cbetxkdyhwsb.us-east-1.rds.amazonaws.com"));

        String username = jdbUri.getUserInfo().split(":")[0];
        String password = jdbUri.getUserInfo().split(":")[1];
        String port = String.valueOf(jdbUri.getPort());
        String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();
        conn = DriverManager.getConnection(jdbUrl,username, password);

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'r1zec69zx4y0kmdv' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'user_id' INT, 'state' text);");

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void AddUser(String name , int id) throws SQLException
    {
        statmt.execute("INSERT INTO 'r1zec69zx4y0kmdv' ('name', 'user_id', 'state') VALUES (name, id, 'start'); ");

        System.out.println("User"+ name + " added");
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM users");

        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  name = resSet.getString("name");
            String  phone = resSet.getString("chat");
            System.out.println( "ID = " + id );
            System.out.println( "name = " + name );
            System.out.println( "chat = " + phone );
            System.out.println();
        }

        System.out.println("Таблица выведена");
    }
public static ArrayList<Integer> getUsers() throws ClassNotFoundException, SQLException{
    resSet = statmt.executeQuery("SELECT * FROM users");
    ArrayList<Integer> users = new ArrayList<>();
    while(resSet.next()){
        users.add(resSet.getInt("user_id"));
    }
    return users;
}
    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }
}
