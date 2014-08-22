import java.io.*;

public class replaceSNP2
{
    public static void main(String[]args) throws Exception
    {
        BufferedReader SNP = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\rawr1.txt"));
        
        String current;
        
        int disorderedBefore = 0;
        
        int total = 0;
        
        int disorderedAfter = 0;
        
        int lineNumber = 1;
        
        while((current = SNP.readLine()) != null)
        {
            System.out.println(lineNumber);
            lineNumber++;
            
            System.out.println(total);
            System.out.println(disorderedBefore);
            System.out.println(disorderedAfter);
            System.out.println();
            
            String protein = str_piece(current, ',', 5);
            
            String codon = str_piece(current, ',', 15);
            
            int residue = Integer.parseInt(str_piece(current, ',', 14));
            if(isInTable(protein) && !(codon == null) && codon.length() > 3 && codon.charAt(3) == '/' && residue > 0)
            {
            
            if(disordered(protein, residue))
            {
                disorderedBefore++;
            }
            
            total++;
            
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\VSL2\\" + protein + ".flat"));
        
            String aaSequence = "";
        
            String ch;
        
            while((ch = br.readLine()) != null)
            {
                aaSequence += ch;
            }
            
            br.close();
            
            char changeTo = translate(codon.substring(4));

            StringBuilder temp = new StringBuilder(aaSequence);
            
            temp.setCharAt((residue - 1), changeTo);
            
            aaSequence = temp.toString();
        
            PrintWriter bw = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\jxu\\Desktop\\VSL2\\" + protein + "x.flat")));
        
            bw.write(aaSequence);
        
            bw.close();
            
            String query = "java -jar VSL2.jar -s:C:\\Users\\jxu\\Desktop\\VSL2\\" + protein + "x.flat > " + "C:\\Users\\jxu\\Desktop\\VSL2\\" + protein + "x.pred";

            
			try //Calls the command prompt to run the VSL2 program on the protein
			{
				Runtime rt = Runtime.getRuntime();
				rt.exec(new String[]{ "cmd","/K", query });
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
				e1.printStackTrace();
			}
			
			//Pause for 1.0 seconds to allow VSL2 program to finish processing the protein query
			try 
			{
				Thread.sleep(700);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
            
            if(disordered(protein + "x", residue))
            {
                disorderedAfter++;
            }
        }
    }
    
        SNP.close();
        
        System.out.println("Disordered before " + disorderedBefore);
        System.out.println("Disordered after " + disorderedAfter);
        System.out.println("Total " + total);
        
        System.out.println("FINIS");
    }
    
    private static char translate(String threeLetter)
    {
        if(threeLetter.equals("Gly"))
        {
            return 'G';
        }
        else if(threeLetter.equals("Ala"))
        {
            return 'A';
        }
        else if(threeLetter.equals("Val"))
        {
            return 'V';
        }
        else if(threeLetter.equals("Leu"))
        {
            return 'L';
        }
        else if(threeLetter.equals("Ile"))
        {
            return 'I';
        }
        else if(threeLetter.equals("Met"))
        {
            return 'M';
        }
        else if(threeLetter.equals("Phe"))
        {
            return 'F';
        }
        else if(threeLetter.equals("Trp"))
        {
            return 'W';
        }
        else if(threeLetter.equals("Pro"))
        {
            return 'P';
        }
        else if(threeLetter.equals("Ser"))
        {
            return 'S';
        }
        else if(threeLetter.equals("Cys"))
        {
            return 'C';
        }
        else if(threeLetter.equals("Tyr"))
        {
            return 'Y';
        }
        else if(threeLetter.equals("Asn"))
        {
            return 'N';
        }
        else if(threeLetter.equals("Gln"))
        {
            return 'Q';
        }
        else if(threeLetter.equals("Asp"))
        {
            return 'D';
        }
        else if(threeLetter.equals("Glu"))
        {
            return 'E';
        }
        else if(threeLetter.equals("Lys"))
        {
            return 'K';
        }
        else if(threeLetter.equals("Arg"))
        {
            return 'R';
        }
        else if(threeLetter.equals("His"))
        {
            return 'H';
        }
        return '0';
    }
    
    private static boolean isInTable(String pro)
    {
        if(pro.equals("gyrA") || pro.equals("gyrB") || pro.equals("inhA") || pro.equals("ndh") || pro.equals("accD6")
        || pro.equals("furA") || pro.equals("nhoA") || pro.equals("kasA") || pro.equals("efpA") || pro.equals("ahpC") ||
        pro.equals("embB") || pro.equals("thyA") || pro.equals("rpoB") || pro.equals("gidB") || pro.equals("rpsL") || 
        pro.equals("hyp1") || pro.equals("tlyA") || pro.equals("pncA") || pro.equals("ethA") || pro.equals("iniB"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static String str_piece(String str, char separator, int index)
    {
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
    
    private static boolean disordered(String prot, int res) throws Exception
    {
        BufferedReader prediction = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\VSL2\\" + prot + ".pred"));
        
        String current;
        
        boolean thereYet = false;
        
        while((current = prediction.readLine()) != null)
        {
            if(current.charAt(0) == '-')
            {
                thereYet = true;
            }
            else if(thereYet == true)
            {
                if(current.startsWith(Integer.toString(res)))
                {
                    String [] tokens = current.split("\t");
                    
                    if(tokens[3].equals("D"))
                    {
                        prediction.close();
                        return true;
                    }
                    else
                    {
                        prediction.close();
                        return false;
                    }
                }
            }
        }
        return false;
    }
}