# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import AllDifferentConstraint, Problem

def solve():
    problem = Problem()
    problem.addVariable('A', range(1, 10))
    problem.addVariables(['B', 'C'], range(10))
    problem.addConstraint(AllDifferentConstraint(), ['A', 'B', 'C'])
    solutions = problem.getSolutions()
    return min(solutions, key=lambda r: (100*r['A'] + 10*r['B'] + r['C']) / (r['A'] + r['B'] + r['C']))

def main():
    r = solve()
    print(100*r['A'] + 10*r['B'] + r['C'])

if __name__ == '__main__':
    main()
