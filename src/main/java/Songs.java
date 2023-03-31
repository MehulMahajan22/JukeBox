import javax.sound.sampled.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit.*;
import javax.sound.sampled.AudioInputStream;

public class Songs {
    static Statement createConnection() {
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


    public void printSongDetails() {
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select * from Songs");
            System.out.println("Song Number || Song Name || Song Duration");
            System.out.println("=========================================");
            while (set.next()) {
                System.out.println(set.getInt(1) + " => " + set.getString(2) + " || " + set.getString(6));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean playSong(int id) {
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select SongPath from songs where SongId=" + id);
            Boolean exists = set.next();
            if (exists == false) {
                System.out.println("Wrong song number entered");
                return false;
            }
            String filepath = set.getString(1);
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            String status = "playing";
            long pauseTime = 0;
            Scanner scan = new Scanner(System.in);
            System.out.println("To start the song from the beginning press 1");
            System.out.println("To jump to a particular time, press 5");
            int choice = scan.nextInt();
            while (choice != 1 && choice != 5) {
                System.out.println("Invalid Input, try again");
                System.out.println("To start the song from the beginning press 1");
                System.out.println("To jump to a particular time, press 5");
                System.out.println("To play next song enter 6");
                System.out.println("To play previous song enter 7");
                choice = scan.nextInt();
            }
            while (!status.equals("stop")) {
                if (choice > 7 || choice < 0) {
                    System.out.println("Invalid input");
                    System.out.println("To stop press 0");
                    System.out.println("To pause press 2");
                    System.out.println("To restart press 3");
                    System.out.println("To jump to a particular time, press 5");
                    System.out.println("To play next song enter 6");
                    System.out.println("To play previous song enter 7");
                    choice = scan.nextInt();
                }
                switch (choice) {
                    case 1:
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                        System.out.println("To stop press 0");
                        System.out.println("To pause press 2");
                        System.out.println("To restart press 3");
                        System.out.println("To jump to a particular time, press 5");
                        System.out.println("To play next song enter 6");
                        System.out.println("To play previous song enter 7");
                        choice = scan.nextInt();
                        break;
                    case 2:
                        pauseTime = clip.getMicrosecondPosition();

                        System.out.println("Paused at : " + convertMicrosecondsToMinutes(pauseTime) + "seconds");
                        System.out.println("Time remaining : " + convertMicrosecondsToMinutes(clip.getMicrosecondLength() - pauseTime) + " seconds");
                        clip.stop();
                        System.out.println("If you wish to stop playing press 0");
                        System.out.println("To start the song from the beginning press 1");
                        System.out.println("If you wish to resume playing press 4");
                        System.out.println("To jump to a particular time, press 5");
                        System.out.println("To play next song enter 6");
                        System.out.println("To play previous song enter 7");
                        choice = scan.nextInt();
                        break;
                    case 0:
                        System.out.println("Stopped at : " + convertMicrosecondsToMinutes(clip.getMicrosecondPosition()) + " seconds");
                        System.out.println("Time remaining : " + convertMicrosecondsToMinutes(clip.getMicrosecondLength() - clip.getMicrosecondPosition()) + " seconds");
                        clip.stop();
                        status = "stop";
                        break;
                    case 3:
                        clip.setMicrosecondPosition(0);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                        System.out.println("To stop press 0");
                        System.out.println("To pause press 1");
                        System.out.println("To restart press 2");
                        System.out.println("To jump to a particular time, press 5");
                        System.out.println("To play next song enter 6");
                        System.out.println("To play previous song enter 7");
                        choice = scan.nextInt();
                        break;
                    case 4:
                        clip.setMicrosecondPosition(pauseTime);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                        System.out.println("To stop press 0");
                        System.out.println("To pause press 1");
                        System.out.println("To restart press 2");
                        System.out.println("To jump to a particular time press 5");
                        System.out.println("To play next song enter 6");
                        System.out.println("To play previous song enter 7");
                        choice = scan.nextInt();
                        break;
                    case 5:
                        clip.stop();
                        System.out.println("Enter the time in seconds to start from");
                        double m = scan.nextDouble();
                        long c = (long) m * 1000000;
                        if (c > 0 && c < clip.getMicrosecondLength()) {
                            clip.stop();
                            clip.setMicrosecondPosition(c);
                            clip.start();
                            System.out.println("To stop press 0");
                            System.out.println("To pause press 2");
                            System.out.println("To restart press 3");
                            System.out.println("To jump to a particular time press 5");
                            System.out.println("To play next song enter 6");
                            System.out.println("To play previous song enter 7");
                            choice = scan.nextInt();
                            break;
                        } else {
                            System.out.println("Input time cannot be more than the song length or less than 0");
                        }
                        break;
                    case 7:
                        clip.stop();
                        status = "stop";
                        if (id == 1) {
                            playSong(totalSongsinDatabase());
                        } else {
                            playSong(id + 1);
                        }
                        break;
                    case 6:
                        clip.stop();
                        status = "stop";
                        int lastId = totalSongsinDatabase();
                        if (id == lastId) {
                            playSong(1);
                        } else {
                            playSong(id - 1);
                        }
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


    public String convertMicrosecondsToMinutes(long microseconds) {
        long totalSeconds = TimeUnit.MICROSECONDS.toSeconds(microseconds);
        int minutes = (int) totalSeconds / 60;
        int seconds = (int) totalSeconds % 60;
        String min, secs;
        if (minutes < 10) {
            min = "0" + String.valueOf(minutes);
        } else {
            min = String.valueOf(minutes);
        }
        if (seconds < 10) {
            secs = "0" + String.valueOf(seconds);
        } else {
            secs = String.valueOf(seconds);
        }
        String str = min + ":" + secs;
        return str;
    }

    public boolean playSongInPlaylist(int id, int pId) {
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select SongPath from songs where SongId=" + id);
            Boolean exists = set.next();
            if (exists == false) {
                System.out.println("Wrong song number entered");
                return false;
            }
            String filepath = set.getString(1);
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            String status = "playing";
            long pauseTime = 0;
            Scanner scan = new Scanner(System.in);
            System.out.println("To start the song from the beginning press 1");
            System.out.println("To jump to a particular time, press 5");
            int choice = scan.nextInt();
            while (choice != 1 && choice != 5) {
                System.out.println("Invalid Input, try again");
                System.out.println("To start the song from the beginning press 1");
                System.out.println("To jump to a particular time, press 5");
                System.out.println("To play next song enter 6");
                System.out.println("To play previous song enter 7");
                choice = scan.nextInt();
            }
            while (!status.equals("stop")) {
                if (choice > 7 || choice < 0) {
                    System.out.println("Invalid input");
                    System.out.println("To stop press 0");
                    System.out.println("To pause press 2");
                    System.out.println("To restart press 3");
                    System.out.println("To jump to a particular time, press 5");
                    System.out.println("To play next song enter 6");
                    System.out.println("To play previous song enter 7");
                    choice = scan.nextInt();
                }
                switch (choice) {
                    case 1:
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                        System.out.println("To stop press 0");
                        System.out.println("To pause press 2");
                        System.out.println("To restart press 3");
                        System.out.println("To jump to a particular time, press 5");
                        System.out.println("To play next song enter 6");
                        System.out.println("To play previous song enter 7");
                        choice = scan.nextInt();
                        break;
                    case 2:
                        pauseTime = clip.getMicrosecondPosition();
                        System.out.println("Paused at : " + convertMicrosecondsToMinutes(pauseTime) + "seconds");
                        System.out.println("Time remaining : " + convertMicrosecondsToMinutes(clip.getMicrosecondLength() - pauseTime) + " seconds");
                        clip.stop();
                        System.out.println("If you wish to stop playing press 0");
                        System.out.println("To start the song from the beginning press 1");
                        System.out.println("If you wish to resume playing press 4");
                        System.out.println("To jump to a particular time, press 5");
                        System.out.println("To play next song enter 6");
                        System.out.println("To play previous song enter 7");
                        choice = scan.nextInt();
                        break;
                    case 0:
                        System.out.println("Stopped at : " + convertMicrosecondsToMinutes(clip.getMicrosecondPosition()) + " seconds");
                        System.out.println("Time remaining : " + convertMicrosecondsToMinutes(clip.getMicrosecondLength() - clip.getMicrosecondPosition()) + " seconds");
                        clip.stop();
                        status = "stop";
                        break;
                    case 3:
                        clip.setMicrosecondPosition(0);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                        System.out.println("To stop press 0");
                        System.out.println("To pause press 1");
                        System.out.println("To restart press 2");
                        System.out.println("To jump to a particular time, press 5");
                        System.out.println("To play next song enter 6");
                        System.out.println("To play previous song enter 7");
                        choice = scan.nextInt();
                        break;
                    case 4:
                        clip.setMicrosecondPosition(pauseTime);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                        System.out.println("To stop press 0");
                        System.out.println("To pause press 1");
                        System.out.println("To restart press 2");
                        System.out.println("To jump to a particular time press 5");
                        System.out.println("To play next song enter 6");
                        System.out.println("To play previous song enter 7");
                        choice = scan.nextInt();
                        break;
                    case 5:
                        clip.stop();
                        System.out.println("Enter the time in seconds to start from");
                        double m = scan.nextDouble();
                        long c = (long) m * 1000000;
                        if (c > 0 && c < clip.getMicrosecondLength()) {
                            clip.stop();
                            clip.setMicrosecondPosition(c);
                            clip.start();
                            System.out.println("To stop press 0");
                            System.out.println("To pause press 2");
                            System.out.println("To restart press 3");
                            System.out.println("To jump to a particular time press 5");
                            System.out.println("To play next song enter 6");
                            System.out.println("To play previous song enter 7");
                            choice = scan.nextInt();
                            break;
                        } else {
                            System.out.println("Input time cannot be more than the song length or less than 0");
                        }
                        break;
                    case 7:
                        clip.stop();
                        status = "stop";
                        Playlist pl = new Playlist();
                        ArrayList<Integer> songIds = pl.getSongIdsInPlaylist(pId);
                        ArrayList<Integer> previous = new ArrayList<>();
                        previous.add(-1);
                        previous.addAll(songIds);
                        int prId;
                        Iterator<Integer> iterator = songIds.iterator();
                        Iterator<Integer> iterator1 = previous.iterator();
                        while (iterator1.hasNext() && iterator.hasNext()) {
                            prId = iterator1.next();
                            if (iterator.next() == id) {
                                if (prId == -1) {
                                    int i = songIds.get(songIds.size()-1);
                                    playSongInPlaylist(i, pId);
                                } else {
                                    playSongInPlaylist(prId, pId);
                                    choice = 0;
                                }
                                break;
                            }
                        }
                        break;
                    case 6:
                        clip.stop();
                        status = "stop";
                        Playlist play = new Playlist();
                        ArrayList<Integer> songIds1 = play.getSongIdsInPlaylist(pId);
                        ArrayList<Integer> next = new ArrayList<>();
                        next.addAll(songIds1);
                        int nxtId;
                        int cId;
                        Iterator<Integer> iterator2 = songIds1.iterator();
                        while (iterator2.hasNext()) {
                            if ((cId = iterator2.next()) == id) {
                                if (!iterator2.hasNext()) {
                                    int i = songIds1.get(0);
                                    playSongInPlaylist(i, pId);
                                } else {
                                    nxtId = iterator2.next();
                                    playSongInPlaylist(nxtId, pId);
                                    choice = 0;
                                }
                                break;
                            }
                        }
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }


    public void viewSongsByArtists() {
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select ArtistName, SongName, SongId from songs order by ArtistName");
            System.out.println("Song No || Artist Name || SongName ");
            System.out.println("====================================");
            while (set.next()) {
                System.out.println(set.getInt(3) + " => " + set.getString(1) + " || " + set.getString(2));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewSongsByGenre() {
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select Genre, SongName, SongId from songs order by Genre");
            System.out.println("Song No || Artist Name || SongName ");
            System.out.println("====================================");
            while (set.next()) {
                System.out.println(set.getInt(3) + " => " + set.getString(1) + " || " + set.getString(2));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void viewSongsByAlbum() {
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select distinct(AlbumName), SongName, SongId from songs order by Genre");
            System.out.println("Song No || Artist Name || SongName ");
            System.out.println("====================================");
            while (set.next()) {
                System.out.println(set.getInt(3) + " => " + set.getString(1) + " || " + set.getString(2));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int searchByName(String songName) {
        int i = 0;
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select * from songs where SongName='" + songName + "'");
            boolean exists = set.next();
            if (exists == false) {
                return i;
            }
            System.out.println("Song Number || Song Name || Song Duration");
            System.out.println("=========================================");
            System.out.println(set.getInt(1) + " => " + set.getString(2) + " || " + set.getString(6));
            i = set.getInt(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return i;
    }

    public ArrayList<Integer> searchByArtist(String Artist) {
        ArrayList<Integer> i = new ArrayList<>();
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select * from songs where ArtistName='" + Artist + "'");
            while (set.next()) {

                System.out.println("-----------------------------");
                System.out.println("Song Name : " + set.getString(2));
                System.out.println("Artist Name : " + set.getString(3));
                System.out.println("Song Number : " + set.getInt(1));
                System.out.println("------------------------------");
                i.add(set.getInt(1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return i;
    }

    public ArrayList<Integer> searchByAlbum(String Album) {
        ArrayList<Integer> i = new ArrayList<>();
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select * from songs where AlbumName='" + Album + "'");
            while (set.next()) {

                System.out.println("-----------------------------");
                System.out.println("Song Name : " + set.getString(2));
                System.out.println("Album Name : " + set.getString(4));
                System.out.println("Song Number : " + set.getInt(1));
                System.out.println("------------------------------");
                i.add(set.getInt(1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return i;
    }

    public ArrayList<Integer> searchByGenre(String Genre) {
        ArrayList<Integer> i = new ArrayList<>();
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select * from songs where Genre='" + Genre + "'");
            while (set.next()) {

                System.out.println("-----------------------------");
                System.out.println("Song Name : " + set.getString(2));
                System.out.println("Genre Name : " + set.getString(5));
                System.out.println("Song Number : " + set.getInt(1));
                System.out.println("------------------------------");
                i.add(set.getInt(1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return i;
    }

    public int totalSongsinDatabase() {
        int total = 0;
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select count(SongId) from songs");
            set.next();
            total = set.getInt(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return total;
    }
}
