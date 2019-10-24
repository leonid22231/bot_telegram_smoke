package com.lyadev.BD;

import java.sql.*;
import java.util.ArrayList;

public class bd {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("mysql://x7vsa0phsgb86kex:vrclco49rjal3i1p@pwcspfbyl73eccbn.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/r1zec69zx4y0kmdv","x7vsa0phsgb86kex","vrclco49rjal3i1p");

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
