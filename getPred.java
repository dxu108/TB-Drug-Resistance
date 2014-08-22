import java.io.*;

public class getPred
{
    public static void main(String[]args) throws Exception
    {
        BufferedReader prediction = new BufferedReader(new FileReader("C:\\Users\\jxu\\Desktop\\VSL2" + args[0] + ".pred"));
        
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
                if(current.startsWith(args[1]))
                {
                    String [] tokens = current.split("[ ]+");
                    
                    double probability = Double.parseDouble(tokens[2]);
                    
                    System.out.println(probability);
                }
            }
        }
    }
}