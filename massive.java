import java.io.*;

public class massive
{
    public static void main(String[]args) throws Exception
    {
        int counter = 0;
        
        BufferedReader sp = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\swissprot" + args[0] + ".txt"));
        
        String current;
        
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\jxu\\Desktop\\proteome\\" + counter + ".txt"));
        
        while((current = sp.readLine()) != null)
        {
            if(current.equals(""))
            {
            }
            else if(current.charAt(0) == '>')
            {
                bw.close();
                counter++;
                bw = new BufferedWriter(new FileWriter("C:\\Users\\jxu\\Desktop\\proteome\\" + counter + ".txt"));
            }
            else
            {
                bw.write(current);
            }
        }
        
        bw.close();
    }
}