package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song {

    private StringProperty songName;
    private StringProperty artistName;
//    private IntegerProperty duration;

    public Song() {
        this.songName = new SimpleStringProperty();
        this.artistName = new SimpleStringProperty();
    }

    public String getSongName() {
        return songName.get();
    }

    public StringProperty getSongName(StringProperty songProp) {
        return songName;
    }

    public void setSongName(String song_name) {
        this.songName.set(song_name);
    }
    public void setArtistName(String name) {
        this.artistName.set(name);
    }

    public String getArtist(int artist_id) {
        return artistName.get();
    }


    public String getArtistName() {
        return artistName.get();

    }
}
