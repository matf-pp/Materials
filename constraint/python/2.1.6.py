# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import ExactSumConstraint, Problem

def magic_sequences(n):
    problem = Problem()
    names = list(range(n))
    problem.addVariables(names, range(n))
    problem.addConstraint(ExactSumConstraint(n), names)
    problem.addConstraint(lambda *values: sum(i * value for i, value in enumerate(values)) == n, names)
    for i in names:
        problem.addConstraint(lambda *values, i=i: values.count(i) == values[i], names)
    return [list(solution[i] for i in names) for solution in problem.getSolutions()]

def main():
    n = int(input('Duzina sekvence: '))
    solutions = magic_sequences(n)
    if not solutions:
        print(f'Ne postoji magicna sekvenca duzine {n}')
    for solution in solutions:
        print(solution)

if __name__ == '__main__':
    main()
