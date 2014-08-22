import java.io.*;

public class Amalgamate
{
    public static void main(String[]args) throws Exception
    {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\H37Rv.txt"));
        
        String DNA = "";
        
        String current;
        
        while((current = br.readLine()) != null)
        {
            DNA += current;
        }
        
        br.close();
        
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\jxu\\Desktop\\H37Rv1.txt"));
        
        bw.write(DNA);
        
        bw.close();
    }
}