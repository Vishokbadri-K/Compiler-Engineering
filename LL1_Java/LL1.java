import java.util.*;

class LL1
{
    static HashMap<Character, ArrayList<String>> grammar = new HashMap<>();
    static HashMap<Character, HashSet<Character>> first = new HashMap<>();
    static HashMap<Character, HashSet<Character>> follow = new HashMap<>();
    static HashMap<Character, HashMap<Character, String>> parsingTable = new HashMap<>();
    static HashSet<Character> terminals = new HashSet<>();

    static char startSymbol;

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter the number of productions: ");
        int n = sc.nextInt();
        sc.nextLine();

        System.out.println("\n[Enter terminals in lowercase letters]");
        System.out.println("[Enter non terminals in uppercase letters]");
        System.out.println("[Enter # for null productions]");
        System.out.println("[ eg:\tA=aA|# ]");
        System.out.println("\nEnter productions: ");
        System.out.println("--------------------"); 

        for(int i=0; i<n; i++)
        {
            String input = sc.nextLine();
            char lhs = input.charAt(0);

            if(i == 0)
                startSymbol = lhs;

            String rhs = input.substring(2);
            String[] productions = rhs.split("\\|");

            ArrayList<String> list = new ArrayList<>();

            for(String p: productions)
            {
                for(char c: p.toCharArray())
                {
                    if(!Character.isUpperCase(c) && c!='#')
                        terminals.add(c);
                }
            }
            list.addAll(Arrays.asList(productions));

            grammar.put(lhs,list);
        }

        terminals.add('$');

        // Initialize FIRST and FOLLOW sets
        for(char nonTerminal : grammar.keySet())
        {
            first.put(nonTerminal, new HashSet<>());
            follow.put(nonTerminal, new HashSet<>());
        }

        // Compute FIRST
        for(char nonTerminal : grammar.keySet())
            computeFirst(nonTerminal);
        
        // Add $ to start symbol FOLLOW
        follow.get(startSymbol).add('$');

        // Compute FOLLOW
        boolean changed = true;

        while(changed)
        {
            changed = false;

            for(char nonTerminal : grammar.keySet())
            {
                int before = follow.get(nonTerminal).size();
                computeFollow(nonTerminal);
                int after = follow.get(nonTerminal).size();

                if(after > before)
                    changed = true;
            }
        }

        // Print FIRST
        System.out.println("\nFIRST Sets: ");
        System.out.println("-------------");
        for(char nonTerminal : first.keySet())
        {
            int count = 0;
            int size = first.get(nonTerminal).size();
            System.out.print("FIRST(" + nonTerminal + ") = {");
            for(char c : first.get(nonTerminal))
            {
                count++;
                System.out.print(c);
                if(count < size)
                    System.out.print(",");
            }
            System.out.println("}");
        }

        // Print FOLLOW
        System.out.println("\nFOLLOW Sets: ");
        System.out.println("--------------");
        for(char nonTerminal : follow.keySet())
        {
            int count = 0;
            int size = follow.get(nonTerminal).size();
            System.out.print("FOLLOW(" + nonTerminal + ") = {");
            for(char c : follow.get(nonTerminal))
            {
                count++;
                System.out.print(c);
                if(count < size)
                    System.out.print(",");
            }
            System.out.println("}");
        }

        // Constructing Parsing Table
        constructParsingTable();
        printParsingTable();

        // Validating String
        System.out.print("\nEnter string to validate: ");
        String text = sc.nextLine();
        validateString(text);

