/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Ryan Chin
 */
public class Hasher {
    private byte[] salt;
    private byte[] hash;
    
    public Hasher(char[] password){
        try{
            SecureRandom random = new SecureRandom();
            salt = new byte[16];
            random.nextBytes(salt);
            int iterations = 100000;
            int keyLength = 256;
            String algorithm = "PBKDF2WithHmacSHA256";
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
            hash = factory.generateSecret(spec).getEncoded();
        }catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(LoginSQLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Hasher(byte[] salt, char[] password){
        this.salt = salt;
        try{
            int iterations = 100000;
            int keyLength = 256;
            String algorithm = "PBKDF2WithHmacSHA256";
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
            this.hash = factory.generateSecret(spec).getEncoded();
        }catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(LoginSQLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public byte[] getSalt(){
        return salt;
    }
    
    public byte[] getHash(){
        return hash;
    }
}
