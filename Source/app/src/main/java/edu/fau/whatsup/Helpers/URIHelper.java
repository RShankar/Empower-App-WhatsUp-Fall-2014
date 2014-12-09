package edu.fau.whatsup.Helpers;

public class URIHelper {

    public static String FormatIntoWebsite(String val){
        StringBuilder sb = new StringBuilder();
        if(!val.contains("http://")){
            sb.append("http://");
        }

        sb.append(val);

        return sb.toString();
    }
}
