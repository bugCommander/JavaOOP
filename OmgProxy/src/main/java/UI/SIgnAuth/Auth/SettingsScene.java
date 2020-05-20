package UI.SIgnAuth.Auth;

import UI.SIgnAuth.Users;
import UI.SIgnAuth.WHO;
import UI.SceneSheet;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class SettingsScene extends SceneSheet {
    public Button getBack() {
        return back;
    }

    Button back;

    public SettingsScene(double h, double w, Image image) {
        super(h, w, image);
        back = new Button("back");

        elements.getChildren().addAll(back);
    }
}

class Settings {
    Users users;
    public Settings(Users userlist){
        users = userlist;

    }

    void changePassword(String login,String newPassword, WHO who) throws BadPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {
        deleteUser(login,who);
        users.addUsers(getMap(who),login,newPassword,getFile(who));
    }

    HashMap<String,String> getMap(WHO who){
        return  who ==WHO.USER?users.getUserMap():users.getAdminMap();
    }
    String getPath(WHO who) throws IOException {
        return who == WHO.USER?users.getUserFile().getCanonicalPath():   users.getAdminFile().getCanonicalPath();
    }
    File getFile(WHO who){
        return who == WHO.USER?users.getUserFile():   users.getAdminFile();

    }

     void deleteUser(String login,WHO who) throws IOException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
         HashMap<String,String> map = getMap(who);
         map.remove(login);
         String pathName = getPath(who);
        users.putDataInFile(map,pathName);


    }
    void showAllUsers(WHO who){



    }
}
