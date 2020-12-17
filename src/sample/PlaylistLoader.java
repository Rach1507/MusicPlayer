package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.sql.*;

public class PlaylistLoader extends ActionEvent {

    private static Connection connection;

    @FXML
    private Label lbl1;

    @FXML
    private JFXListView<Song> lst1;
    @FXML
    private TableView<Song> playlistTable;

    @FXML
    private TableColumn<Song, String> playlistColumn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton doneBtn;

    @FXML
    private JFXButton createNewBtn;

    @FXML
    private HBox hbox;

    @FXML
    private JFXTextField textField;

    public String songName;

    public void initData(Song song){
        songName=song.getSongName();


    }


    private Song selectedSong;




    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {

        playlistColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("playlistName"));
        ObservableList<Song> playlistData = FXCollections.observableArrayList();
        playlistData=getallPlaylists();

        playlistTable.refresh();
        playlistTable.setItems(playlistData);
               // lst1.setItems((ObservableList<Song>) playlistTable);

    }

    private static void setConnection() throws SQLException,ClassNotFoundException {
        String url = "jdbc:mysql://localhost/musicapp";
        String uname = "root";
        String pwd = "phani@123";
        //String pwd = "12Ccbu12!";
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url, uname, pwd);
            System.out.println("Connection succesful");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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


        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return null;
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

    @FXML
    private void createNewPlaylist(){

        doneBtn.setText("Create");
        hbox.setVisible(true);






    }
    @FXML
    private void doneMethod() throws SQLException, ClassNotFoundException {
        setConnection();
        if(doneBtn.getText().equals("Create")){
            String newPlaylist=textField.getText();
            int userid=1; //TODO after integrating login part ,this user id must be of the user who has logged in
            long d = System.currentTimeMillis();
            Date datee = new Date(d);
            System.out.println(datee);
            //String query=String.format("SELECT track_name FROM track t WHERE t.album_id in (SELECT album_id FROM album a where album_name=\"%s\");",album);
            //String query=String.format("INSERT INTO playlists(user_id,playlist_name,date_added)"+" values(1,?,?");
            String query="INSERT INTO playlists(user_id,playlist_name,date_added)"+" values(?,?,?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userid);
            statement.setString(2, newPlaylist);
            statement.setString(3, String.valueOf(datee));
            statement.executeUpdate();


        }else{
            Song curr_playlist = playlistTable.getSelectionModel().getSelectedItem();
            String playlistString=curr_playlist.getPlaylistName();
            System.out.println(playlistString);
            //String query1=String.format("SELECT playlist_id FROM playlists WHERE playlist_name=?",playlistString);
            String query1="SELECT playlist_id FROM playlists WHERE playlist_name=?";

            PreparedStatement statement1 = connection.prepareStatement(query1);
            statement1.setString(1, playlistString);
            ResultSet rs1=statement1.executeQuery();
            rs1.next();
            System.out.println(rs1.getString(1)+" playlist_id retreived");
            int playlistId=Integer.parseInt(rs1.getString(1));

            String query2="SELECT track_id FROM track WHERE track_name=?";
            PreparedStatement statement2 = connection.prepareStatement(query2);
            System.out.println(songName);
            statement2.setString(1,songName);
            ResultSet rs2=statement2.executeQuery();
            rs2.next();
            System.out.println(rs2.getString(1)+" track_id retreived");
            int trackId=Integer.parseInt(rs2.getString(1));

            int userId=1;//TODO after integrating login part ,this user id must be of the user who has logged in

        String insertIntoPcontainsTable="INSERT INTO p_contains VALUES(?,?,?)";
            PreparedStatement statement3 = connection.prepareStatement(insertIntoPcontainsTable);
            statement3.setInt(1,trackId);
            statement3.setInt(2,playlistId);
            statement3.setInt(3,userId);
           int affectedRows= statement3.executeUpdate();
           System.out.println("Affected rows are "+affectedRows);





           // int trackId=







        }


    }

    @FXML
    private void quitMethod(){

    }


    @FXML
    public void CreateNewDisappear(){

        hbox.setVisible(false);
        doneBtn.setText("Done");

    }

}
