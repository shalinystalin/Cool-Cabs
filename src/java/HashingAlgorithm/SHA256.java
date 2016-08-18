package HashingAlgorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vivek
 */
public class SHA256 {
     
    public String getHashOf(String s) {

        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(s.getBytes());
            
            byte byteData[] = md.digest();
            
            //convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            hash = sb.toString();

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SHA256.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hash;
    }
}


