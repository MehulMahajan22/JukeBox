import java.sql.*;
import java.util.InputMismatchException;

public class User {

    public Connection createConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/MusicApp", "root", "admin@1234");

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return connection;
    }
    public int validateUser(String userName, String password){
        try {
            Connection connection = createConnection();
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select * from users");
            while(set.next()){
                if(userName.equalsIgnoreCase(set.getString(2)) && password.equals(set.getString(3)))
                    return set.getInt(4);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
//    public static void main(String[] args){
//        User u = new User();
//        String result = u.validateUser("Mahajan22","admin@1234");
//        System.out.println(result);
//    }
    public void displayWelcomeScreen(){
        System.out.println("Choose what you want to do");
        System.out.println("1. View Songs");
        System.out.println("2. View Podcasts");
        System.out.println("3. View Playlists");
        System.out.println("4. Create/Edit Playlist");
        System.out.println("5. View Songs sorted by Artists");
        System.out.println("6. View Songs sorted by Genre");
        System.out.println("7. View Songs sorted by Album");
        System.out.println("8. Search song");
        System.out.println("9. Search Podcast");
        System.out.println("10. Exit Jukebox");
    }

    public void signUpUser(String name, String uName, String password, long phoneNo){
        try{
                    int up = 0;
                    Connection connection = createConnection();
                    Statement statement = connection.createStatement();
                    up = statement.executeUpdate("insert into Users(FullName, UserName, Password, PhoneNo) values ('" + name + "','" + uName + "','" + password + "'," + phoneNo+")");
                    if (up != 0) {
                        System.out.println("Successfully Registered");
                    } else
                        System.out.println("Registration Unsuccessful");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public boolean checkUserNameAlreadyExists(String uName){
        boolean uNameExists=true;
        try {
            Connection connection1 = createConnection();
            Statement statement1 = connection1.createStatement();
            ResultSet set1 = statement1.executeQuery("select * from Users where UserName='" + uName + "'");
            uNameExists = set1.next();

        }
        catch (SQLException s){
            System.out.println("Username cannot have inverted commas");
            return false;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return uNameExists;
    }
    public boolean checkPhoneAlreadyExists(long phoneNo){
     boolean exists = true;
     try{
         Connection connection2 = createConnection();
         Statement statement2 = connection2.createStatement();
         ResultSet set2 = statement2.executeQuery("select * from Users where PhoneNo="+phoneNo);
         exists = set2.next();
     }
     catch (Exception e){
         System.out.println(e.getMessage());
         return false;
     }
     return exists;
    }
}
