action_table = {}
goto_table = {}
productions = {}
states = set()


def build_stack_string(symbol_stack, state_stack):
    sb = str(state_stack[0])
    for k in range(len(symbol_stack)):
        sb += " " + symbol_stack[k] + " " + str(state_stack[k + 1])
    return sb


def print_tables():
    print("\n======================================")
    print("\tLR(1) ACTION TABLE")
    print("======================================")
    for s in sorted(states):
        for t in action_table.get(s, {}):
            print(f"ACTION[{s},{t}] = {action_table[s][t]}")

    print("\n======================================")
    print("\tLR(1) GOTO TABLE")
    print("======================================")
    for s in sorted(states):
        for nt in goto_table.get(s, {}):
            print(f"GOTO[{s},{nt}] = {goto_table[s][nt]}")


def parse(input_str):
    state_stack = [0]
    symbol_stack = []
    i = 0

    print("\n===========================================================================")
    print(f"{'Stack':<30} {'Input':<20} {'Action':<20}")
    print("===========================================================================")

    while True:
        state = state_stack[-1]
        current = input_str[i]
        action = action_table.get(state, {}).get(current)
        stack_str = build_stack_string(symbol_stack, state_stack)
        remaining = input_str[i:]

        if action is None:
            print(f"{stack_str:<30} {remaining:<20} ERROR")
            print("\n===========================================================================")
            print("\t\tString Rejected")
            print("===========================================================================")
            return

        if action == "acc":
            print(f"{stack_str:<30} {remaining:<20} ACCEPT")
            print("\n===========================================================================")
            print("\t\tString Accepted")
            print("=============================================================================")
            return

        elif action.startswith("s"):
            print(f"{stack_str:<30} {remaining:<20} Shift {current}")
            symbol_stack.append(current)
            state_stack.append(int(action[1:]))
            i += 1

        elif action.startswith("r"):
            prod_num = int(action[1:])
            production = productions[prod_num]
            lhs = production[0]
            rhs = production[production.index('=') + 1:]
            print(f"{stack_str:<30} {remaining:<20} Reduce by {production}")
            if rhs != "#":
                for _ in range(len(rhs)):
                    symbol_stack.pop()
                    state_stack.pop()
            symbol_stack.append(lhs)
            top_state = state_stack[-1]
            if top_state not in goto_table or lhs not in goto_table[top_state]:
                print(f"{build_stack_string(symbol_stack, state_stack):<30} {remaining:<20} ERROR")
                print("\n===========================================================================")
                print("\t\tString Rejected")
                print("===========================================================================")
                return
            state_stack.append(goto_table[top_state][lhs])


print("\nEnter number of productions: ", end="")
p = int(input())

print("\nEnter productions (Example: S=CC  OR  C=cC|d)")

prod_index = 1
for i in range(1, p + 1):
    prod = input()
    lhs = prod[0]
    rhs = prod[prod.index('=') + 1:]
    for alt in rhs.split("|"):
        productions[prod_index] = lhs + "=" + alt.strip()
        print(f"  Stored production {prod_index}: {lhs}={alt.strip()}")
        prod_index += 1

print("\nEnter ACTION table entries")
print("Format: state terminal action")
print("Example: 0 c s3   OR   4 $ acc")
print("Type 'end' to stop")

while True:
    line = input()
    if line == "end":
        break
    parts = line.split()
    state, terminal, action = int(parts[0]), parts[1][0], parts[2]
    action_table.setdefault(state, {})[terminal] = action
    states.add(state)

print("\nEnter GOTO table entries")
print("Format: state nonTerminal nextState")
print("Example: 0 S 1")
print("Type 'end' to stop")

while True:
    line = input()
    if line == "end":
        break
    parts = line.split()
    state, nt, next_state = int(parts[0]), parts[1][0], int(parts[2])
    goto_table.setdefault(state, {})[nt] = next_state
    states.update([state, next_state])

print("\n======================================")
print("\tExpanded Productions")
print("======================================")
for k in sorted(productions):
    print(f"  {k}. {productions[k]}")

print_tables()

print("\nEnter input string: ", end="")
parse(input() + "$")