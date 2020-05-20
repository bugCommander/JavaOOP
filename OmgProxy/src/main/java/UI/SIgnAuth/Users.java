package UI.SIgnAuth;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Users {
    Map<String,String > userMap;
    File userFile;
    PasswordCipher cipher;


    public Users() {
        userMap = new HashMap<>();
        userFile =new File("src/Userlist/users");
        cipher = new PasswordCipher("Wearemenmanlymen");

    }

    private boolean checkPass(String password){
        return !password.contains(" ") && password.length() >= 3 && !password.contains("/");


    }
    private  boolean checkLogin(String login){
        return !login.contains(" ") && login.length() >= 3 && !login.contains("/");

    }

    public CHECKER CheckAuth(String Login, String password){
        if(!userMap.containsKey(Login)){
            return CHECKER.INCORRECT_ALL;
        }
        if(!userMap.get(Login).equals(password)){
            return CHECKER.INCORRECT_ALL;

        }

        return CHECKER.CORRECT;
    }

    public CHECKER addUsers(String login, String password) throws IOException, IllegalBlockSizeException, java.security.InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        boolean loginFlag = checkLogin(login);
        boolean passwordFlag = checkPass(password);
        boolean incorectALL = !loginFlag & !passwordFlag;
        if(incorectALL){
            return CHECKER.INCORRECT_ALL;
        }
        if(!loginFlag){
            return CHECKER.INCORRECT_LOGIN;
        }
        if(!passwordFlag){
            return CHECKER.INCORRECT_PASSWORD;
        }



        if(userMap.containsKey(login)){
            return CHECKER.USER_EXIST;
        }


        userMap.put(login,cipher.encrypt(password));
        Writer magicWriter = new FileWriter(userFile,true);

        magicWriter.write(login+"///"+cipher.encrypt(password)+"\n");
        magicWriter.close();
        return CHECKER.CORRECT;



    }


    public   void getDataFromFile() throws FileNotFoundException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        File file = new File("src/Userlist/users");
        Scanner magicReader = new Scanner(file);
        String delimeter = "///";
        String []userdata;
        while (magicReader.hasNext()) {
            String aux = magicReader.next();
            userdata = aux.split(delimeter);
            userdata[1] = cipher.decrypt(userdata[1]);
            userMap.put(userdata[0], userdata[1]);
            System.out.println(userdata[0] + " " + userdata[1]);
        }
        magicReader.close();

    }

}

 class PasswordCipher{
     private  SecretKey secretKey;
     private  Base64.Encoder encoder;
     private  Base64.Decoder decoder;
     public PasswordCipher(String key) {
         secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
         encoder = Base64.getEncoder();
         decoder = Base64.getDecoder();

     }

     public String encrypt(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

             byte[] plainTextByte = plainText.getBytes();

             Cipher cipher = Cipher.getInstance("AES");
             cipher.init(Cipher.ENCRYPT_MODE, secretKey);
             byte[] encryptedByte = cipher.doFinal(plainTextByte);

             return encoder.encodeToString(encryptedByte);
     }


     public String decrypt(String encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
             byte[] encryptedByte = decoder.decode(encrypted);

             Cipher cipher = Cipher.getInstance("AES");
             cipher.init(Cipher.DECRYPT_MODE, secretKey);
             byte[] decryptedByte = cipher.doFinal(encryptedByte);

             return new String(decryptedByte);


     }












 }
