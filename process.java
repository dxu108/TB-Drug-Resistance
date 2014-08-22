import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class process
{
    public static void main(String [] args) throws IOException
    {
        String protein = args[0];
        ArrayList <Integer> residues = new ArrayList<Integer>();
        BufferedReader residueN = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\RSI\\data " + protein + ".txt"));
        String current1;
        ArrayList <Integer> regions = new ArrayList<Integer>();
        BufferedReader regionsN = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\RSI\\regions " + protein + ".txt"));
        String current2;
        
        int disorderedCount = 0;
        int orderedCount = 0;
        
        int disorderedTotal = 0;
        
        while((current1 = residueN.readLine()) != null)
        {
            residues.add(new Integer(Integer.parseInt(current1)));
        }
        
        while((current2 = regionsN.readLine()) != null)
        {
            int lower = Integer.parseInt(str_piece(current2, '-', 1));
            int upper = Integer.parseInt(str_piece(current2, '-', 2));
            for(int i = lower; i <= upper; i++)
            {
                regions.add(new Integer(i));
                disorderedTotal++;
            }
        }
        
        for(int r : residues)
        {
            boolean done = false;
            for(int s : regions)
            {
                if(r == s)
                {
                    disorderedCount++;
                    done = true;
                }
            }
            if(!done)
            {
                orderedCount++;
            }
        }
        
        double prob = (double) disorderedCount / (double) (orderedCount + disorderedCount);
        
        System.out.println("The probability is: " + prob);
        
        System.out.println("The disordered count is " + disorderedCount);
        
        int sum = disorderedCount + orderedCount;
        
        System.out.println("The total SNP count is " + sum);
        
        System.out.println("The total disordered count is " + disorderedTotal);
    }
    
    
    private static String str_piece(String str, char separator, int index) {
        String str_result = "";
        int count = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == separator) {
                count++;
                if(count == index) {
                    break;
                }
            }
            else {
                if(count == index-1) {
                    str_result += str.charAt(i);
                }
            }
        }
        return str_result;
    }
}