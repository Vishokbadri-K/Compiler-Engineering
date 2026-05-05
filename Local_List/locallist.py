class Operation:
    def __init__(self, name, delay):
        self.name = name
        self.delay = delay
        self.successors = []
        self.predecessors = []
        self.start_time = -1

    def is_ready(self):
        return all(p.start_time != -1 for p in self.predecessors)


op_map = {}

print("Enter the operations and cost (type 'end' to stop):")
while True:
    line = input().strip()
    if line == "end":
        break
    name, cost = line.split()
    op_map[name] = Operation(name, int(cost))

print()
print("Enter the edges (type 'end' to stop):")
while True:
    line = input().strip()
    if line == "end":
        break
    u, v = line.split()
    op_map[u].successors.append(op_map[v])
    op_map[v].predecessors.append(op_map[u])

print()

ready = [op for op in op_map.values() if not op.predecessors]
active = []
cycle = 1

while ready or active:
    print("[" + " ".join(op.name for op in ready) + "] ", end="")
    print("[" + " ".join(op.name for op in active) + "] ")

    if ready:
        op = ready.pop(0)
        op.start_time = cycle
        active.append(op)

    cycle += 1

    still_active = []
    for op in active:
        if op.start_time + op.delay <= cycle:
            for s in op.successors:
                if s.is_ready() and s not in ready:
                    ready.append(s)
        else:
            still_active.append(op)
    active = still_active

print("\nTotal clock cycles: " + str(cycle))