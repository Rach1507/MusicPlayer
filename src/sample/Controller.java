package sample;


import com.jfoenix.controls.JFXButton;
import jaco.mp3.player.MP3Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.File;
import java.sql.*;

public class Controller extends ActionEvent {

    static Connection connection;

    @FXML
    private  TableColumn<Song,String> songColumn;

    @FXML
    private TableColumn<Song,JFXButton> goToArtist;

    @FXML
    private  TableView<Song> songTable;

    @FXML
    private JFXButton playBtn;
    @FXML
    private JFXButton allSongs;

    @FXML
    private JFXButton allArtists;
    @FXML
    private JFXButton allAlbums;

    @FXML
   private void initialize() throws Exception {

        songColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("songName"));
        String query = "SELECT track_name from track";
        ObservableList<Song> songList = getallSongs(query);
        songTable.refresh();
        songTable.setItems(songList);
//    }
}

    public  void getArtistSongs() throws SQLException, ClassNotFoundException {


        Song curr_artist = songTable.getSelectionModel().getSelectedItem();
        String artist = curr_artist.getArtistName();


        String query = String.format("%s=\"%s\";","SELECT track_name from track_artist where artist_name",artist);
        System.out.println(query);
        ObservableList<Song> artistSongs=getallSongs(query);
        songTable.refresh();
        songTable.setItems(artistSongs);

    }

    @FXML
    private void artistInitialise() throws ClassNotFoundException, SQLException {
        songColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        ObservableList<Song> artistList = ArtistController.getallArtist();
        songTable.setItems(artistList);

//
//        Callback<TableColumn<Song,String>, TableCell<Song,String> > cellFactory =(param)-> {
//            final TableCell<Song,String> cell =new TableCell<>(){
//
//                @Override
//                public void updateItem(String item, boolean empty )  {
//                    super.updateItem(item,empty);
//                    if(empty){
//                        setGraphic(null);
//                        setText(null);
//                    }
//                    else{
//                        artistList.
//                    ().add(new JFXButton("Go to Artist"));
//
//                        }
//                    }
//
//
//                }
//            };
//
//


    }

    @FXML
    private void albumInitialise() throws ClassNotFoundException, SQLException {
        songColumn.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        ObservableList<Song> albumList = AlbumController.getallAlbums();
        songTable.setItems(albumList);
    }




    public void play() throws SQLException, ClassNotFoundException {

        Song curr_song = songTable.getSelectionModel().getSelectedItem();
         String song = curr_song.getSongName();

        String query = String.format("%s=\"%s\";","SELECT path_ from track where track_name",song);
        System.out.println(query);
        setConnection();
        PreparedStatement prepmnt= null;
        try {
            prepmnt = connection.prepareStatement(query);
            ResultSet rs;

            rs = prepmnt.executeQuery(query);
            if (rs==null)
                System.out.println("nothing here");
            while (rs.next()) {
                String song_path = rs.getString("path_");
                System.out.println(song_path);
                new MP3Player(new File(song_path)).play();
            }
//
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }



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


    public static ObservableList<Song> getallSongs(String query) throws SQLException, ClassNotFoundException {

        setConnection();
        PreparedStatement prepmnt= null;
        try {
            prepmnt = connection.prepareStatement(query);

                ResultSet rs;

                    rs = prepmnt.executeQuery(query);
                    ObservableList<Song> allSongData=getSongObjects(rs);
                    return allSongData;

//
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }

    return null;
    }

    private static ObservableList<Song> getSongObjects(ResultSet rs) {
        try {
            ObservableList<Song> songData = FXCollections.observableArrayList();
            while (rs.next()) {
                Song song = new Song();
                song.setSongName(rs.getString("track_name"));
                songData.add(song);
            }
            return songData;

    } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    return null;
    }



    }








