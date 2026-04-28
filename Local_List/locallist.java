import java.util.*;

class Operation
{
    String name;
    int delay;
    List<Operation> successors = new ArrayList<>();
    List<Operation> predecessors = new ArrayList<>();
    int startTime = -1;

    Operation(String name, int delay)
    {
        this.name = name;
        this.delay = delay;
    }

    boolean isReady()
    {
        for (Operation op: predecessors)
        {
            if(op.startTime == -1) return false;
        }
        return true;
    }
}

class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        Map<String, Operation> map = new HashMap<>();

        // INPUTS
        System.out.println("Enter the operations and cost (type 'end' to stop):");
        while(true)
        {
            String name = sc.next();
            if(name.equals("end")) break;

            int cost = sc.nextInt();
            map.put(name, new Operation(name,cost));
        }

        System.out.println();
        System.out.println("Enter the edges (type 'end' to stop):");
        while(true)
        {
            String u = sc.next();
            if(u.equals("end")) break;

            String v = sc.next();
            Operation from = map.get(u);
            Operation to = map.get(v);

            from.successors.add(to);
            to.predecessors.add(from);
        }
        System.out.println();

        // INITIALIZE
        List<Operation> ready = new ArrayList<>();
        List<Operation> active = new ArrayList<>();

        for(Operation op: map.values())
        {
            if(op.predecessors.size() == 0)
            {
                ready.add(op);
            }
        }

        int cycle = 1;

        // MAIN
        while(!ready.isEmpty() || !active.isEmpty())
        {
            // Print current State
            System.out.print("[");
            for(int i=0; i<ready.size(); i++)
            {
                System.out.print(ready.get(i).name);
                if(i != ready.size()-1)
                    System.out.print(" ");
            }
            System.out.print("] ");

            System.out.print("[");
            for(int i=0; i<active.size(); i++)
            {
                System.out.print(active.get(i).name);
                if(i != active.size()-1)
                    System.out.print(" ");
            }
            System.out.println("] ");

            // Schedule one Output
            if(!ready.isEmpty())
            {
                Operation op = ready.remove(0);
                op.startTime = cycle;
                active.add(op);
            }

            cycle++;

            // Check active
            Iterator<Operation> it = active.iterator();
            while(it.hasNext())
            {
                Operation op = it.next();
                if(op.startTime + op.delay <= cycle)
                {
                    it.remove();
                    for(Operation s: op.successors)
                    {
                        if(s.isReady() && !ready.contains(s))
                        {
                            ready.add(s);
                        }
                    }
                }
            }
        }
        System.out.println("\nTotal clock cycles: " + cycle);
    }
}
