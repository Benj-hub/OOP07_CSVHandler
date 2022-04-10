/*
 * Project: "Top40"
 * Author: Benjamin Lamprecht
 * Created: 09.04.2022
 * Last Change: 10.04.2022
 */

import java.util.*;

public class Tracks {
    private final HashMap<Integer, Track> tracks = new HashMap<>();

    //fetches via IOHandler the inputs of a Track.csv and parses its values for track constructor
    public Tracks(String input) {
        short lw = 0;
        short dw = 0;
        float rating = 0;
        List<List> list = IOHandler.readCSV(input);
        int i = 0;
        for (List ls : list.subList(1, list.size())) {
            i++;
            try {

                if (!(ls.get(1).equals(""))) {
                    lw = Short.parseShort((String) ls.get(1));
                }
                if (!(ls.get(2).equals(""))) {
                    dw = Short.parseShort((String) ls.get(2));
                }
                if (!(ls.get(5).equals(""))) {
                    rating = Float.parseFloat(ls.get(5).toString().replace(',', '.'));
                }
                tracks.put(i, new Track(lw, dw, (String) ls.get(3), (String) ls.get(4), rating));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("CSV file invalid Value");
            }
        }
    }

    //rearranging list with new track
    public void putNewTrack(int ranking, Track track) {
        Track nTrack = tracks.get(ranking);
        tracks.put(ranking, track);
        if (ranking <= 75) {
            putNewTrack(ranking + 1, nTrack);
        } else {
            popOldTrack(nTrack);
        }
    }

    //garbage collection for old track
    public void popOldTrack(Track track) {
        for (Artist a : track.getArtistList())
            a.popRatings(track.getRating());
    }

    public void shiftWeek() {
        for (int i = 1; i <= tracks.size(); i++) {
            tracks.get(i).setLw((short)i);
            tracks.get(i).setWw();
        }
    }


    public void printList() {
        System.out.printf("%3s | %3s | %3s | %32s | %46s | %3s\n", "DW", "LW", "WW", "Titel", "Interpret", "Bewertung");
        for (int i = 1; i <= tracks.size(); i++) {
            if (tracks.containsKey(i)) {
                tracks.get(i).printTrack(i);
            }
        }
    }
}