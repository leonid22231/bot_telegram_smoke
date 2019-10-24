package com.lyadev.BD;

import java.net.*;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;

public class bd {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    static String createTableSQL = "CREATE TABLE USERS("
            + "USER_LOCAL_ID INT NOT NULL, "
            + "USERNAME TEXT NOT NULL, "
            + "ID INT NOT NULL, "
            + "STATE TEXT NOT NULL, "
            + "ADMIN VARCHAR(5) NOT NULL, "
            + "PRIMARY KEY (USER_LOCAL_ID) "
            + ")";
    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws URISyntaxException, ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        URI jdbUri = new URI("mysql://x7vsa0phsgb86kex:vrclco49rjal3i1p@pwcspfbyl73eccbn.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/r1zec69zx4y0kmdv");

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
        try {
            statmt.execute(createTableSQL);
        }catch (SQLException e){
            System.out.println("Уже существует");
        }


        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void AddUser(String name , Long id) throws SQLException
    {
        String insertTableSQL = null;
        try {
            insertTableSQL = "INSERT INTO USERS"
                    + "(USER_LOCAL_ID,USERNAME, ID, STATE, ADMIN) " + "VALUES"
                    + "("+ getUsers().size()+"," + "'"+name+ "'"+","+id+","+"'Start'"+","+"'FALCE')";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        statmt.execute(insertTableSQL);

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
public static ArrayList<String> getUsers() throws ClassNotFoundException, SQLException{
    resSet = statmt.executeQuery("SELECT * FROM USERS");
    ArrayList<String> users = new ArrayList<>();
    System.out.println("LOG "+ resSet);
    while(resSet.next()){
        users.add(resSet.getString("USERNAME"));
    }
    System.out.println(users.get(0));
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
    ////STRING
   public String addgenerate(){

        return "";
    }

}
