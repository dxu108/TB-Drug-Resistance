import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class lines
{
    public static void main(String [] args) throws Exception
    {
        String protein = args[0];
        
        BufferedReader regions = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\VSL2\\" + protein + ".pred"));
        
        String current;
        
        int count = 0;
        
        File file = new File("C:\\Users\\jxu\\Desktop\\RSI\\regions " + protein + ".txt");
        
        if(!file.exists())
        {
            file.createNewFile();
        }
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
        
        boolean stuff = false;
        
        while((current = regions.readLine()) != null && stuff == false)
        {
            if(current.charAt(0) != '-')
            {
                if(Character.isDigit(current.charAt(0)))
                {
                    bw.write(current);
                    bw.write(System.getProperty("line.separator"));
                }
            }
            else
            {
                stuff = true;
            }
        }
        
        bw.close();
    }
}