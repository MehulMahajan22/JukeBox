import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Implementation {
    public static void main(String args[]) {
        try {
            Songs s = new Songs();
            Podcasts p = new Podcasts();
            Playlist pl = new Playlist();
            Scanner scan = new Scanner(System.in);
            int UserId = 0;
            String userName = "";
            User u = new User();
            while (UserId == 0) {
                System.out.println("================================================================");
                System.out.println("==================  Welcome To Jukebox  ========================");
                System.out.println("================================================================");
                System.out.println("1. Sing In");
                System.out.println("2. Sing up");
                System.out.println("3. Exit Jukebox");
                int i = scan.nextInt();
                switch (i) {
                    case 1:
                        System.out.println("Input Username");
                        userName = scan.next();
                        System.out.println("Input Password");
                        String password = scan.next();
                        UserId = u.validateUser(userName, password);
                        if (UserId == 0)
                            System.out.println("Invalid Credentials, please try again");
                    break;
                    case 2:
                        Scanner sc = new Scanner(System.in);
                        System.out.println("Input your Full Name");
                        String fullName = sc.nextLine();
                        boolean userNameExist = true;
                        String uName=null;
                        while (userNameExist) {
                            System.out.println("Input Username (username cannot have spaces");
                            uName = sc.next();
                            userNameExist = u.checkUserNameAlreadyExists(uName);
                            if(userNameExist){
                                System.out.println("UserName already exists, try another username");
                            }
                        }
                        System.out.println("Input password");
                        String password1 = sc.next();
                        System.out.println("Confirm Password");
                        String cpassword = sc.next();
                        if(!password1.equalsIgnoreCase(cpassword)){
//                            if(password1.contains())
                            System.out.println("Password and confirm password did not match");
                            i=2;
                            break;
                        }
                        System.out.println("Input phone Number");
                        long phoneNo = scan.nextLong();
                        boolean phoneNoExists = u.checkPhoneAlreadyExists(phoneNo);
                        if(phoneNoExists){
                            System.out.println("Phone no already linked to another user");
                            i=2;
                            break;
                        }
                        u.signUpUser(fullName, uName, password1, phoneNo);
                    break;
                    case 3:
                        System.out.println("Thankyou for using jukebox");
                        System.exit(0);
                    break;
                    default:
                        System.out.println("Incorrect input, try again");
                    break;
                }
            }
            System.out.println("Welcome " + userName);
            int a = 0;
            while (a < 1 || a > 10) {
                u.displayWelcomeScreen();
                a = scan.nextInt();
                if (a < 1 || a > 10)
                    System.out.println("Invalid Input! Try again");
            }
            while (true) {
                switch (a) {

                    case 0:
                        while (a < 1 || a > 10) {
                            u.displayWelcomeScreen();
                            a = scan.nextInt();
                        }
                        break;

                    case 1:
                        s.printSongDetails();
                        System.out.println("Enter the song number you want to play or input 0 to go back to main menu");
                        System.out.println("Enter -1 to exit jukebox");
                        int b = scan.nextInt();
                        if(b==-1){
                            System.out.println("Thankyou for using jukebox");
                            System.exit(0);
                        }
                        if (b == 0) {
                            a = 0;
                            break;
                        }
                        boolean exists = s.playSong(b);
                        if (exists == false) {
                            a = 1;
                            break;
                        }
                        break;

                    case 2:
                        p.printPodcastDetails();
                        System.out.println("Input podcast number to view details or input 0 to go back to main menu");
                        System.out.println("Enter -1 to exit jukebox");
                        int c = scan.nextInt();
                        if(c==-1){
                            System.out.println("Thankyou for using jukebox");
                            System.exit(0);
                        }
                        if (c == 0) {
                            a = 0;
                            break;
                        }
                        exists = p.printPodcastEpisodes(c);
                        if (exists == false) {
                            a = 2;
                            break;
                        }
                        System.out.println("Input Episode No to listen to the episode");
                        System.out.println("Input 0 to go to main menu");
                        System.out.println("Enter -1 to exit jukebox");
                        int d = scan.nextInt();
                        if(d==-1){
                            System.out.println("Thankyou for using jukebox");
                            System.exit(0);
                        }
                        if (d == 0) {
                            a = 0;
                            break;
                        }
                        exists = p.playPodcast(d);
                        if (exists == false) {
                            a = 2;
                            break;
                        }
                        break;

                    case 3:
                        pl.displayPlaylistNames(UserId);
                        System.out.println("Input playlist number to open playlist");
                        System.out.println("Input 0 to return to main menu");
                        System.out.println("Enter -1 to exit jukebox");
                        int e = scan.nextInt();
                        if(e==-1){
                            System.out.println("Thankyou for using jukebox");
                            System.exit(0);
                        }
                        if (e == 0) {
                            a = 0;
                            break;
                        }
                        int currentPlaylistId = pl.showContentsOfPlaylist(e);
                        if(currentPlaylistId==0){
                            System.out.println("No playlist with entered number exists");
                            a=3;
                            break;
                        }
                        ArrayList<Integer> songIdsInPlaylist = pl.getSongIdsInPlaylist(currentPlaylistId);
                        ArrayList<Integer> podcastIdsInPlaylist = pl.getPodcastIdsInPlaylist(currentPlaylistId);
                        System.out.println("Input s if you want to play a song, input p if you want to play a podcast, enter m to go to the main menu");
                        System.out.println("Enter e to exit jukebox");
                        char cho = scan.next().charAt(0);
                        if(cho=='e'||cho=='E'){
                            System.out.println("Thankyou for using jukebox");
                            System.exit(0);
                        }
                        int played=0;
                        if(cho=='M'||cho=='m'){
                            a=0;
                            break;
                        }

                        int checker=0;
                        if(cho=='s'|| cho=='S'){
                            System.out.println("Enter no of song you want to play");
                            System.out.println("Enter -1 to exit jukebox");
                            int sn = scan.nextInt();
                            if(sn==-1){
                                System.out.println("Thankyou for using jukebox");
                                System.exit(0);
                            }
                            for(Integer i : songIdsInPlaylist){
                                if(sn==i){
                                    s.playSongInPlaylist(sn, e);
                                    played=1;
                                    break;
                                }
                            }
                            if (played==0){
                                System.out.println("The song no entered does not exist in the playlist");
                                a=3;
                                break;
                            }
                        }
                        else if (cho=='p' || cho=='P') {
                            System.out.println("Enter no of podcast to view episodes");
                            System.out.println("Enter -1 to exit jukebox");
                            int noP = scan.nextInt();
                            if(noP==-1){
                                System.out.println("Thankyou for using jukebox");
                                System.exit(0);
                            }
                            for(Integer j : podcastIdsInPlaylist){
                                if(j==noP){
                                    p.printPodcastEpisodes(noP);
                                    played=1;
                                }
                            }
                            if(played==0){
                                System.out.println("The podcast no entered does not exist in the playlist");
                                a=3;
                                break;
                            }
                            System.out.println("Enter episode number to play episode");
                            System.out.println("Enter -1 to exit jukebox");
                            int temp = scan.nextInt();
                            if(temp==-1){
                                System.out.println("Thankyou for using jukebox");
                                System.exit(0);
                            }
                            boolean ex = p.playPodcast(temp);
                            if(!ex){
                                a=3;
                                break;
                            }
                        }
                        else {
                            System.out.println("Invalid input, try again");
                            a=3;
                        }

                        break;
                    case 4:
                        String podcastIds = null;
                        String songIds = null;
                        System.out.println("Do you wish to create a new playlist or edit an existing one?(N/E)");
                        System.out.println("To go to main menu enter M");
                        System.out.println("Enter S to exit jukebox");
                        char choi = scan.next().charAt(0);
                        if(choi=='s'||choi=='S'){
                            System.out.println("Thankyou for using jukebox");
                            System.exit(0);
                        }
                        if(choi=='M'||choi=='m'){
                            a=0;
                            break;
                        }
                        if(choi=='N'||choi=='n'){
                            System.out.println("-----------------");
                            s.printSongDetails();
                            System.out.println("-----------------");
                            p.printPodcastDetails();
                            System.out.println("-----------------");
                            System.out.println("Enter Playlist Name");
                            System.out.println("Enter exit to exit jukebox");
                            Scanner scan3=new Scanner(System.in);
                            String pName = scan3.nextLine();
                            if(pName.equalsIgnoreCase("exit")){
                                System.out.println("Thankyou for using jukebox");
                                System.exit(0);
                            }
                            if(pl.checkPlaylistName(pName, UserId)){
                                System.out.println("You already have a playlist with this name");
                                a=4;
                                break;
                            }
                            System.out.println("How many songs you wish to add in the playlist?");
                            System.out.println("If you donot wish to add any songs, enter 0");
                            System.out.println("Enter -1 to exit jukebox");
                            int i = scan.nextInt();
                            if(i==-1){
                                System.out.println("Thankyou for using jukebox");
                                System.exit(0);
                            }
                            if(i==0){}
                            else if(i<0){
                                System.out.println("Negative number of songs cannot be added in the playlist");
                            }
                            else if(i>s.totalSongsinDatabase()){
                                System.out.println("Playlist cannot have more songs than database");
                            }
                            else{
                                int arr[] = new int[i];
                                for(int j=0;j<i;j++){
                                    System.out.println("Enter the Number mentioned alongside song name to add to playlist");
                                    arr[j]=scan.nextInt();
                                }
                                songIds = String.valueOf(arr[0]);
                                for(int j=1;j<i;j++){
                                    songIds=songIds.concat(","+String.valueOf(arr[j]));
                                }
                            }
                            System.out.println("How many podcasts do you wish to add to playlist?");
                            System.out.println("If you wish to add no podcast enter 0");
                            System.out.println("Enter -1 to exit jukebox");
                            int temp = scan.nextInt();
                            if(temp==-1){
                                System.out.println("Thankyou for using jukebox");
                                System.exit(0);
                            }
                            if(temp==0){}
                            else if (temp<0) {
                                System.out.println("Cannot add negative no of podcasts in a playlist");
                            }
                            else if (temp>p.getTotalPodcastsInDatabase()) {
                                System.out.println("Cannot add more podcasts than there are in database");
                            }
                            else {
                                int arr[] = new int[temp];
                                for(int j=0;j<arr.length;j++){
                                    System.out.println("Enter the number mentioned alongside podcast to add to playlist");
                                    arr[j]=scan.nextInt();
                                }
                                podcastIds = String.valueOf(arr[0]);
                                for(int j=1;j<arr.length;j++){
                                    podcastIds=podcastIds.concat(String.valueOf(arr[j]));
                                }
                            }
                            int check = pl.createNewPlaylist(pName,UserId,songIds,podcastIds);
                            if(check==0){
                                System.out.println("Insertion unsuccessful");
                            }
                            else {
                                System.out.println("Insertion successful");
                                a=0;
                                break;
                            }
                        }
                        else if (choi=='E'||choi=='e'){
                            pl.displayPlaylistNames(UserId);
                            System.out.println("Enter the playlist name");
                            System.out.println("Enter exit to exit jukebox");
                            Scanner scan4= new Scanner(System.in);
                            String plName = scan4.nextLine();
                            if(plName.equalsIgnoreCase("exit")){
                                System.out.println("Thankyou for using jukebox");
                                System.exit(0);
                            }
                            boolean existing = pl.checkPlaylistName(plName, UserId);
                            if(!existing){
                                System.out.println("No playlist with this name exists");
                            }
                            else {
                                System.out.println("Choose an action");
                                System.out.println("1. Add Song");
                                System.out.println("2. Delete Song");
                                System.out.println("3. Add Podcast");
                                System.out.println("4. Delete Podcast");
                                System.out.println("5. To go back to main menu");
                                System.out.println("6. Exit jukebox");
                                int choic = scan.nextInt();
                                if (choic==6){
                                    System.out.println("Thankyou for using jukebox");
                                    System.exit(0);
                                }
                                if(choic==5){
                                    a=0;
                                    break;
                                }
                                else if (choic>5 || choic<0) {
                                    System.out.println("Invalid input");
                                    a=4;
                                    break;
                                }
                                switch (choic){
                                    case 1:
                                        System.out.println("-------------------------");
                                        s.printSongDetails();
                                        System.out.println("-------------------------");
                                        pl.showContentsOfPlaylist(plName);
                                        System.out.println("Enter Number of song you want to add");
                                        System.out.println("Enter -1 to exit jukebox");
                                        int sId = scan.nextInt();
                                        if(sId==-1){
                                            System.out.println("Thankyou for using jukebox");
                                            System.exit(0);
                                        }
                                        boolean check = pl.checkIfSongAlreadyInPlaylist(plName, sId, UserId);
                                        if(check){
                                            System.out.println("Song Already Exists in playlist");
                                            a=4;
                                            break;
                                        }
                                        else{
                                           int update = pl.addSongToPlaylist(plName, sId, UserId);
                                           if(update==0){
                                               System.out.println("Insertion Unsuccessful");
                                               a=4;
                                               break;
                                           }
                                           else {
                                               System.out.println("Insertion Successful");
                                               a = 4;
                                               break;
                                           }
                                        }
                                    case 2:
                                        System.out.println("---------------------");
                                        pl.showContentsOfPlaylist(plName);
                                        System.out.println("Enter the number of song you want to remove");
                                        System.out.println("Enter -1 to exit jukebox");
                                        int soId = scan.nextInt();
                                        if(soId==-1){
                                            System.out.println("Thankyou for using jukebox");
                                            System.exit(0);
                                        }
                                        boolean check1 = pl.checkIfSongAlreadyInPlaylist(plName, soId, UserId);
                                        if(!check1){
                                            System.out.println("Song does not exist in playlist");
                                            a=4;
                                            break;
                                        }
                                        else{
                                            int update1 = pl.deleteSongFromPlaylist(plName, soId, UserId);
                                            if(update1==0){
                                                System.out.println("Deletion Unsuccessful");
                                                a=4;
                                                break;
                                            }
                                            else {
                                                System.out.println("Deletion Successful");
                                                a = 4;
                                                break;
                                            }
                                        }
                                    case 3:
                                        System.out.println("--------------------");
                                        p.printPodcastDetails();
                                        System.out.println("--------------------");
                                        pl.showContentsOfPlaylist(plName);
                                        System.out.println("Enter the number of podcast you want to add");
                                        System.out.println("Enter -1 to exit jukebox");
                                        int pId = scan.nextInt();
                                        if(pId==-1){
                                            System.out.println("Thankyou for using jukebox");
                                            System.exit(0);
                                        }
                                        boolean check2 = pl.checkIfPodcastAlreadyInPlaylist(plName, pId, UserId);
                                        if(check2){
                                            System.out.println("Podcast Already Exists in playlist");
                                            a=4;
                                            break;
                                        }
                                        else{
                                            int update3 = pl.addPodcastToPlaylist(
                                                    plName, pId, UserId);
                                            if(update3==0){
                                                System.out.println("Insertion Unsuccessful");
                                                a=4;
                                                break;
                                            }
                                            else {
                                                System.out.println("Insertion Successful");
                                                a = 4;
                                                break;
                                            }
                                        }
                                    case 4:
                                        System.out.println("---------------------");
                                        pl.showContentsOfPlaylist(plName);
                                        System.out.println("Enter the number of podcast you want to remove");
                                        System.out.println("Enter -1 to exit jukebox");
                                        int poId = scan.nextInt();
                                        if(poId==-1){
                                            System.out.println("Thankyou for using jukebox");
                                            System.exit(0);
                                        }
                                        boolean check3 = pl.checkIfPodcastAlreadyInPlaylist(plName, poId, UserId);
                                        if(!check3){
                                            System.out.println("Podcast does not exist in playlist");
                                            a=4;
                                            break;
                                        }
                                        else{
                                            int update1 = pl.deletePodcastFromPlaylist(plName, poId, UserId);
                                            if(update1==0){
                                                System.out.println("Deletion Unsuccessful");
                                                a=4;
                                                break;
                                            }
                                            else {
                                                System.out.println("Deletion Successful");
                                                a = 4;
                                                break;
                                            }
                                        }
                                }
                            }
                        }
                        else
                        {
                            System.out.println("Invalid Input, try again");
                            a=4;
                            break;
                        }

                        break;
                    case 5:
                        s.viewSongsByArtists();
                        System.out.println("Enter song number to play song");
                        System.out.println("Enter 0 to go to main menu");
                        System.out.println("Enter -1 to exit jukebox");
                        int f = scan.nextInt();
                        if(f==-1){
                            System.out.println("Thankyou for using jukebox");
                            System.exit(0);
                        }
                        if (f == 0) {
                            a = 0;
                            break;
                        }
                        exists = s.playSong(f);
                        if (exists == false) {
                            a = 5;
                            break;
                        }

                        break;
                    case 6:
                        s.viewSongsByGenre();
                        System.out.println("Enter song number to play song");
                        System.out.println("Enter 0 to go to main menu");
                        System.out.println("Enter -1 to exit jukebox");
                        int g = scan.nextInt();
                        if(g==-1){
                            System.out.println("Thankyou for using jukebox");
                            System.exit(0);
                        }
                        if (g == 0) {
                            a = 0;
                            break;
                        }
                        exists = s.playSong(g);
                        if (exists == false) {
                            a = 6;
                            break;
                        }
                        break;
                    case 7:
                        s.viewSongsByAlbum();
                        System.out.println("Enter song number to play song");
                        System.out.println("Enter 0 to go to main menu");
                        System.out.println("Enter -1 to exit jukebox");
                        int h = scan.nextInt();
                        if(h==-1){
                            System.out.println("Thankyou for using jukebox");
                            System.exit(0);
                        }
                        if (h == 0) {
                            a = 0;
                            break;
                        }
                        exists = s.playSong(h);
                        if (exists == false) {
                            a = 7;
                            break;
                        }
                        break;
                    case 8:
                        System.out.println("What do you want to search song by?");
                        System.out.println("1. Name");
                        System.out.println("2. Artist");
                        System.out.println("3. Genre");
                        System.out.println("4. Album");
                        System.out.println("5. Back to Main menu");
                        System.out.println("6. Exit jukebox");
                        int i = scan.nextInt();
                        if(i==-1){
                            System.out.println("Thankyou for using jukebox");
                            System.exit(0);
                        }
                        switch (i) {
                            case 1:
                                System.out.println("Input song name");
                                Scanner scan2 = new Scanner(System.in);
                                String songName = scan2.nextLine();
                                int result = s.searchByName(songName);
                                if (result == 0) {
                                    System.out.println("Song does not exist");
                                } else {
                                    System.out.println("Do you wish to play the song?(Y/N)");
                                    char ch = scan.next().charAt(0);
                                    if (ch == 'y' || ch == 'Y') {
                                        s.playSong(result);
                                    } else if (ch == 'N' || ch == 'n') {
                                        a = 0;
                                        break;
                                    } else {
                                        System.out.println("Invalid input, try again");
                                        a = 8;
                                    }
                                }
                                break;
                            case 2:
                                System.out.println("Input Artist name");
                                Scanner sc = new Scanner(System.in);
                                String artistName = sc.nextLine();
                                ArrayList<Integer> songs = s.searchByArtist(artistName);
                                if (songs.isEmpty()) {
                                    System.out.println("Artist not available");
                                } else {
                                    System.out.println("Do you wish to play the song?(Y/N)");
                                    char ch = scan.next().charAt(0);
                                    if (ch == 'y' || ch == 'Y') {
                                        System.out.println("Enter Song Number");
                                        int j = scan.nextInt();
                                        int count = 0;
                                        for (Integer z : songs) {
                                            if (j == z) {
                                                s.playSong(j);
                                                count = 1;
                                            }
                                        }
                                        if (count == 0)
                                            System.out.println("The artist has no song with the number entered");
                                    } else if (ch == 'N' || ch == 'n') {
                                        a = 0;
                                        break;
                                    } else {
                                        System.out.println("Invalid input, try again");
                                        a = 8;
                                    }
                                }
                                break;
                            case 3:
                                System.out.println("Input Genre name");
                                Scanner scan1 = new Scanner(System.in);
                                String Genre = scan1.nextLine();
                                ArrayList<Integer> songsInGenre = s.searchByGenre(Genre);
                                if (songsInGenre.isEmpty()) {
                                    System.out.println("Genre not available");
                                } else {
                                    System.out.println("Do you wish to play the song?(Y/N)");
                                    char ch = scan.next().charAt(0);
                                    if (ch == 'y' || ch == 'Y') {
                                        System.out.println("Enter Song Number");
                                        int j = scan.nextInt();
                                        int count = 0;
                                        for (Integer z : songsInGenre) {
                                            if (j == z) {
                                                s.playSong(j);
                                                count = 1;
                                            }
                                        }
                                        if (count == 0)
                                            System.out.println("The Genre has no song with the number entered");
                                    } else if (ch == 'N' || ch == 'n') {
                                        a = 0;
                                        break;
                                    } else {
                                        System.out.println("Invalid input, try again");
                                        a = 8;
                                    }
                                }
                                break;
                            case 4:
                                System.out.println("Input Album name");
                                Scanner sca = new Scanner(System.in);
                                String albumName = sca.nextLine();
                                ArrayList<Integer> songsInAlbum = s.searchByAlbum(albumName);
                                if (songsInAlbum.isEmpty()) {
                                    System.out.println("Album not available");
                                } else {
                                    System.out.println("Do you wish to play the song?(Y/N)");
                                    char ch = scan.next().charAt(0);
                                    if (ch == 'y' || ch == 'Y') {
                                        System.out.println("Enter Song Number");
                                        int j = scan.nextInt();
                                        int count = 0;
                                        for (Integer z : songsInAlbum) {
                                            if (j == z) {
                                                s.playSong(j);
                                                count = 1;
                                            }
                                        }
                                        if (count == 0)
                                            System.out.println("The album has no song with the number entered");
                                    } else if (ch == 'N' || ch == 'n') {
                                        a = 0;
                                        break;
                                    } else {
                                        System.out.println("Invalid input, try again");
                                        a = 8;
                                    }
                                }

                                break;
                            case 5:
                                a = 0;
                                break;

                        }
                        break;
                    case 9:
                        System.out.println("Enter podcast title to search");
                        System.out.println("To return to main menu enter `return`");
                        System.out.println("Enter `exit` to exit jukebox");
                        Scanner s1 = new Scanner(System.in);
                        String podcastName = s1.nextLine();
                        if(podcastName.equalsIgnoreCase("exit")){
                            System.out.println("Thankyou for using jukebox");
                            System.exit(0);
                        }
                        if(podcastName.equalsIgnoreCase("return")){
                            a=0;
                            break;
                        }
                        int podcastId = p.searchPodcastByName(podcastName);
                        if (podcastId == 0) {
                            System.out.println("No podcast with that title exists");
                            a = 9;
                            break;
                        }
                        System.out.println("Do you wish to view episode list of the podcast?(Y/N)");
                        char ch = scan.next().charAt(0);
                        if (ch == 'y' || ch == 'Y') {
                            p.printPodcastEpisodes(podcastId);
                            System.out.println("Do you wish to play an episode?(Y/N)");
                            char epCh = scan.next().charAt(0);
                            if (epCh == 'y' || epCh == 'Y') {
                                System.out.println("Enter episode number");
                                int l = scan.nextInt();
                                boolean exist = p.playPodcast(l);
                                if (exist == false) {
                                    System.out.println("No episode with that number exists");
                                    a = 9;
                                    break;
                                }
                                else if (epCh == 'N' || epCh == 'n') {
                                    a = 0;
                                    break;
                                }
                                else {
                                    System.out.println("Invalid input, try again");
                                    a = 9;
                                }
                            }
                        } else if (ch == 'N' || ch == 'n') {
                            a = 0;
                            break;
                        } else {
                            System.out.println("Invalid input, try again");
                            a = 9;
                        }
                        break;
                    case 10:
                        System.out.println("Thankyou for using jukebox");
                        System.exit(0);
                        break;

                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
