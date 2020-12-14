package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Song;

import java.sql.*;

public class PlaylistController {
    String playlistName;
    Date dateAdded;

    private PlaylistController(){



    }
    static Connection connection;




    private static void setConnection() throws SQLException,ClassNotFoundException {
        String url = "jdbc:mysql://localhost/musicapp";
        String uname = "root";
        String pwd = "phani@123";
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url, uname, pwd);
            System.out.println("Connection succesful");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    private static ObservableList<Song> getPlaylistObjects(ResultSet rs) {
        try {
            ObservableList<Song> playlistData = FXCollections.observableArrayList();
            while (rs.next()) {
                Song song = new Song();
                song.setPlaylistName(rs.getString("playlist_name"));
                playlistData.add(song);
            }
            return playlistData;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public static ObservableList<Song> getallPlaylists() throws SQLException, ClassNotFoundException {

        setConnection();
        String query = "SELECT playlist_name from playlists;";
        PreparedStatement prepmnt= null;
        try {
            prepmnt = connection.prepareStatement(query);

            ResultSet rs;

            rs = prepmnt.executeQuery(query);
            ObservableList<Song> allPlaylistData=getPlaylistObjects(rs);
            return allPlaylistData;

//
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }


}
