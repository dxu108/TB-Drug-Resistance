import java.io.*;
import java.util.*;

public class writeDisorder
{
    public static void main(String [] args) throws Exception
    {
        BufferedReader TBSNP = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\TBSNP.tsv"));
        
        String current = TBSNP.readLine();
        
        String allInfo = "";
        
        while((current = TBSNP.readLine()) != null)
        {
            String [] tokens = current.split("\t");
            
            if(!tokens[7].equals("SYN"))
            {
                int nucleotide = Integer.parseInt(tokens[1]);
                int aaPosition = (int) (Math.floor((double) nucleotide / 3));
                
                String SNPnum = tokens[0];
                
                String gene = tokens[2];
                
                String dr = "no";
                
                if(tokens.length > 8)
                {
                    dr = "yes";
                }
                
                BufferedReader prediction = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\pred\\" + gene + "x.txt"));
        
                String now;
        
                boolean thereYet = false;
                
                String probability = "";
                
                String order = "";
        
                while((now = prediction.readLine()) != null)
                {
                    if(now.charAt(0) == '-')
                    {
                        thereYet = true;
                    }
                    else if(thereYet == true)
                    {
                        if(now.startsWith(Integer.toString(aaPosition)))
                        {
                            String [] tokens1 = now.split("\t");
                    
                            probability = tokens1[2];
                            
                            order = tokens1[3];
                    
                            System.out.println(probability);
                        }
                    }
                }
                
                allInfo += probability + "," + order + "," + dr + System.getProperty("line.separator");
            }
        }
        
        BufferedWriter allDisProb = new BufferedWriter(new FileWriter("C:\\Users\\jxu\\Desktop\\disProb.txt"));
        
        allDisProb.write(allInfo);
        
        allDisProb.close();
    }
}