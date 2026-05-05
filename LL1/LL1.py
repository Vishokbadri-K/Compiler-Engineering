grammar = {}
first = {}
follow = {}
parsing_table = {}
terminals = set()
start_symbol = None


def compute_first(symbol):
    for production in grammar[symbol]:
        i = 0
        while i < len(production):
            ch = production[i]
            if not ch.isupper():
                first[symbol].add(ch)
                break
            compute_first(ch)
            first[symbol].update(first[ch] - {'#'})
            if '#' not in first[ch]:
                break
            if i == len(production) - 1:
                first[symbol].add('#')
            i += 1


def compute_follow(symbol):
    for lhs in grammar:
        for production in grammar[lhs]:
            for i, ch in enumerate(production):
                if ch != symbol:
                    continue
                j = i + 1
                while j < len(production):
                    nxt = production[j]
                    if not nxt.isupper():
                        follow[symbol].add(nxt)
                        break
                    follow[symbol].update(first[nxt] - {'#'})
                    if '#' not in first[nxt]:
                        break
                    j += 1
                else:
                    if lhs != symbol:
                        follow[symbol].update(follow[lhs])


def first_of_string(string):
    result = set()
    for ch in string:
        if not ch.isupper():
            result.add(ch)
            return result
        result.update(first[ch] - {'#'})
        if '#' not in first[ch]:
            return result
    result.add('#')
    return result


def construct_parsing_table():
    for nt in grammar:
        parsing_table[nt] = {}
        for production in grammar[nt]:
            first_set = first_of_string(production)
            for t in first_set - {'#'}:
                parsing_table[nt][t] = production
            if '#' in first_set:
                for f in follow[nt]:
                    parsing_table[nt][f] = production


def print_parsing_table():
    term_list = list(terminals)
    print("\n==============================================")
    print("\n\t\tLL(1) Parsing Table")
    print("==============================================")
    print(f"{'':>10}", end="")
    for t in term_list:
        print(f"{t:>10}", end="")
    print()
    for nt in grammar:
        print(f"{nt:>10}", end="")
        for t in term_list:
            entry = parsing_table[nt].get(t)
            print(f"{(nt + '-->' + entry) if entry else '-':>10}", end="")
        print()
    print("==============================================")


def validate_string(input_str):
    stack = ['$', start_symbol]
    input_str += "$"
    i = 0
    print("\n==================================================")
    print(f"{'Stack':<20} {'Input':<20} {'Action':<20}")
    print("==================================================")
    while stack:
        top = stack[-1]
        current = input_str[i]
        remaining = input_str[i:]
        if top == current:
            print(f"{str(stack):<20} {remaining:<20} Match {current}")
            stack.pop()
            i += 1
        elif not top.isupper():
            print(f"{str(stack):<20} {remaining:<20} Error {current}")
            print("String Rejected")
            return
        else:
            production = parsing_table[top].get(current)
            if production is None:
                print(f"{str(stack):<20} {remaining:<20} Error {current}")
                print("String Rejected")
                return
            print(f"{str(stack):<20} {remaining:<20} {top}->{production}")
            stack.pop()
            if production != '#':
                for ch in reversed(production):
                    stack.append(ch)
    print("==================================================")
    print("\t\tString Accepted" if i == len(input_str) else "String Rejected")
    print("==================================================")


print("\nEnter the number of productions: ", end="")
n = int(input())

print("\n[Enter terminals in lowercase letters]")
print("[Enter non terminals in uppercase letters]")
print("[Enter # for null productions]")
print("[ eg:\tA=aA|# ]")
print("\nEnter productions: ")
print("--------------------")

for i in range(n):
    line = input()
    lhs = line[0]
    if i == 0:
        start_symbol = lhs
    productions = line[2:].split("|")
    for p in productions:
        for c in p:
            if not c.isupper() and c != '#':
                terminals.add(c)
    grammar[lhs] = productions

terminals.add('$')

for nt in grammar:
    first[nt] = set()
    follow[nt] = set()

for nt in grammar:
    compute_first(nt)

follow[start_symbol].add('$')

changed = True
while changed:
    changed = False
    for nt in grammar:
        before = len(follow[nt])
        compute_follow(nt)
        if len(follow[nt]) > before:
            changed = True

print("\nFIRST Sets: ")
print("-------------")
for nt in first:
    print(f"FIRST({nt}) = " + "{" + ",".join(first[nt]) + "}")

print("\nFOLLOW Sets: ")
print("--------------")
for nt in follow:
    print(f"FOLLOW({nt}) = " + "{" + ",".join(follow[nt]) + "}")

construct_parsing_table()
print_parsing_table()

print("\nEnter string to validate: ", end="")
validate_string(input())