package monopoly;

import java.io.IOException;

public class IOHandler
{
    public static char readChar()
    {
        char c;
        int a = -1;
        try
        {
            a = System.in.read();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if (a == -1)
            c = '\u0000';
        else
            c = (char) a;
        return c;
    }
    
    public static long readLong()
    {
        long i = 0;
        boolean negative = false;
        
        char c = readChar();
        if (c == '\u0000')
            return i;
        if (Character.isWhitespace(c))
        {
            readChar();
            return i;
        }
        if (c == '-')
            negative = true;
        else
            i = (int) c - (int) '0';
        
        if (i < 0 || i > 9)
            throw new NumberFormatException("Expecting integer value!");
        
        while (true)
        {
            c = readChar();
            if (c == '\u0000')
                break;
            if (Character.isWhitespace(c))
            {
                readChar();
                break;
            }
            int temp = (int) c - (int) '0';
            if (temp < 0 || temp > 9)
                throw new NumberFormatException("Expecting integer value! ");
            i *= 10;
            i += temp;
        }        
        if (negative)
            i *= -1;
        return i;
    }
    
    public static int readInt()
    {
        long l = readLong();
        if (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE)
            throw new IllegalArgumentException("Outside Integer bounds."
                                                    + "Try reading as Long!");
        int i = (int) l;
        return i;
    }
    
    public static double readDouble()
    {
        double d;
        String s = readString();
        d = Double.parseDouble(s);
        return d;
    }
    
    public static String readString()
    {
        String string = "";
        
        while (true)
        {
            char c = readChar();
            if (c == '\u0000')
                break;
            if (c == '\r')
            {
                readChar();
                break;
            }
            string += c;
        }
        
        return string;
    }
}