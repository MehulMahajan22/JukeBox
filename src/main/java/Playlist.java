import java.sql.*;
import java.util.ArrayList;

public class Playlist {
    public Statement createConnection() {
        Statement statement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/MusicApp", "root", "admin@1234");
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return statement;
    }

    public void displayPlaylistNames(int UserId) {
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select distinct(PlaylistName),PlaylistId from playlists where UserId=" + UserId);
            System.out.println("Playlist Number || Playlist Name");
            while (set.next()) {
                System.out.println( set.getInt(2)+ " => " + set.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int showContentsOfPlaylist(int pId) {
        int plId = 0;
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select * from playlists where PlaylistId=" + pId);
            boolean exists = set.next();
            if (exists == false) {
                return plId;
            } else {
                plId = pId;
                System.out.println("Playlist Name : " + set.getString(2));
                String songIds = set.getString(4);
                String podcastIds = set.getString(5);
                if(songIds!=null) {
                    System.out.println("-------------------------");
                    System.out.println("Song Number || Song Name");
                    Statement statement1 = createConnection();
                    ResultSet set1 = statement1.executeQuery("select * from songs where SongId in (" + songIds + ")");
                    while (set1.next()) {
                        System.out.println(set1.getInt(1) + " => " + set1.getString(2));
                    }
                }
                if(podcastIds!=null) {
                    System.out.println("-------------------------------");
                    System.out.println("Podcast Number || Podcast Name");
                    Statement statement2 = createConnection();
                    ResultSet set2 = statement2.executeQuery("select * from podcasts where PodcastId in (" + podcastIds + ")");
                    while (set2.next()) {
                        System.out.println(set2.getInt(1) + " => " + set2.getString(2));
                    }
                }
                if(songIds==null && podcastIds == null){
                    System.out.println("Empty playlist");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return plId;
    }
    public void showContentsOfPlaylist(String pName) {
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select * from playlists where PlaylistName='"+pName+"'");
            boolean exists = set.next();
            if (exists == false) {
                return;
            } else {
                System.out.println("Playlist Name : " + set.getString(2));
                String songIds = set.getString(4);
                String podcastIds = set.getString(5);
                if(songIds!=null) {
                    System.out.println("-------------------------");
                    System.out.println("Song Number || Song Name");
                    Statement statement1 = createConnection();
                    ResultSet set1 = statement1.executeQuery("select * from songs where SongId in (" + songIds + ")");
                    while (set1.next()) {
                        System.out.println(set1.getInt(1) + " => " + set1.getString(2));
                    }
                }
                if(podcastIds!=null) {
                    System.out.println("-------------------------------");
                    System.out.println("Podcast Number || Podcast Name");
                    Statement statement2 = createConnection();
                    ResultSet set2 = statement2.executeQuery("select * from podcasts where PodcastId in (" + podcastIds + ")");
                    while (set2.next()) {
                        System.out.println(set2.getInt(1) + " => " + set2.getString(2));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Integer> getSongIdsInPlaylist(int pId) {
        ArrayList<Integer> i = new ArrayList<>();
        try {

            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select SongIds from playlists where PlaylistId=" + pId);
            set.next();
            if (set.getString(1)==null){
                return i;
            }
            String data = set.getString(1);
            String IDs[] = data.split(",");
            for (int j = 0; j < IDs.length; j++) {
                i.add(Integer.parseInt(IDs[j]));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return i;
    }
    public ArrayList<Integer> getPodcastIdsInPlaylist(int pId) {
        ArrayList<Integer> i = new ArrayList<>();
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select PodcastIds from playlists where PlaylistId=" + pId);
            set.next();
            if (set.getString(1)==null){
                return i;
            }
            String data = set.getString(1);
            String IDs[] = data.split(",");
            for (int j = 0; j < IDs.length; j++) {
                i.add(Integer.parseInt(IDs[j]));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return i;
    }
    public int createNewPlaylist(String pName, int userId, String songIds, String podcastIds){
        int s=0;
        try {
            Statement statement = createConnection();
            if(songIds!=null && podcastIds!=null) {
                s = statement.executeUpdate("Insert into playlists(UserId, PlaylistName, SongIds, PodcastIds) values(" + userId + ",'" + pName + "','" + songIds + "','" + podcastIds + "')");
            }
            else if (songIds==null && podcastIds==null)
            {
                s = statement.executeUpdate("Insert into playlists(UserId, PlaylistName) values(" + userId + ",'" + pName + "')");
            }
            else if (songIds!=null && podcastIds==null) {
                s = statement.executeUpdate("Insert into playlists(UserId, PlaylistName, SongIds) values(" + userId + ",'" + pName + "','" + songIds + "')");
            }
            else {
                s = statement.executeUpdate("Insert into playlists(UserId, PlaylistName, PodcastIds) values(" + userId + ",'" + pName + "','" + podcastIds + "')");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return s;
    }
    public boolean checkPlaylistName(String pName, int userId){
        try{
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select * from Playlists where PlaylistName='"+pName+"' and UserId="+userId);
            if(set.next()){
               return true;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public int addSongToPlaylist(String pName, int SongId, int userId){
        int s = 0;
        try{
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select SongIds from playlists where PlaylistName='"+pName+"' and UserId="+userId);
            boolean exists = set.next();
            if (exists==false){
                System.out.println("No playlist found with the entered name");
                return s;
            }
            String songIds = "";
            if(set.getString(1)==null) {
            songIds = String.valueOf(SongId);
            }
            else {
                songIds = set.getString(1);
                songIds = songIds.concat("," + String.valueOf(SongId));
            }
            Statement statement1 = createConnection();
            s = statement1.executeUpdate("update playlists set SongIds='"+songIds+"' where PlaylistName='"+pName+"' and UserId="+userId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return s;
    }
    public int deleteSongFromPlaylist(String pName, int SongId, int userId){
        int s = 0;
        try{
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select SongIds from playlists where PlaylistName='"+pName+"' and UserId="+userId);
            boolean exists = set.next();
            if (exists==false){
                System.out.println("No playlist found with the entered name");
                return s;
            }
            if(set.getString(1)==null){
                System.out.println("Empty playlist");
                return 0;
            }
            String songIds = set.getString(1);
            String StringIds[] = songIds.split(",");
            int ids[] = new int[StringIds.length];
            for(int a=0;a<StringIds.length;a++){
                ids[a]=Integer.parseInt(StringIds[a]);
            }
            int removalIndex=-1;
            for (int j=0;j<ids.length;j++){
                if(ids[j]==SongId){
                    removalIndex=j;
                }
            }
            if(removalIndex==-1){
                System.out.println("Song not available in playlist");
                return 0;
            }
            String newSongIds;
            if(removalIndex==0){
                newSongIds = String.valueOf(ids[1]);
                for (int b=2;b<ids.length;b++){
                    newSongIds=newSongIds.concat(","+String.valueOf(ids[b]));
                }
            }
            else {
                newSongIds = String.valueOf(ids[0]);
                for (int b = 1; b < ids.length; b++) {
                    if(b!=removalIndex) {
                        newSongIds = newSongIds.concat("," + String.valueOf(ids[b]));
                    }
                }
            }
            Statement statement1 = createConnection();
            s = statement1.executeUpdate("update playlists set SongIds='"+newSongIds+"' where PlaylistName='"+pName+"' and UserId="+userId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return s;
    }
    public boolean checkIfSongAlreadyInPlaylist(String pName, int songId, int userId){
        try{
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select SongIds from playlists where PlaylistName='"+pName+"' and UserId="+userId);
            set.next();
            if (set.getString(1)==null){
                return  false;
            }
            String s = set.getString(1);
            String ids[] = s.split(",");
            for(int i=0;i<ids.length;i++){
                if(Integer.parseInt(ids[i])==songId){
                    return true;
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean checkIfPodcastAlreadyInPlaylist(String pName, int podcastId, int userId){
        try{
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select PodcastIds from playlists where PlaylistName='"+pName+"' and UserId="+userId);
            set.next();
            if (set.getString(1)==null){
                return  false;
            }
            String s = set.getString(1);
            String ids[] = s.split(",");
            for(int i=0;i<ids.length;i++){
                if(Integer.parseInt(ids[i])==podcastId){
                    return true;
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public int addPodcastToPlaylist(String pName, int podcastId, int userId){
        int i=0;
        try{
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select SongIds from playlists where PlaylistName='"+pName+"' and UserId="+userId);
            boolean exists = set.next();
            if (exists==false){
                System.out.println("No playlist found with the entered name");
                return i;
            }
            String podcastIds = "";
            if(set.getString(1)==null){
                podcastIds =  String.valueOf(podcastId);
            }
            else {
                podcastIds =set.getString(1);
                podcastIds = podcastIds.concat("," + String.valueOf(podcastId));
            }
            Statement statement1 = createConnection();
            i = statement1.executeUpdate("update playlists set PodcastIds='"+podcastIds+"' where PlaylistName='"+pName+"' and UserId="+userId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return i;
    }
    public int deletePodcastFromPlaylist(String pName, int podcastId, int userId){
        int i=0;
        try{
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select PodcastIds from playlists where PlaylistName='"+pName+"' and UserId="+userId);
            boolean exists = set.next();
            if (exists==false){
                System.out.println("No playlist found with the entered name");
                return i;
            }
            if(set.getString(1)==null){
                System.out.println("Empty playlist");
                return 0;
            }
            String podcastIds = set.getString(1);
            String StringIds[] = podcastIds.split(",");
            int ids[] = new int[StringIds.length];
            for(int a=0;a<StringIds.length;a++){
                ids[a]=Integer.parseInt(StringIds[a]);
            }
            int removalIndex=-1;
            for (int j=0;j<ids.length;j++){
                if(ids[j]==podcastId){
                    removalIndex=j;
                }
            }
            if(removalIndex==-1){
                System.out.println("Podcast not available in playlist");
                return 0;
            }
            String newPodcastIds;
            if(removalIndex==0){
                newPodcastIds = String.valueOf(ids[1]);
                int b=2;
                while(b<ids.length) {
                    newPodcastIds = newPodcastIds.concat("," + String.valueOf(ids[b]));
                    b++;
                }
            }
            else {
                newPodcastIds = String.valueOf(ids[0]);
                int b=1;
                while (b<ids.length){
                    if(b!=removalIndex) {
                        newPodcastIds = newPodcastIds.concat("," + String.valueOf(ids[b]));
                        b++;
                    }
                }
            }
            Statement statement1 = createConnection();
            i = statement1.executeUpdate("update playlists set PodcastIds='"+newPodcastIds+"' where PlaylistName='"+pName+"' and UserId="+userId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return i;
    }

}
