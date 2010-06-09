package com.dynamobi.ws.util;

import org.springframework.security.providers.encoding.ShaPasswordEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LucidDBEncoder
    extends ShaPasswordEncoder
{

    public LucidDBEncoder(int strength)
    {
        super(strength);
    }

    public String encodePassword(String rawPass, Object salt)
    {    
        // support a case: password is null.   
        if(rawPass.isEmpty()){
        	return "";
        }
            	
    	String saltedPass = mergePasswordAndSalt(rawPass, salt, false);

        MessageDigest messageDigest = getMessageDigest();

        byte[] digest;

        try {
            digest = messageDigest.digest(saltedPass.getBytes("UTF-16LE"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-16LE not supported!");
        }

//         System.out.println("ZZZZ Base64 ["
//         + new String(Base64.encodeBase64(digest)) + "] Hex = ["
//         + new String(Hex.encodeHex(digest)) + "]");
        if (getEncodeHashAsBase64()) {
            return new String(Base64.encodeBase64(digest));
        } else {
            return new String(Hex.encodeHex(digest));
        }
    }

    public boolean isPasswordValid(String encPass, String rawPass, Object salt)
    {
        String pass1 = "" + encPass;
        String pass2 = encodePassword(rawPass, salt);

//         System.out.println("ZZZZ rawPass= [" + rawPass + "] pass1 = [" +
//         pass1
//         + "] pass2 = [" + pass2 + "]");

        return pass1.equals(pass2);
    }

    public static void main(String... strings)
    {

        LucidDBEncoder te = new LucidDBEncoder(256);
        te.setEncodeHashAsBase64(true);
        System.out.println(te.encodePassword("", null));
        System.out.println(te.isPasswordValid(
            "47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=",
            "",
            null));
    }

}
