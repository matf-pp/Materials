from constraint import AllDifferentConstraint, Problem

def solve():
    problem = Problem()
    cols = list(range(8))
    problem.addVariables(cols, range(8))
    problem.addConstraint(AllDifferentConstraint(), cols)
    return problem.getSolutions()

def main():
    for r in solve():
        for row in range(8):
            print(''.join('T' if r[row] == col else '-' for col in range(8)))
        print()

if __name__ == '__main__':
    main()
