package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class ArtistController {

    static Connection connection;




    private static void setConnection() throws SQLException,ClassNotFoundException {
        String url = "jdbc:mysql://localhost/MusicApp";
        String uname = "root";
        String pwd = "12Ccbu12!";
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url, uname, pwd);
            System.out.println("Connection succesful");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    private static ObservableList<Song> getArtistObjects(ResultSet rs) {
        try {
            ObservableList<Song> artistData = FXCollections.observableArrayList();
            while (rs.next()) {
                Song song = new Song();
                song.setArtistName(rs.getString("artist_name"));
                artistData.add(song);
            }
            return artistData;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
        public static ObservableList<Song> getallArtist() throws SQLException, ClassNotFoundException {

            setConnection();
            String query = "SELECT artist_name from artist;";
            PreparedStatement prepmnt= null;
            try {
                prepmnt = connection.prepareStatement(query);

                ResultSet rs;

                rs = prepmnt.executeQuery(query);
                ObservableList<Song> allArtistData=getArtistObjects(rs);
                return allArtistData;

//
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }


}
