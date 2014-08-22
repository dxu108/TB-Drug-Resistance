import java.io.*;
import java.util.*;
public class ExtractData
{
    public static void main(String [] args) throws Exception
    {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\TBSNP.tsv"));
        
        BufferedReader gen = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\H37Rv - p.txt"));
        
        String now;
        
        String genome = "";
        
        while((now = gen.readLine()) != null)
        {
            genome += now;
        }
        
        gen.close();
        
        String current;
        
        current = br.readLine();
        
        ArrayList <String> genes = new ArrayList<String>();
        
        while((current = br.readLine()) != null)

        {
            
            String [] tokens = current.split("\t");
            
            String gene = tokens[2];
        
            if(!genes.contains(gene))
            {
                
                genes.add(gene);
                System.out.println(gene);
            
            
        BufferedReader br2 = new BufferedReader(new FileReader("C:\\TBG\\" + gene + ".txt"));
        
        String current2;
        
        String total = "";
        
        current2 = br2.readLine();
        
        while((current2 = br2.readLine()) != null)
        {
            total += current2;
        }
        
        br2.close();

        int index = total.indexOf("TD width=160 align=center");
        
        int numIndex = index + 15;
        
        boolean exit = false;
        
        while(!exit)
        {
            if(Character.isDigit(total.charAt(numIndex)))
            {
                exit = true;
            }
            else
            {
                numIndex++;
            }
        }
        
        String start = "";
        
        boolean done = false;
        
        while(!done)
        {
            if(Character.isDigit(total.charAt(numIndex)))
            {
                start += total.substring(numIndex, numIndex + 1);
                numIndex++;
            }
            else
            {
                done = true;
            }
        }
        
        boolean exit1 = false;
        
        while(!exit1)
        {
            if(Character.isDigit(total.charAt(numIndex)))
            {
                exit1 = true;
            }
            else
            {
                numIndex++;
            }
        }
        
        String end = "";
        
        boolean done1 = false;
        
        while(!done1)
        {
            if(Character.isDigit(total.charAt(numIndex)))
            {
                end += total.substring(numIndex, numIndex + 1);
                numIndex++;
            }
            else
            {
                done1 = true;
            }
        }
        
        System.out.println(start);
        System.out.println(end);
        
        
        int startInt = Integer.parseInt(start);
        int endInt = Integer.parseInt(end);
        
        String nucleotides = "";
        
        nucleotides += ">" + gene;
        
        nucleotides += System.getProperty("line.separator");
        
        nucleotides += genome.substring(startInt - 1, endInt);
        
        BufferedWriter f = new BufferedWriter(new FileWriter("C:\\Users\\jxu\\Desktop\\genome\\" + gene + ".txt"));
        
        f.write(nucleotides);
        
        f.close();
        
    }
    }
    
    br.close();
    }
}