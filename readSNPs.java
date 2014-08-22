import java.io.*;
import java.util.*;

public class readSNPs
{
    public static void main(String [] args) throws Exception
    {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\TBSNP.tsv"));
        
        String current;
        
        int lineNumber = 1;
        
        current = br.readLine();
        
        ArrayList <String> genes = new ArrayList<String>();
        
        while((current = br.readLine()) != null)
        {
            // System.out.println(lineNumber);
            
            String [] tokens = current.split("\t");
            
            String gene = tokens[2];
            
            System.out.println(gene);
            
            if(!genes.contains(gene))
            {
                genes.add(gene);
                
                try
                {
                    Process p = Runtime.getRuntime().exec(new String[]{ "cmd", "/c", "\"C:\\Program Files (x86)\\GnuWin32\\bin\\wget\" http://tuberculist.epfl.ch/quicksearch.php?gene+name=" + gene + " -O C:\\TBG\\" + gene + ".txt"});
                    p.waitFor();
                    System.out.println("OK");
                }   
                catch (Exception e1)
                {
                    System.out.println(e1.toString());
                    e1.printStackTrace();
                }
            }
            
            lineNumber++;
        }
    }
}