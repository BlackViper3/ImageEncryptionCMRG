/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KeyGenerators;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class KeyGenerator2PD
{
    Random rand= new Random();
    public static void main(String args[]) throws UnsupportedEncodingException
    {
       long start=System.currentTimeMillis();
        KeyGenerator2PD obj = new KeyGenerator2PD();
        String d;
        int NoOfKeys=100000;
        for(int i=0;i<NoOfKeys;i++)
        {
            //obj.rand.nextInt();
        obj.generateMRG31k3pKey();
//        System.out.println(obj.MRG31k3p());
        }
        System.out.print("Time taken for generating 10^5 keys : ");
        System.out.println(System.currentTimeMillis()-start);
    }
    long m1= 2147483647; // 2^31 -1 
    long m2 =2147462579; //2^31 − 21069
    double norm = 4.656612873077393e-10;
    long mask12 = 511; // 2^9 -1
    long mask13 = 16777215; //2^24 -1
    long mask21 = 65535; //2^16 - 1

    long x10=0;
    long x11=4194304; // 2^22 
    long x12=129; //2^7 + 1
    long x20=32768; //2^15
    long x21=0; 
    long x22=32769; // 2^15 + 1
public String generateMRG31k3pKey () throws UnsupportedEncodingException
{
 long y1, y2; /* For intermediate results */
/* First component */
y1 = (((x11 & mask12) << 22) + (x11 >> 9))
+ (((x12 & mask13) << 7) + (x12 >> 24));
if (y1 > m1) y1 -= m1;
y1 += x12;
if (y1 > m1) y1 -= m1;
x12 = x11; x11 = x10; x10 = y1;
/* Second component */
y1 = ((x20 & mask21) << 15) + 21069 * (x20 >> 16);
if (y1 > m2) y1 -= m2;
y2 = ((x22 & mask21) << 15) + 21069 * (x22 >> 16);
if (y2 > m2) y2 -= m2;
y2 += x22;
if (y2 > m2) y2 -= m2;
y2 += y1;
if (y2 > m2) y2 -= m2;
x22 = x21; x21 = x20; x20 = y2;
/* Combinaison */
Double returnVal;
if (x10 <= x20) 
    returnVal=((x10 - x20 + m1) * norm) * rand.nextInt();
else 
    returnVal= ((x10 - x20) * norm)* rand.nextInt();
return Base64.encode((returnVal.toString()).getBytes("UTF-8"));

}

}
