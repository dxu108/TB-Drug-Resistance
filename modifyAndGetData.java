import java.io.*;
public class modifyAndGetData
{
    public static void main(String [] args) throws Exception
    {
        BufferedReader TBSNP = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\TBSNP.tsv"));
        
        String current = TBSNP.readLine();
        
        double initialDisorder = 0;
        
        double finalDisorder = 0;
        
        int numSNP = 1;
        
        String dataWrite;
        
        String allInfo = "";
        
        while((current = TBSNP.readLine()) != null)
        {
            System.out.println(numSNP);
            
            numSNP++;
            
            try
            {
            
            String [] tokens = current.split("\t");
            
            String dr = "no";
                
                if(tokens.length > 8)
                {
                    dr = "yes";
                }
            
            String currentGene = tokens[2];
            
            char initNucleotide = tokens[4].charAt(0);
            
            char finalNucleotide = tokens[5].charAt(0);
            
            int locationNucleotide = Integer.parseInt(tokens[1]);
            
            int locationAminoAcid = (int) Math.floor((double) locationNucleotide / 3);
            
            BufferedReader prediction = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\pred\\" + currentGene + "x.txt"));
        
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
                        if(now.startsWith(Integer.toString(locationAminoAcid)))
                        {
                            String [] tokens1 = now.split("\t");
                    
                            probability = tokens1[2];
                            
                            order = tokens1[3];
                        }
                    }
                }
                
                prediction.close();
            
            BufferedReader inGene = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\genome\\" + currentGene + ".txt"));
            
            BufferedReader inProt = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\TBProt\\" + currentGene + ".txt"));
            
            String nSeq = inGene.readLine();
            
            nSeq = inGene.readLine();
            
            String aaSequence = inProt.readLine();
            
            int adjustedLocation = locationNucleotide - 1;
            
            String firstN = "";
            
            String secondN = "";
            
            String thirdN = "";
            
            String codon = "";
            
            String newAminoAcid = "";
            
            if(locationNucleotide % 3 == 0)
            {
                firstN = nSeq.substring(adjustedLocation - 2, adjustedLocation - 1);
                
                secondN = nSeq.substring(adjustedLocation - 1, adjustedLocation);
                
                thirdN = Character.toString(finalNucleotide);
                
                codon = firstN + secondN + thirdN;
                
                newAminoAcid = codonToAminoAcid(codon);
            }
            else if(locationNucleotide % 3 == 1)
            {
                firstN = Character.toString(finalNucleotide);
                
                secondN = nSeq.substring(adjustedLocation + 1, adjustedLocation + 2);
                
                thirdN = nSeq.substring(adjustedLocation + 2, adjustedLocation + 3);
                
                codon = firstN + secondN + thirdN;
                
                newAminoAcid = codonToAminoAcid(codon);
            }
            else if(locationNucleotide % 3 == 2)
            {
                firstN = nSeq.substring(adjustedLocation - 1, adjustedLocation);
                
                secondN = Character.toString(finalNucleotide);
                
                thirdN = nSeq.substring(adjustedLocation + 1, adjustedLocation + 2);
                
                codon = firstN + secondN + thirdN;
                
                newAminoAcid = codonToAminoAcid(codon);
            }
            
            inGene.close();
            
            inProt.close();
            
            BufferedWriter modifiedProtein = new BufferedWriter(new FileWriter("C:\\Users\\jxu\\Desktop\\TBProt\\" + currentGene + "M.txt"));
            
            StringBuilder protSequenceProcess = new StringBuilder(aaSequence);
            
            protSequenceProcess.setCharAt(locationAminoAcid, newAminoAcid.charAt(0));
            
            aaSequence = protSequenceProcess.toString();
            
            modifiedProtein.write(aaSequence);
            
            modifiedProtein.close();
            
            try
                {
                    Process p = Runtime.getRuntime().exec(new String[]{ "cmd", "/c", "java -jar C:\\Users\\jxu\\Desktop\\RSI\\VSL2.jar -s:C:\\Users\\jxu\\Desktop\\TBProt\\" + currentGene + "M.txt > C:\\Users\\jxu\\Desktop\\pred\\" + currentGene + "M.txt"});
                    p.waitFor();
                }   
                catch (Exception e1)
                {
                    System.out.println(e1.toString());
                    e1.printStackTrace();
                }
                
            BufferedReader predictionA = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\pred\\" + currentGene + "M.txt"));
        
                String nowA;
        
                boolean thereYetA = false;
                
                String probabilityA = "";
                
                String orderA = "";
        
                while((nowA = predictionA.readLine()) != null)
                {
                    if(nowA.charAt(0) == '-')
                    {
                        thereYetA = true;
                    }
                    else if(thereYetA == true)
                    {
                        if(nowA.startsWith(Integer.toString(locationAminoAcid)))
                        {
                            String [] tokensA = nowA.split("\t");
                    
                            probabilityA = tokensA[2];
                            
                            orderA = tokensA[3];
                        }
                    }
                }
                
                predictionA.close();
                
                double probBefore = Double.parseDouble(probability);
                
                double probAfter = Double.parseDouble(probabilityA);
                
                double percentChange = (probAfter - probBefore) / probBefore;
                
                String pChange = String.valueOf(percentChange);
                
                allInfo += probability + "," + order + "," + probabilityA + "," + orderA + "," + pChange + "," + dr + System.getProperty("line.separator");
        }
        catch(Exception e)
        {
            System.out.println("error here");
        }
    }
        
        TBSNP.close();
        
        BufferedWriter drugResistanceInfo = new BufferedWriter(new FileWriter("C:\\Users\\jxu\\Desktop\\drugresistance.csv"));
        
        drugResistanceInfo.write(allInfo);
        
        drugResistanceInfo.close();
    }

    /**
     * Return the single-letter abbreviation for a codon, e.g., "F" for "TTT"
     * (phenylalanine). This method returns "X" if the parameter is a non-valid
     * codon.
     * 
     * @param codon
     *            is the codon whose abbreviation is returned
     * @return the one-letter abbreviation for the codon, or "X" if the codon
     *         isn't valid
     */

    private static String codonToAminoAcid(String codon)
    {
        
            String[] CODONS = { 
            "TTT", "TTC", "TTA", "TTG", "TCT",
            "TCC", "TCA", "TCG", "TAT", "TAC", "TGT", "TGC", "TGG", "CTT",
            "CTC", "CTA", "CTG", "CCT", "CCC", "CCA", "CCG", "CAT", "CAC",
            "CAA", "CAG", "CGT", "CGC", "CGA", "CGG", "ATT", "ATC", "ATA",
            "ATG", "ACT", "ACC", "ACA", "ACG", "AAT", "AAC", "AAA", "AAG",
            "AGT", "AGC", "AGA", "AGG", "GTT", "GTC", "GTA", "GTG", "GCT",
            "GCC", "GCA", "GCG", "GAT", "GAC", "GAA", "GAG", "GGT", "GGC",
            "GGA", "GGG", };

            String[] AMINOS_PER_CODON = { 
            "F", "F", "L", "L", "S", "S",
            "S", "S", "Y", "Y", "C", "C", "W", "L", "L", "L", "L", "P", "P",
            "P", "P", "H", "H", "Q", "Q", "R", "R", "R", "R", "I", "I", "I",
            "M", "T", "T", "T", "T", "N", "N", "K", "K", "S", "S", "R", "R",
            "V", "V", "V", "V", "A", "A", "A", "A", "D", "D", "E", "E", "G",
            "G", "G", "G", };
        
        for (int k = 0; k < CODONS.length; k++) {
            if (CODONS[k].equals(codon)) {
                return AMINOS_PER_CODON[k];
            }
        }

        // never reach here with valid codon
        return "X";
    }
}