        sc.close();
    }

    static void computeFirst(char symbol)
    {
        for(String production : grammar.get(symbol))
        {
            for(int i=0; i<production.length(); i++)
            {
                char ch = production.charAt(i);

                // If terminal
                if(!Character.isUpperCase(ch))
                {
                    first.get(symbol).add(ch);
                    break;
                }

                // If non-terminal
                else
                {
                    computeFirst(ch);
                    for(char c : first.get(ch))
                    {
                        if(c != '#')
                            first.get(symbol).add(c);
                    }

                    if(!first.get(ch).contains('#'))
                        break;

                    if(i == production.length() - 1)
                        first.get(symbol).add('#');
                }
            }
        }
    }

    static void computeFollow(char symbol)
    {
        for(char lhs : grammar.keySet())
        {
            for(String production : grammar.get(lhs))
            {
                for(int i=0; i<production.length(); i++)
                {
                    if(production.charAt(i) == symbol)
                    {
                        // If not last symbol
                        int j = i+1;
                        boolean epsilonInAll = true;                        
                        
                        while(j < production.length())
                        {
                            char next = production.charAt(j);

                            if(!Character.isUpperCase(next))
                            {
                                follow.get(symbol).add(next);
                                epsilonInAll = false;
                                break;
                            }
                            else
                            {
                                for(char c : first.get(next))
                                {
                                    if(c != '#')
                                        follow.get(symbol).add(c);
                                }

                                if(first.get(next).contains('#'))
                                    j++;
                                else
                                {
                                    epsilonInAll = false;
                                    break;
                                }
                            }
                        }

                        // If last symbol
                        if(j == production.length() && epsilonInAll)
                        {
                            if(lhs != symbol)
                                follow.get(symbol).addAll(follow.get(lhs));
                        }
                    }
                }
            }
        }
    }

    static HashSet<Character> firstOfString(String str)
    {
        HashSet<Character> result = new HashSet<>();

        for(int i=0; i<str.length(); i++)
        {
            char ch = str.charAt(i);

            if(!Character.isUpperCase(ch))
            {
                result.add(ch);
                return result;
            }

            for(char c : first.get(ch))
            {
                if(c!='#')
                    result.add(c);
            }

            if(!first.get(ch).contains('#'))
                return result;
        }

        result.add('#');
        return result;
    }

    static void constructParsingTable()
    {
        for(char nt: grammar.keySet())
        {
            parsingTable.put(nt, new HashMap<>());

            for(String production: grammar.get(nt))
            {
                HashSet<Character> firstSet = firstOfString(production);

                for(char t: firstSet)
                {
                    if(t!='#')
                        parsingTable.get(nt).put(t, production);
                }

                if(firstSet.contains('#'))
                {
                    for(char f: follow.get(nt))
                        parsingTable.get(nt).put(f, production);
                }
            }
        }
    }

    static void printParsingTable()
    {
        ArrayList<Character> termList = new ArrayList<>(terminals);
        ArrayList<Character> nonTermList = new ArrayList<>(grammar.keySet());

        System.out.println("\n==============================================");
        System.out.println("\n\t\tLL(1) Parsing Table");
        System.out.println("==============================================");
        System.out.printf("%10s", "");
        
        for(char t: termList)
            System.out.printf("%10c", t);

        System.out.println();

        for(char nt: nonTermList)
        {
            System.out.printf("%10c", nt);
            for(char t: termList)
            {
                String entry = parsingTable.get(nt).get(t);

                if(entry != null)
                    System.out.printf("%10s", nt + "-->" + entry);
                else
                    System.out.printf("%10s", "-");
            }
    
            System.out.println();
        }
        System.out.println("==============================================");
    }

    static void validateString(String input)
    {
        Stack<Character> stack = new Stack<>();

        stack.push('$');
        stack.push(startSymbol);

        input = input + "$";

        int i=0;

        System.out.println();
        System.out.println("==================================================");
        System.out.printf("%-20s %-20s %-20s\n", "Stack", "Input", "Action");
        System.out.println("==================================================");

        while(!stack.isEmpty())
        {
            String stackStr = stack.toString();
            String remainingInput = input.substring(i);

            char top = stack.peek();
            char current = input.charAt(i);

            if(top == current)
            {
                System.out.printf("%-20s %-20s Match %c\n", stackStr, remainingInput, current);

                stack.pop();
                i++;
            }

            else if(!Character.isUpperCase(top))
            {
                System.out.printf("%-20s %-20s Error %c\n", stackStr, remainingInput,current);
                System.out.println("String Rejected");
                return;
            }

            else
            {
                String production = parsingTable.get(top).get(current);

                if(production == null)
                {
                    System.out.printf("%-20s %-20s Error %c\n", stackStr, remainingInput, current);
                    System.out.println("String rejected");
                    return;
                }

                System.out.printf("%-20s %-20s %c->%s\n", stackStr, remainingInput, top, production);

                stack.pop();

                if(!production.equals('#'))
                {
                    for(int j = production.length()-1; j>=0; j--)
                        stack.push(production.charAt(j));
                }
            }
        }

        if(i == input.length())
        {
            System.out.println("==================================================");
            System.out.println("\t\tString Accepted");
            System.out.println("==================================================");
        }
        else
            System.out.println("String Rejected");
    }
}