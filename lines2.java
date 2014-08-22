import javax.swing.*;
import java.io.*;

public class lines2
{
    public static void main(String [] args) throws Exception
    {
        int overallDisordered = 0;
        int totalResidues = 0;
        for(int i = 1; i <= 2025; i++)
        {
        
        BufferedReader regions = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\proteome\\" + i + ".pred"));
        
        String current;
        
        int count = 0;
        
        File file = new File("C:\\Users\\jxu\\Desktop\\proteome\\regions " + i + ".txt");
        
        if(!file.exists())
        {
            file.createNewFile();
        }
        
//         BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
//         
//         boolean stuff = false;
//         
//         while((current = regions.readLine()) != null && stuff == false)
//         {
//             if(current.charAt(0) != '-')
//             {
//                 if(Character.isDigit(current.charAt(0)))
//                 {
//                     bw.write(current);
//                     bw.write(System.getProperty("line.separator"));
//                 }
//             }
//             else
//             {
//                 stuff = true;
//             }
//         }
//         
//         bw.close();
        
        BufferedReader bw2 = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\proteome\\" + i + ".pred"));
        
        String cur;
        
        while((cur = bw2.readLine()) != null)
        {
            totalResidues += cur.length();
        }
        
        bw2.close();
        
        BufferedReader regionsN = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\proteome\\regions " + i + ".txt"));
        
        String current2;
        
        while((current2 = regionsN.readLine()) != null)
        {
            int lower = Integer.parseInt(str_piece(current2, '-', 1));
            int upper = Integer.parseInt(str_piece(current2, '-', 2));
            for(int k = lower; k <= upper; k++)
            {
                overallDisordered++;
            }
        }
        
        regionsN.close();
        
        
    }
    
    System.out.println(overallDisordered);
        System.out.println(totalResidues);
        System.out.println((double) overallDisordered / (double) totalResidues);
        
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