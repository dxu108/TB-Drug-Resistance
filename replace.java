import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class replace
{
    public static void main(String[]args) throws Exception
    {
        String proteinName = JOptionPane.showInputDialog("Protein name?");
        
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\VSL2\\" + proteinName + ".flat"));
        
        String aaSequence = "";
        
        String current;
        
        while((current = br.readLine()) != null)
        {
            aaSequence += current;
        }
        
        br.close();
        
        BufferedReader br2 = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\RSI\\data " + proteinName + ".txt"));
        
        String current2;
        
        BufferedReader changedAA = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\" + proteinName + " m.txt"));
        
        ArrayList<Integer> residues = new ArrayList<Integer>();
        
        while((current2 = br2.readLine()) != null)
        {
            residues.add(new Integer(Integer.parseInt(current2)));
        }
        
        br2.close();
        
        for(Integer x : residues)
        {
            int x1 = x;
            
            char residueC = (char) (changedAA.read());
            
            StringBuilder temp = new StringBuilder(aaSequence);
            
            temp.setCharAt((x1 - 1), residueC);
            
            aaSequence = temp.toString();
        }
        
        PrintWriter bw = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\jxu\\Desktop\\VSL2\\" + proteinName + "x.flat")));
        
        bw.write(aaSequence);
        
        bw.close();
    }
}