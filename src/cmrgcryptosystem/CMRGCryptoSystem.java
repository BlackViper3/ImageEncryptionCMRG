package cmrgcryptosystem;
import KeyGenerators.KeyGenerator2PD;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

public class CMRGCryptoSystem
{
    
    static String keyString;
    String nameOfFile;
    String nameOfDir;
   
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{

              //  System.out.println(args[0]+" "+args[1]);
                String dirName=args[0];
                String fileName=args[1];
                
                CMRGCryptoSystem obj = new CMRGCryptoSystem();
                byte[] cipherData=obj.encryptImage(dirName, fileName);
                obj.constructImage(dirName, fileName, cipherData, "encrypted.png");
                byte[] plain=obj.decryptImage(cipherData);
                obj.constructImage(dirName, fileName, plain,"decrypted.png");
                
    }
    byte[] extractPixelData(String dirName,String fileName) throws IOException
      {
          byte[] imgData;
        CMRGCryptoSystem ob=new CMRGCryptoSystem();        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(1000)) {
                      
                    BufferedImage img=ImageIO.read(new File(dirName,fileName));
                    String format=ob.getImgFormat(dirName, fileName);
                    ImageIO.write(img, format, baos);
                    WritableRaster raster = img.getRaster();
                    DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
                    imgData=buffer.getData();
        }
        return imgData;
    }
    
    
    
    
    byte[] encryptImage(String dirName,String fileName) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException
    {
        CMRGCryptoSystem ob=new CMRGCryptoSystem();        
        
                byte[] imgData=ob.extractPixelData(dirName, fileName);
                        /* ***Encryption*** */
                      Cipher aes=Cipher.getInstance("AES/CBC/PKCS5Padding");
                      //cbc needs iv
                      //ecb doenot need iv
                        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                        IvParameterSpec ivspec = new IvParameterSpec(iv);
          
                       KeyGenerator2PD keyGenObj= new KeyGenerator2PD();
                       String keyStringVal=keyGenObj.generateMRG31k3pKey();
                       keyString=keyStringVal;
                       byte[] key = (keyStringVal.getBytes("UTF-8"));
                      System.out.println("Key:"+keyStringVal);//--256 bits key
                      String fileContent = keyStringVal;
                     BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\xampp\\htdocs\\CryptoSystem\\keyStrore.txt",false));
                      writer.write(fileContent);
                      writer.close();
               
                       MessageDigest sha = MessageDigest.getInstance("SHA-1");
                       key = sha.digest(key);
                       key = Arrays.copyOf(key,16); // use only first 128 bit
                        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
                        aes.init(Cipher.ENCRYPT_MODE,secretKey,ivspec);
                       
                        byte[] cipherText=aes.doFinal(imgData);
               return cipherText;  
    
    }

     byte[] decryptImage(byte[] cipherText) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException
    {
        Cipher aes=Cipher.getInstance("AES/CBC/PKCS5Padding");
        //cbc needs iv
        //ecb doenot need iv
        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        byte[] key = (keyString.getBytes("UTF-8"));
        System.out.println("Key:"+keyString);//--256 bits key
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key,16); // use only first 128 bit
         SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        aes.init(Cipher.DECRYPT_MODE,secretKey,ivspec);
                          byte[] plain = aes.doFinal(cipherText);
        return plain;  
    
    }

    String getImgFormat(String dirName,String fileName) throws IOException
    {
        ImageInputStream iis = ImageIO.createImageInputStream(new File(dirName,fileName));
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
        if (!iter.hasNext()) 
        { 
          throw new RuntimeException("No readers found!");
         }
        ImageReader reader = iter.next();
        iis.close();
        return reader.getFormatName();
    
    }
    
    void constructImage(String dirName,String fileName,byte[] byteData,String newFileName) throws IOException
    {
        CMRGCryptoSystem ob=new CMRGCryptoSystem();
         try (ByteArrayOutputStream baos = new ByteArrayOutputStream(1000)) {
                      String format=ob.getImgFormat(dirName, fileName);
                      BufferedImage img=ImageIO.read(new File(dirName,fileName));
                      ImageIO.write(img, format, baos);
                      WritableRaster raster = img.getRaster();
                      DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
                      byte[] imgData=buffer.getData();
        
                    int pixelSize;
                    ColorModel colorModel = img.getColorModel();
                    pixelSize = colorModel.getPixelSize();
                    int bytesPerPixel = pixelSize / 8;
                        for (int i = 0; i < imgData.length; i += bytesPerPixel) {
                            for (int j = 0; j < bytesPerPixel; j++) {
                                                    imgData[i + j] = (byte) (byteData[i + j]);
                                                }
                           }
        boolean isAlphaPremultiplied =  img.isAlphaPremultiplied();
        BufferedImage imag = new BufferedImage(colorModel, raster,isAlphaPremultiplied, null);
        ImageIO.write(imag, format, new File(dirName,newFileName)); 
                
         }
               
    }
    
    
    void compressImage(String fileName,String dirName) throws FileNotFoundException, IOException
    {
      File compressedImageFile = new File(dirName,fileName);
      BufferedImage img= ImageIO.read(new File(dirName,fileName));
      OutputStream os =new FileOutputStream(compressedImageFile);

      Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("jpg");
      ImageWriter writer = (ImageWriter) writers.next();

      ImageOutputStream ios = ImageIO.createImageOutputStream(os);
      writer.setOutput(ios);

      ImageWriteParam param = writer.getDefaultWriteParam();
      
      param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
      param.setCompressionQuality(0.1f);
      writer.write(null, new IIOImage(img, null, null), param);
      
      os.close();
  
    }
}