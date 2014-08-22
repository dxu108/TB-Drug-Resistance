import java.io.*;
import java.util.*;
public class splitProt
{
    public static void main(String [] args) throws Exception
    {
        BufferedReader proteome = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\TBProteome.txt"));
        
        String now;
        
        ArrayList <String> sequences = new ArrayList <String> ();
        
        String current = "";
        
        while((now = proteome.readLine()) != null)
        {
            if(now.charAt(0) == '>')
            {
                sequences.add(current);
                current = now;
                current += System.getProperty("line.separator");
            }
            else
            {
                current += now;
            }
        }
        
        proteome.close();
        
        sequences.remove(0);
        
        while(!sequences.isEmpty())
        {
            String seq = sequences.remove(0);
            
            boolean reached = false;
            
            String [] tokens = seq.split(System.getProperty("line.separator"));
            
            String id = tokens[0];
            
            String [] partsOfName = id.split("_");
            
            String geneName = partsOfName[0].substring(1);
            
            BufferedWriter protein = new BufferedWriter(new FileWriter("C:\\Users\\jxu\\Desktop\\TBProt\\" + geneName + ".txt"));
            
            protein.write(tokens[1]);
            
            protein.close();
        }
    }
}