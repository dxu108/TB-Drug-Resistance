import java.io.*;
import java.util.*;
public class getData
{
    public static void main(String [] args) throws Exception
    {
        BufferedReader TBSNP = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\TBSNP.tsv"));
        
        String current = TBSNP.readLine();
        
        ArrayList <String> genes = new ArrayList<String>();
        
        while((current = TBSNP.readLine()) != null)
        {
            String [] tokens = current.split("\t");
            
            String currentGene = tokens[2];
            
            if(!genes.contains(currentGene))
            {
                genes.add(currentGene);
                
                try
                {
                    Process p = Runtime.getRuntime().exec(new String[]{ "cmd", "/c", "java -jar C:\\Users\\jxu\\Desktop\\RSI\\VSL2.jar -s:C:\\Users\\jxu\\Desktop\\TBProt\\" + currentGene + ".txt > C:\\Users\\jxu\\Desktop\\pred\\" + currentGene + "x.txt"});
                    p.waitFor();
                    System.out.println("done");
                }   
                catch (Exception e1)
                {
                    System.out.println(e1.toString());
                    e1.printStackTrace();
                }
            }
        }
        
        TBSNP.close();
    }
    
    private static void translate(String gene)
    {
        
    }
}