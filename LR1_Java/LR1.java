import java.util.*;

class LR1
{
    // Static tables for LR(1) parser
    static HashMap<Integer, HashMap<Character, String>> actionTable = new HashMap<>();
    static HashMap<Integer, HashMap<Character, Integer>> gotoTable = new HashMap<>();
    static HashMap<Integer, String> productions = new HashMap<>();
    static HashSet<Integer> states = new HashSet<>();

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter number of productions: ");
        int p = sc.nextInt();
        sc.nextLine();

        System.out.println("\nEnter productions (Example: S=CC  OR  C=cC|d)");

        // Track production number separately to handle | expansions
        int prodIndex = 1;  

        for(int i = 1; i <= p; i++)
        {
            String prod = sc.nextLine();
            char lhs = prod.charAt(0);
            String rhs = prod.substring(prod.indexOf('=') + 1);

            // Split by '|' and store each alternative as a separate production
            String[] alternatives = rhs.split("\\|");

            for(String alt : alternatives)
            {
                productions.put(prodIndex, lhs + "=" + alt.trim());
                prodIndex++;
            }
        }

        System.out.println("\nEnter ACTION table entries");
        System.out.println("Format: state terminal action");
        System.out.println("Example: 0 c s3   OR   4 $ acc");
        System.out.println("Type 'end' to stop");

        while(true)
        {
            String line = sc.nextLine();

            if(line.equals("end"))
                break;

            String[] parts = line.split(" ");

            int state = Integer.parseInt(parts[0]);
            char terminal = parts[1].charAt(0);
            String action = parts[2];

            actionTable.putIfAbsent(state, new HashMap<>());
            actionTable.get(state).put(terminal, action);

            states.add(state);
        }

        System.out.println("\nEnter GOTO table entries");
        System.out.println("Format: state nonTerminal nextState");
        System.out.println("Example: 0 S 1");
        System.out.println("Type 'end' to stop");

        while(true)
        {
            String line = sc.nextLine();

            if(line.equals("end"))
                break;

            String[] parts = line.split(" ");

            int state = Integer.parseInt(parts[0]);
            char nt = parts[1].charAt(0);
            int next = Integer.parseInt(parts[2]);

            gotoTable.putIfAbsent(state, new HashMap<>());
            gotoTable.get(state).put(nt, next);

            states.add(state);
            states.add(next);
        }

        // Print expanded productions for user reference
        System.out.println("\n======================================");
        System.out.println("\tExpanded Productions");
        System.out.println("======================================");
        for(int k : new TreeSet<>(productions.keySet()))
            System.out.println("  " + k + ". " + productions.get(k));

        System.out.print("\nEnter input string: ");
        String input = sc.nextLine() + "$";

        printTables();
        parse(input);

        sc.close();
    }

    static void printTables()
    {
        TreeSet<Integer> sortedStates = new TreeSet<>(states);

        System.out.println("\n======================================");
        System.out.println("\tLR(1) ACTION TABLE");
        System.out.println("======================================");

        for(int s : sortedStates)
        {
            if(actionTable.containsKey(s))
            {
                for(char t : actionTable.get(s).keySet())
                {
                    System.out.println("ACTION[" + s + "," + t + "] = " +
                            actionTable.get(s).get(t));
                }
            }
        }

        System.out.println("\n======================================");
        System.out.println("\tLR(1) GOTO TABLE");
        System.out.println("======================================");

        for(int s : sortedStates)
        {
            if(gotoTable.containsKey(s))
            {
                for(char nt : gotoTable.get(s).keySet())
                {
                    System.out.println("GOTO[" + s + "," + nt + "] = " +
                            gotoTable.get(s).get(nt));
                }
            }
        }
    }

    static void parse(String input)
    {
        Stack<Integer> stateStack = new Stack<>();
        Stack<String> symbolStack = new Stack<>();

        stateStack.push(0);

        int i = 0;

        System.out.println("\n===========================================================================");
        System.out.printf("%-30s %-20s %-20s\n", "Stack", "Input", "Action");
        System.out.println("===========================================================================");

        while(true)
        {
            int state = stateStack.peek();
            char current = input.charAt(i);

            String action = null;

            if(actionTable.containsKey(state))
                action = actionTable.get(state).get(current);

            String stackStr = buildStackString(symbolStack, stateStack);
            String remainingInput = input.substring(i);

            if(action == null)
            {
                System.out.printf("%-30s %-20s ERROR\n", stackStr, remainingInput);
                System.out.println("\n===========================================================================");
                System.out.println("\t\tString Rejected");
                System.out.println("===========================================================================");
                return;
            }

            if(action.equals("acc"))
            {
                System.out.printf("%-30s %-20s ACCEPT\n", stackStr, remainingInput);
                System.out.println("\n===========================================================================");
                System.out.println("\t\tString Accepted");
                System.out.println("=============================================================================");
                return;
            }

            else if(action.startsWith("s"))
            {
                int nextState = Integer.parseInt(action.substring(1));

                System.out.printf("%-30s %-20s Shift %c\n", stackStr, remainingInput, current);

                symbolStack.push(String.valueOf(current));
                stateStack.push(nextState);

                i++;
            }

            else if(action.startsWith("r"))
            {
                int prodNum = Integer.parseInt(action.substring(1));
                String production = productions.get(prodNum);

                char lhs = production.charAt(0);
                String rhs = production.substring(production.indexOf('=') + 1);

                System.out.printf("%-30s %-20s Reduce by %s\n",stackStr, remainingInput, production);

                if(!rhs.equals("#"))
                {
                    for(int k = 0; k < rhs.length(); k++)
                    {
                        symbolStack.pop();
                        stateStack.pop();
                    }
                }

                symbolStack.push(String.valueOf(lhs));

                int topState = stateStack.peek();

                if(!gotoTable.containsKey(topState) || !gotoTable.get(topState).containsKey(lhs))
                {
                    System.out.printf("%-30s %-20s ERROR (missing GOTO[%d,%c])\n",
                            buildStackString(symbolStack, stateStack), remainingInput, topState, lhs);
                    System.out.println("\n===========================================================================");
                    System.out.println("\t\tString Rejected");
                    System.out.println("===========================================================================");
                    return;
                }

                int gotoState = gotoTable.get(topState).get(lhs);
                stateStack.push(gotoState);
            }
        }
    }

    static String buildStackString(Stack<String> symbolStack, Stack<Integer> stateStack)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(stateStack.get(0));

        for(int k = 0; k < symbolStack.size(); k++)
        {
            sb.append(" ").append(symbolStack.get(k));
            sb.append(" ").append(stateStack.get(k + 1));
        }

        return sb.toString();
    }
}


/*

================ SAMPLE INPUT =================

Enter number of productions: 2

Enter productions:
S=CC
C=cC|d

ACTION TABLE
------------
0 c s3
0 d s4
1 $ acc
2 c s3
2 d s4
3 c s3
3 d s4
4 c r3
4 d r3
4 $ r3
5 $ r1
6 c r2
6 d r2
6 $ r2
end

GOTO TABLE
----------
0 S 1
0 C 2
2 C 5
3 C 6
end

Input: dd
Expected: String Accepted

================================================

*/