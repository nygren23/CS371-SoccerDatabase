package cs301.Soccer;

import android.util.Log;
import android.widget.Toast;

import cs301.Soccer.soccerPlayer.SoccerPlayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Soccer player database -- presently, all dummied up
 *
 * @author *** Blake Nygren ***
 * @version *** put date of completion here ***
 *
 */
public class SoccerDatabase implements SoccerDB {
    private Hashtable<String, SoccerPlayer> hashtable= new Hashtable<>();


    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
    public boolean addPlayer(String firstName, String lastName,
                             int uniformNumber, String teamName) {

        String key = firstName.concat(" ## " + lastName);

        if(hashtable.containsKey(key)) {
            return false;
        }
        else{
            SoccerPlayer newPlayer = new SoccerPlayer(firstName, lastName, uniformNumber, teamName);
            hashtable.put(key, newPlayer);
        }
        return true;
    }

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {
        String key = firstName.concat(" ## " + lastName);
        if(hashtable.containsKey(key)){
            hashtable.remove(key);
            return true;
        }
        return false;
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
    public SoccerPlayer getPlayer(String firstName, String lastName) {
        String key = firstName.concat(" ## " + lastName);

        if(hashtable.containsKey(key)){
            return (SoccerPlayer) hashtable.get(key);
        }

        return null;
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        String key = firstName.concat(" ## " + lastName);

        if(hashtable.containsKey(key)){
            hashtable.get(key).bumpGoals();
            return true;
        }
        return false;
    }

    /**
     * increment a player's assists
     *
     * @see SoccerDB#bumpAssists(String, String)
     */
    @Override
    public boolean bumpAssists(String firstName, String lastName) {
        String key = firstName.concat(" ## " + lastName);

        if(hashtable.containsKey(key)){
            hashtable.get(key).bumpAssists();
            return true;
        }
        return false;
    }


    /**
     * increment a player's shots
     *
     * @see SoccerDB#bumpShots(String, String)
     */
    @Override
    public boolean bumpShots(String firstName, String lastName) {
        String key = firstName.concat(" ## " + lastName);

        if(hashtable.containsKey(key)){
            hashtable.get(key).bumpShots();
            return true;
        }
        return false;

    }

    /**
     * increment a player's saves
     *
     * @see SoccerDB#bumpSaves(String, String)
     */
    @Override
    public boolean bumpSaves(String firstName, String lastName) {
        String key = firstName.concat(" ## " + lastName);

        if(hashtable.containsKey(key)){
            hashtable.get(key).bumpSaves();
            return true;
        }
        return false;
    }

    /**
     * increment a player's fouls
     *
     * @see SoccerDB#bumpFouls(String, String)
     */
    @Override
    public boolean bumpFouls(String firstName, String lastName) {
        String key = firstName.concat(" ## " + lastName);

        if(hashtable.containsKey(key)){
            hashtable.get(key).bumpFouls();
            return true;
        }
        return false;

    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        String key = firstName.concat(" ## " + lastName);

        if(hashtable.containsKey(key)){
            hashtable.get(key).bumpYellowCards();
            return true;
        }
        return false;
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        String key = firstName.concat(" ## " + lastName);

        if(hashtable.containsKey(key)){
            hashtable.get(key).bumpRedCards();
            return true;
        }
        return false;
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
    public int numPlayers(String teamName) {
        int teamCount = 0;
        boolean teamSelected = false;

        if(!(teamName == null)){
            teamSelected = true;
        }

            Set<String> keys = hashtable.keySet();

            for(String key: keys) {

                if (teamSelected) {
                    if (hashtable.get(key).getTeamName().equals(teamName)) {
                        teamCount++;
                    }
                } else {
                    teamCount++;
                }
            }

        return teamCount;
    }

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerNum(int, String)
     */
    // get the nTH player
    @Override
    public SoccerPlayer playerNum(int idx, String teamName) {
        int teamNumCounter = 0;
        boolean teamSelected = false;

        Set<String> keys = hashtable.keySet();

        if (!(teamName == null)) {
            teamSelected = true;
            if (idx >= numPlayers(teamName)) {
                return null;
            }
        }

        for (String key : keys) {
            //Log.i("in for loop", "I made it to the start of the for loop " + teamNumCounter + " times.");
            if (teamSelected) {
                if (hashtable.get(key).getTeamName().equals(teamName)) {
                    if (teamNumCounter == idx) {
                        return hashtable.get(key);
                    } else {
                        teamNumCounter++;
                    }
                }
            } else {
                if (teamNumCounter == idx) {
                    return hashtable.get(key);
                } else {
                    teamNumCounter++;
                }
            }

            if (teamNumCounter >= idx) {
                return null;
            }

        }
        return null;
    }



    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
    // read data from file
    @Override
    public boolean readData(File file) {
        String fName;
        String lName;
        int jNum;
        String tName;
        int g;
        int a;
        int sh;
        int sa;
        int f;
        int y;
        int r;


        Scanner reader = null;
        try {
            reader = new Scanner(file);
        } catch (Exception e) {

        }
        String temp;
        while(reader.hasNext()){
            fName = reader.nextLine();
            lName = reader.nextLine();
            jNum = parseInt(reader.nextLine());
            tName = reader.nextLine();

            addPlayer(fName, lName, jNum, tName);

            SoccerPlayer tempPlayer = getPlayer(fName, lName);
            g = parseInt(reader.nextLine());
            a = parseInt(reader.nextLine());
            sh = parseInt(reader.nextLine());
            sa = parseInt(reader.nextLine());
            f = parseInt(reader.nextLine());
            y = parseInt(reader.nextLine());
            r = parseInt(reader.nextLine());

            for(int i = 0; i < g; i++)
                tempPlayer.bumpGoals();

            for(int i = 0; i < a; i++)
                tempPlayer.bumpAssists();

            for(int i = 0; i < sh; i++)
                tempPlayer.bumpShots();

            for(int i = 0; i < sa; i++)
                tempPlayer.bumpSaves();

            for(int i = 0; i < f; i++)
                tempPlayer.bumpFouls();

            for(int i = 0; i < y; i++)
                tempPlayer.bumpYellowCards();

            for(int i = 0; i < r; i++)
                tempPlayer.bumpRedCards();
        }
            Log.i("Done!", "Read in all data");
            return true;
    }


    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(File)
     */
    // write data to file
    @Override
    public boolean writeData(File file) {
        //String f1 = file.toString();
        PrintWriter writer = null;
        Log.i("Data", "trying to write data...");
        try {
            writer = new PrintWriter(file, "UTF-8");
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for(String key : hashtable.keySet()) {

            SoccerPlayer temp = hashtable.get(key);
            //writer.println(logString(key));
            writer.println(logString(temp.getFirstName()));
            writer.println(logString(temp.getLastName()));
            writer.println(logString("" + temp.getUniform()));
            writer.println(logString(temp.getTeamName()));
            writer.println(logString("" + temp.getGoals()));
            writer.println(logString("" + temp.getAssists()));
            writer.println(logString("" + temp.getShots()));
            writer.println(logString("" + temp.getSaves()));
            writer.println(logString("" + temp.getFouls()));
            writer.println(logString("" + temp.getYellowCards()));
            writer.println(logString("" + temp.getRedCards()));

        }

        writer.flush();
        writer.close();
        return true;
    }

    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see cs301.Soccer.SoccerDB#getTeams()
     */
    // return list of teams
    @Override
    public HashSet<String> getTeams() {
        HashSet<String> allTeams = new HashSet<String>();
        String keyName;



        for(String key : hashtable.keySet()){
            keyName = hashtable.get(key).getTeamName();
            if(!(allTeams.contains(keyName))){
                allTeams.add(keyName);
            }
        }
        return allTeams;
    }

}
