from constraint import AllDifferentConstraint, ExactSumConstraint, Problem

LINES = ['abc', 'def', 'ghi', 'adg', 'beh', 'cfi', 'aei', 'ceg']

def solve():
    problem = Problem()
    problem.addVariables('abcdefghi', range(1, 10))
    problem.addConstraint(AllDifferentConstraint(), 'abcdefghi')
    for line in LINES:
        problem.addConstraint(ExactSumConstraint(15), line)
    return problem.getSolutions()

def main():
    for r in solve():
        print(r['a'], r['b'], r['c'])
        print(r['d'], r['e'], r['f'])
        print(r['g'], r['h'], r['i'])
        print()

if __name__ == '__main__':
    main()
