package db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class ConnectionToDB{
public static List<String> users = new LinkedList<>();
    public static void main(String[] args) {

//        incertNewUser("hhh","443");

//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//            try (Connection conn = getConnection()){
//
//                System.out.println("Connection to Store DB succesfull!");
//                Statement statement = conn.createStatement();
//
//
////String sqlCommand = "INSERT  `mydb`.`accounts` (name, password) VALUES ('user3', '4')";
////statement.executeUpdate(sqlCommand);
//            /** возвращаем всех пользователей
//             *
//             * */
//
////                ResultSet resultSet = statement.executeQuery("SELECT * FROM `mydb`.`accounts`");
////                while (resultSet.next()){
////                    String user = resultSet.getString(1);
////                    System.out.printf("%s \n", user);
////                }
//
//
//            }
//        }
//        catch(Exception ex){
//            System.out.println("Connection failed...");
//
//            System.out.println(ex);
//        }
    }
     public static   void incertNewUser(String userName, String password){
         try{
             Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
             try (Connection conn = getConnection()){

                 System.out.println("Connection to Store DB succesfull!");
                 Statement statement = conn.createStatement();


String sqlCommand = "INSERT INTO `mydb`.`accounts` (`name`, `password`) VALUES ('"+userName+"', '"+password+"')";
StringBuilder stringBuilder = new StringBuilder();
stringBuilder.append("INSERT  `mydb`.`accounts` (name, password) VALUES (");
stringBuilder.append(userName);
                 stringBuilder.append(", ");
                 stringBuilder.append(password);
                 stringBuilder.append(")");
                 String command = stringBuilder.toString();
                 System.out.println(command);
statement.executeUpdate(sqlCommand);
                 /** возвращаем всех пользователей
                  *
                  * */

//                ResultSet resultSet = statement.executeQuery("SELECT * FROM `mydb`.`accounts`");
//                while (resultSet.next()){
//                    String user = resultSet.getString(1);
//                    System.out.printf("%s \n", user);
//                }


             }
         }
         catch(Exception ex){
             System.out.println("Connection failed...");

             System.out.println(ex);
         }
     }

    public static   boolean isUserExist(String login){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = getConnection()){
                System.out.println("Connection to Store DB succesfull!");
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM `mydb`.`accounts`");
                while (resultSet.next()){
                    String user = resultSet.getString(1);
                    if(user.equals(login)){
                        System.out.println("already exist");
                        return true;
                    }

                }


        }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }

        return false;
    }

     public static boolean logIn(String login, String password){
         try{
             Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
             try (Connection conn = getConnection()){
                 System.out.println("Connection to Store DB succesfull!");
                 Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM `mydb`.`accounts`");
                 while (resultSet.next()){
                     String user = resultSet.getString(1);
                    String pass = resultSet.getString(2);
                    if((user.equals(login)) && (pass.equals(password))){
                        System.out.println("Sign In !!");
                        return true;
                    }
                 }


             }
         }
         catch(Exception ex){
             System.out.println("Connection failed...");

             System.out.println(ex);
         }
         System.out.println("не вышло");
        return false;
     }


    public static Connection getConnection() throws SQLException, IOException{

        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("C:\\Users\\12\\Desktop\\проекты\\ExampleMavenJavaFX\\Common\\src\\main\\java\\db\\database.properties"))){
            props.load(in);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }
}
