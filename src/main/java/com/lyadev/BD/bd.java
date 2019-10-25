package com.lyadev.BD;

import com.google.inject.internal.cglib.core.$ClassEmitter;
import com.sun.org.apache.bcel.internal.generic.SWITCH;

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
            + "USERNAME_FIRSTNAME TEXT NOT NULL, "
            + "USERNAME_SECONDNAME TEXT , "
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
    public static void AddUser(String name ,String lastname, Long id) throws SQLException
    {
        String insertTableSQL = null;
        try {

            insertTableSQL = "INSERT INTO USERS"
                    + "(USER_LOCAL_ID,USERNAME_FIRSTNAME, USERNAME_SECONDNAME, ID, STATE, ADMIN) " + "VALUES"
                    + "("+ getUsers().size()+"," + "'"+name+ "'"+","+"'"+lastname+"'"+","+id+","+"'Start'"+","+"'FALCE')";
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
public static ArrayList<ArrayList<String>> getUsers() throws ClassNotFoundException, SQLException{
    resSet = statmt.executeQuery("SELECT * FROM USERS");
    ArrayList<ArrayList<String>> users = new ArrayList<>();


    users.clear();

    while(resSet.next()){
        System.out.println(resSet.getString("USERNAME_FIRSTNAME"));
        ArrayList<String> users_firstname = new ArrayList<>();
        users_firstname.add(resSet.getString("USERNAME_FIRSTNAME"));
        users_firstname.add(resSet.getString("USERNAME_SECONDNAME"));
        users_firstname.add(String.valueOf(resSet.getInt("ID")));
        users.add(users_firstname);

    }

    return users;

    }
    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        if(conn!=null){
        conn.close();}
        if(statmt!=null){
        statmt.close();}
        if(resSet!=null){
        resSet.close();}

        System.out.println("Соединения закрыты");
    }



    ////STRING
   public String addgenerate(){

        return "";
    }

public static void changename(int ID, String name){
    try {
        statmt.executeUpdate("UPDATE USERS SET USERNAME_FIRSTNAME = " + "'" + name + "'"+" WHERE USER_LOCAL_ID = "+ID);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static void changesname(int ID, String sname){
        try {
            statmt.executeUpdate("UPDATE USERS SET USERNAME_SECONDNAME = " + "'" + sname + "'"+" WHERE USER_LOCAL_ID = "+ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void changestate(int ID,int state){
        try {
            switch (state) {
                case 0 :
                    statmt.executeUpdate("UPDATE USERS SET STATE = " + "'" + "MENU" + "'" + " WHERE USER_LOCAL_ID = " + ID);
                    break;
                case 1:
                    statmt.executeUpdate("UPDATE USERS SET STATE = " + "'" + "MENU_1" + "'" + " WHERE USER_LOCAL_ID = " + ID);
                    break;
                case 2:
                    statmt.executeUpdate("UPDATE USERS SET STATE = " + "'" + "Menu_2" + "'" + " WHERE USER_LOCAL_ID = " + ID);
                    break;
                case 3:
                    statmt.executeUpdate("UPDATE USERS SET STATE = " + "'" + "Menu_3" + "'" + " WHERE USER_LOCAL_ID = " + ID);
                    break;
                case 4:
                    statmt.executeUpdate("UPDATE USERS SET STATE = " + "'" + "Menu_4" + "'" + " WHERE USER_LOCAL_ID = " + ID);
                    break;
                case 5:
                    statmt.executeUpdate("UPDATE USERS SET STATE = " + "'" + "Menu_5" + "'" + " WHERE USER_LOCAL_ID = " + ID);
                    break;
                case 9:
                    statmt.executeUpdate("UPDATE USERS SET STATE = " + "'" + "DEV_PASS" + "'" + " WHERE USER_LOCAL_ID = " + ID);
                    break;
                default:
                    statmt.executeUpdate("UPDATE USERS SET STATE = " + "'" + "MENU" + "'" + " WHERE USER_LOCAL_ID = " + ID);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int getID(int telegram_id) throws SQLException {
        int Id = 0;
        resSet = statmt.executeQuery("SELECT USER_LOCAL_ID FROM USERS WHERE ID = "+telegram_id);
        while(resSet.next()) {
           Id = resSet.getInt("USER_LOCAL_ID");
        }
        return Id;
    }
}
