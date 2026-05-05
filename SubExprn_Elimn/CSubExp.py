def normalize(expr):
    for op in ['+', '*', '&', '|', '^']:
        if op in expr:
            left, right = expr.split(op, 1)
            return op.join(sorted([left.strip(), right.strip()]))
    return expr.strip()


print("Enter number of Three-Address Statements: ", end="")
n = int(input())

code, lhs, rhs = [], [], []

print("Enter statements: [eg: t1=a+b]")
for _ in range(n):
    line = input()
    code.append(line)
    l, r = line.split("=", 1)
    lhs.append(l.strip())
    rhs.append(r.strip())

print("\nOriginal Three Address Code:")
for s in code:
    print(s)

table = {}
print("\nOptimized Three Address Code:")
for i in range(n):
    key = normalize(rhs[i])
    if key in table:
        print(lhs[i] + "=" + table[key])
    else:
        table[key] = lhs[i]
        print(code[i])