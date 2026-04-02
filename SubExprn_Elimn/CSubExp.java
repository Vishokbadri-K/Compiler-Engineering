// Short & Simple version to eliminate common sub-expressions in Three-Address Code
import java.util.*;

public class CSubExp
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);        
        System.out.print("Enter number of Three-Address Statements: ");
		int n = sc.nextInt();
        sc.nextLine();

        String[] code = new String[n];
        String[] lhs = new String[n];
        String[] rhs = new String[n];
        
        System.out.println("Enter statements: [eg: t1=a+b]");

        for(int i=0; i<n; i++)
        {
            code[i]=sc.nextLine();
            String[] parts = code[i].split("=");
            lhs[i] = parts[0];
            rhs[i] = parts[1];
        }

        System.out.println("\nOriginal Three Address Code:");
        for(String s: code)
            System.out.println(s);

        Map<String, String> table = new HashMap<>();
        System.out.println("\nOptimized Three Address Code:");
        for(int i=0; i<n; i++)
        {
            if(table.containsKey(rhs[i]))
            {
                System.out.println(lhs[i] + "=" + table.get(rhs[i]));
            }

            else
            {
                table.put(rhs[i],lhs[i]);
                System.out.println(code[i]);
            }
        }
    }
}