/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keygenerators;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author Yagzan
 */
public class KeyGeneratorAES{
   
    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException
    {
        long start=System.currentTimeMillis();
        int NoOfKeys=100000;
        for(int i=0;i<NoOfKeys;i++)
        KeyGeneratorAES.generateAESKey();
        System.out.print("Time taken for generating 10^5 keys : ");
        System.out.println(System.currentTimeMillis()-start);
    }
 public static String generateAESKey() throws NoSuchAlgorithmException, InterruptedException
 {
     KeyGenerator keyGen = KeyGenerator.getInstance("AES");
keyGen.init(256); // for example
SecretKey secretKey = keyGen.generateKey();
String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//TimeUnit.MICROSECONDS.sleep((100));
//System.out.println(encodedKey);
return encodedKey;


 }
}
