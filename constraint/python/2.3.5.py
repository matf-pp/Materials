# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import AllDifferentConstraint, Problem

def solve():
    problem = Problem()
    cols = list(range(8))
    problem.addVariables(cols, range(8))
    problem.addConstraint(AllDifferentConstraint(), cols)
    for c1 in cols:
        for c2 in cols:
            if c1 < c2:
                problem.addConstraint(lambda r1, r2, c1=c1, c2=c2: abs(r1-r2) != abs(c1-c2), [c1, c2])
    return problem.getSolutions()

def main():
    for r in solve():
        for row in range(8):
            print(''.join('D' if r[col] == row else '-' for col in range(8)))
        print()

if __name__ == '__main__':
    main()
