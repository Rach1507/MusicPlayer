package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.effect.BlurType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.*;

public class loginController {
    @FXML
    private AnchorPane pane_signup;
    @FXML
    private JFXButton signup_btn;
    //signup_btn.setDisable(true);

    @FXML
    private JFXButton signin_btn;

    @FXML
    private JFXTextField uname_up;

    @FXML
    private JFXTextField email_up;

    @FXML
    private JFXPasswordField pwd_up;

    @FXML
    private JFXPasswordField pwdc_up;

    @FXML
    private AnchorPane pane_signin;

    @FXML
    private JFXPasswordField passin;

    @FXML
    private JFXTextField email_in;

    @FXML
    private JFXButton go_btn;
    Connection conn=null;
    ResultSet rs=null;
//PreparedStatement ps=null;

    public String username;



    @FXML
    private void LoginGo(ActionEvent event) throws Exception{
        // signin_btn.setBackground( "#1c1f1e");

        //conn=DbConnector.ConnectMethod();
        //Media m=new Media();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
       // Parent root1=loader.load();
        Controller controller=loader.getController();
        username=email_in.getText();
        controller.initData(username);

        String username="root";
        String password="phani@123";
        String url="jdbc:mysql://localhost/musicapp3";
        Class.forName("com.mysql.jdbc.Driver").newInstance();

        String em=email_in.getText();
        String pw=passin.getText();
        String sqlstmt=("select * from users where email_id='"+em+"' and password='"+pw+"'");
        //String sqlstmt=String.format("select * from users where email_id=%s and password=%s;",em,pw);
        try{
            Connection con;
            con=DriverManager.getConnection(url,username,password);
            Statement st=con.createStatement() ;
            rs= st.executeQuery(sqlstmt);
            if(rs.next()){

                System.out.println("Login successfull");
                //loginController.closePrgm
              Stage stage=(Stage)passin.getScene().getWindow();
              stage.close();
                //JOptionPane.showMessageDialog(null,"Login successful !");
            }else{
                System.out.println("erong cedentials");
                //JOptionPane.showMessageDialog(null,"Couldn't login. check the credentials");
            }

           // conn.close();
           // st.close();

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }

    }



    @FXML
    void shiftPaneSignUp(ActionEvent event) {

        pane_signin.setVisible(true);
        //signup_btn.setVisible(false);
        pane_signup.setVisible(false);
        signin_btn.setDisable(true);
        signup_btn.setDisable(false);
    }

    @FXML
    void shiftPaneSingIn(ActionEvent event) {

        pane_signup.setVisible(true);
        pane_signin.setVisible(false);
        signup_btn.setDisable(true);
        signin_btn.setDisable(false);
    }

}
