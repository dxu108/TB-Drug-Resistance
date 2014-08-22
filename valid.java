import java.io.*;
public class valid
{
    public static void main(String[]args) throws Exception
    {
        String AA = "WCFIYVLHMATRGQSNPDEK";
        
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\VSL2\\embBx.flat"));
        
        String current;
        
        String aS = "";
        
        while((current = br.readLine()) != null)
        {
            aS += current;
        }
        
        for (int i = 0; i < aS.length(); i++)
        {
            char c = aS.charAt(i);
            if (AA.indexOf(c) == -1 &&(c != '\r') && (c != '\n') && (c != ' ') && (c != '\t'))
            {
                System.out.println("ERROR: " + c + " is an invalid character at position " + i);
            }
        }
    }
}