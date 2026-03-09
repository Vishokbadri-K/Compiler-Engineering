import java.util.*;

class FirstFollow 
{
    static HashMap<Character, ArrayList<String>> grammar = new HashMap<>();
    static HashMap<Character, HashSet<Character>> first = new HashMap<>();
    static HashMap<Character, HashSet<Character>> follow = new HashMap<>();
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
        System.out.println("[ eg: \n\tA=aA|# ]");
        System.out.println("\nEnter productions: ");        
        for(int i=0; i<n; i++)
        {
            String input = sc.nextLine();
            char lhs = input.charAt(0);

            if(i == 0)
                startSymbol = lhs;

            String rhs = input.substring(2);
            String[] productions = rhs.split("\\|");

            ArrayList<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(productions));

            grammar.put(lhs,list);
        }

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
}