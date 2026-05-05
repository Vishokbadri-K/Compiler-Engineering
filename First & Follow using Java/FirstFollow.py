grammar = {}
first = {}
follow = {}
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


print("\nEnter the number of productions: ", end="")
n = int(input())

print("\n[Enter terminals in lowercase letters]")
print("[Enter non terminals in uppercase letters]")
print("[Enter # for null productions]")
print("[ eg: \n\tA=aA|# ]")
print("\nEnter productions: ")

for i in range(n):
    line = input()
    lhs = line[0]
    if i == 0:
        start_symbol = lhs
    grammar[lhs] = line[2:].split("|")

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
for nt in first:
    print(f"FIRST({nt}) = " + "{" + ",".join(first[nt]) + "}")

print("\nFOLLOW Sets: ")
for nt in follow:
    print(f"FOLLOW({nt}) = " + "{" + ",".join(follow[nt]) + "}")