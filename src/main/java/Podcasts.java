import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.sql.ResultSet;
import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Podcasts {
    int currentPodcastId = 0;
    int currentEpisodeId = 0;


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

    public void printPodcastDetails() {
        try {

            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select * from Podcasts");
            System.out.println("Podcast Number || Podcast Title || Podcast Description || No. of Episodes");
            System.out.println("==========================================================================");
            while (set.next()) {
                System.out.println(set.getInt(1) + " => " + set.getString(2) + " || " + set.getString(3) + " || " + set.getString(4));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean printPodcastEpisodes(int podcastId) {
        try {
            currentPodcastId = podcastId;
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select * from podcastepisodes where PodcastId=" + podcastId);
            System.out.println("Episode Number || Episode Title || Artist || Episode Description ");
            int count = 1;
            while (set.next()) {
                System.out.println(count + " => " + set.getString(3) + " || " + set.getString(6) + " || " + set.getString(4));
                count++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public boolean playPodcast(int episodeNo) {
        try {
            Statement statement2 = createConnection();
            ResultSet set2 = statement2.executeQuery("select NoOfEpisodes from podcasts where PodcastId=" + currentPodcastId);
            set2.next();
            int noOfEpisodes = set2.getInt(1);
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select * from podcastepisodes where PodcastId=" + currentPodcastId);
            int count = 1;
            while (set.next()) {
                if (episodeNo == count) {
                    currentEpisodeId = set.getInt(2);
                }
                count++;
            }
            ResultSet set1 = statement.executeQuery("select EpisodePath from podcastepisodes where EpisodeId=" + currentEpisodeId);
            boolean exists = set1.next();
            if (!exists) {
                System.out.println("Wrong episode number entered");
                return false;
            }
            String filepath = set1.getString(1);
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            String status = "playing";
            long pauseTime = 0;
            Scanner scan = new Scanner(System.in);
            System.out.println("To start the Podcast from the beginning press 1");
            System.out.println("To jump to a particular time press 5");
            int choice = scan.nextInt();
            while (!status.equalsIgnoreCase("stop")) {
                if (choice > 7 || choice < 0) {
                    System.out.println("Invalid input");
                    System.out.println("To stop press 0");
                    System.out.println("To pause press 2");
                    System.out.println("To restart press 3");
                    System.out.println("To jump to a particular time, press 5");
                    System.out.println("To play next episode enter 6");
                    System.out.println("To play previous episode enter 7");
                    choice = scan.nextInt();
                }
                switch (choice) {
                    case 0 -> {
                        System.out.println("Stopped at : " + convertMicrosecondsToMinutes(clip.getMicrosecondPosition()) + " seconds");
                        System.out.println("Time remaining : " + convertMicrosecondsToMinutes(clip.getMicrosecondLength() - clip.getMicrosecondPosition()) + " seconds");
                        clip.stop();
                        status = "stop";
                    }
                    case 1 -> {
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                        System.out.println("To stop press 0");
                        System.out.println("To pause press 2");
                        System.out.println("To restart press 3");
                        System.out.println("To jump to a particular time press 5");
                        System.out.println("To play next episode enter 6");
                        System.out.println("To play previous episode enter 7");
                        choice = scan.nextInt();
                    }

                    case 2 -> {
                        pauseTime = clip.getMicrosecondPosition();
                        System.out.println("Paused at : " + convertMicrosecondsToMinutes(pauseTime) + "seconds");
                        System.out.println("Time remaining : " + convertMicrosecondsToMinutes(clip.getMicrosecondLength() - pauseTime) + " seconds");
                        clip.stop();
                        System.out.println("If you wish to stop playing press 0");
                        System.out.println("To start the song from the beginning press 1");
                        System.out.println("If you wish to resume playing press 4");
                        System.out.println("To jump to a particular time, press 5");
                        System.out.println("To play next episode enter 6");
                        System.out.println("To play previous episode enter 7");
                        choice = scan.nextInt();
                    }
                    case 3 -> {
                        clip.setMicrosecondPosition(0);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                        System.out.println("To stop press 0");
                        System.out.println("To pause press 1");
                        System.out.println("To restart press 2");
                        System.out.println("To jump to a particular time, press 5");
                        System.out.println("To play next episode enter 6");
                        System.out.println("To play previous episode enter 7");
                        choice = scan.nextInt();
                    }
                    case 4 -> {
                        clip.setMicrosecondPosition(pauseTime);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                        System.out.println("To stop press 0");
                        System.out.println("To pause press 1");
                        System.out.println("To restart press 2");
                        System.out.println("To jump to a particular time press 5");
                        System.out.println("To play next episode enter 6");
                        System.out.println("To play previous episode enter 7");
                        choice = scan.nextInt();
                    }
                    case 5 -> {
                        clip.stop();
                        System.out.println("Enter the time in seconds to start from");
                        double m = scan.nextDouble();
                        long c = (long) m * 1000000;
                        if (c > 0 && c < clip.getMicrosecondLength()) {
                            clip.stop();
                            clip.setMicrosecondPosition(c);
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                            clip.start();
                            System.out.println("To stop press 0");
                            System.out.println("To pause press 2");
                            System.out.println("To restart press 3");
                            System.out.println("To jump to a particular time press 5");
                            System.out.println("To play next episode enter 6");
                            System.out.println("To play previous episode enter 7");
                            choice = scan.nextInt();

                        } else {
                            System.out.println("Input time cannot be more than the song length or less than 0");
                        }
                    }
                    case 6 -> {
                        clip.stop();
                        status = "stop";
                        int cEP = noOfEpisodes;
                        if (episodeNo == cEP) {
                            choice = 1;
                            playPodcast(1);
                            break;
                        } else {
                            int a = episodeNo + 1;
                            choice = 1;
                            playPodcast(a);
                            break;
                        }
                    }
                    case 7 -> {
                        clip.stop();
                        status = "stop";
                        if (episodeNo == 1) {
                            int prEp = noOfEpisodes;
                            playPodcast(prEp);
                            choice = 0;
                        } else {
                            int b = episodeNo - 1;
                            playPodcast(b);
                            choice = 0;
                        }
                    }
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

    public int getTotalPodcastsInDatabase() {
        int total = 0;
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select count(PodcastId) from podcasts");
            set.next();
            total = set.getInt(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return total;
    }

    public int searchPodcastByName(String pName) {
        int pId = 0;
        try {
            Statement statement = createConnection();
            ResultSet set = statement.executeQuery("select * from podcasts where PodcastTitle='" + pName + "'");
            if (!set.next()) {
                return pId;
            }
            System.out.println("------------------------------------------");
            System.out.println("Podcast Title : " + set.getString(2));
            System.out.println("Podcast Description : " + set.getString(3));
            System.out.println("Podcast Number : " + set.getInt(1));
            System.out.println("------------------------------------------");
            pId = set.getInt(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return pId;
    }
}
