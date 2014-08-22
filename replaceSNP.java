import java.io.*;

public class replaceSNP
{
    public static void main(String[]args) throws Exception
    {
        BufferedReader SNP = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\rawr1.txt"));
        
        String current;
        
        double initProb = 0;
        
        double finalProb = 0;
        
        double avgChange = 0;
        
        int num = 0;
        
        int count = 0;
        
        while((current = SNP.readLine()) != null)
        {
            System.out.println(count);
            count++;
            String protein = str_piece(current, ',', 5);
            
            String codon = str_piece(current, ',', 15);
            
            int residue = Integer.parseInt(str_piece(current, ',', 14));
            
            if(isInTable(protein) && !(codon == null) && codon.length() > 3 && codon.charAt(3) == '/' && residue > 0)
            {
            initProb = findProb(protein,residue);
            
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\VSL2\\" + protein + ".flat"));
        
            String aaSequence = "";
        
            String c;
        
            while((c = br.readLine()) != null)
            {
                aaSequence += c;
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
            
            try
            {
                Runtime.getRuntime().exec(new String [] {"cmd", "/K", query});
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
                e.printStackTrace();
            }
            
            try 
			{
				Thread.sleep(680);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
            
            finalProb = findProb(protein + "x", residue);
            
            double percentChange = (finalProb - initProb) / (initProb);
            
            avgChange += percentChange;
            num++;
        }
        }
        SNP.close();
        avgChange = avgChange / num;
        System.out.println(avgChange);
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
    
    private static double findProb(String prot, int res) throws Exception
    {
        BufferedReader prediction = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\VSL2\\" + prot + ".pred"));
        
        String current;
        
        boolean thereYet = false;
        
        boolean done = false;
        
        double probability = 0;
        
        while((current = prediction.readLine()) != null && !done)
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
                    
                    probability = Double.parseDouble(tokens[2]);
                    
                    done = true;
                }
            }
        }
        
        return probability;
    }
}