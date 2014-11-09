package com.group2.whatsup.Security;

import android.util.Base64;

import com.group2.whatsup.Debug.Log;

import org.apache.http.util.EncodingUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoding {

    public static String SHAHash(String password)
    {
        String retVal = null;

        MessageDigest mdSha1 = null;

        try{
            mdSha1 = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException ex){
            Log.Error("Failed to set the MessageDigest object: {0}", ex.getMessage());
        }

        mdSha1.update(EncodingUtils.getAsciiBytes(password));
        byte[] data = mdSha1.digest();

        try{
            retVal = convertToHex(data);
        }
        catch(IOException ex){
            Log.Error("IOException thrown: {0}", ex.getMessage());
        }


        return retVal;
    }




    private static String convertToHex(byte[] data) throws java.io.IOException
    {
        StringBuffer sb = new StringBuffer();
        String hex = null;

        hex = Base64.encodeToString(data, 0, data.length, 0);

        sb.append(hex);

        return sb.toString();
    }
}
