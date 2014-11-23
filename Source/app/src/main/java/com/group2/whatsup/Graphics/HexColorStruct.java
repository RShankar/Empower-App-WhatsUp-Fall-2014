package com.group2.whatsup.Graphics;

import java.text.MessageFormat;

public class HexColorStruct{
    public int Red;
    public int Green;
    public int Blue;

    @Override
    public String toString(){
        return MessageFormat.format("#{0}{1}{2}", Integer.toHexString(Red), Integer.toHexString(Green), Integer.toHexString(Blue)).toUpperCase();
    }
}
