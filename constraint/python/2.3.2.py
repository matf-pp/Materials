from constraint import AllDifferentConstraint, Problem

def solve():
    problem = Problem()
    problem.addVariables('ABCDEGFIH', range(1, 10))
    problem.addConstraint(AllDifferentConstraint(), 'ABCDEGFIH')
    problem.addConstraint(lambda a,b,c,d,e: a < b < c < d < e and a+b+c+d+e == 25, 'ABCDE')
    problem.addConstraint(lambda g,f,c,h,i: g < f < c < h < i and g+f+c+h+i == 25, 'GFCIH')
    return problem.getSolutions()

def main():
    for r in solve():
        print(f"{r['A']}       {r['G']}")
        print(f"  {r['B']}   {r['F']}")
        print(f"    {r['C']}")
        print(f"  {r['D']}   {r['H']}")
        print(f"{r['E']}       {r['I']}")
        print()

if __name__ == '__main__':
    main()
