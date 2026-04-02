// Expanded COMPILER version to eliminate common sub-expressions in Three-Address Code
import java.util.*;

class TACStatement
{
	String lhs;
	String rhs;

	TACStatement(String lhs, String rhs)
	{
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public String toString()
	{
		return (lhs + " = " + rhs);
	}
}

public class subexp_elim
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		List<TACStatement> code = new ArrayList<>();

		System.out.print("Enter number of Three-Address Statements: ");
		int n = sc.nextInt();
		sc.nextLine();

		System.out.println("Enter statements: [eg: t1=a+b]");
		for(int i=0; i<n; i++)
		{
			String line = sc.nextLine().trim();

			// Split: t1=a+b
			String[] parts = line.split("=",2);

			if(parts.length < 2)
			{
				System.out.println("Invalid input: " + line);
				System.exit(1);
			}

			String lhs = parts[0].trim();
			String rhs = parts[1].trim();

			code.add(new TACStatement(lhs,rhs));
		}

		System.out.println("\nOriginal Three Address Code:\n");
		printCode(code);

		List<TACStatement> optimized = eliminateCSE(code);
		System.out.println("\nOptimized Three Address Code:");
		printCode(optimized);

		sc.close();
	}

	public static List<TACStatement>eliminateCSE(List<TACStatement> code)
	{
		Map<String, String> exprTable = new HashMap<>();
		List<TACStatement> optimized = new ArrayList<>();

		for(TACStatement stmt: code)
		{
			String rhs = stmt.rhs;
			if(exprTable.containsKey(rhs))
			{
				String existingVar = exprTable.get(rhs);

				// Replace RHS with temp variable
				optimized.add(new TACStatement(stmt.lhs, existingVar));
			}

			else
			{
				exprTable.put(rhs, stmt.lhs);
				optimized.add(stmt);
			}
		}		
		return optimized;
	}

	public static void printCode(List<TACStatement> code)
	{
		for(TACStatement stmt: code)
			System.out.println(stmt);
	}
}
