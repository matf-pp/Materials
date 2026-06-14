# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import AllDifferentConstraint, Problem


def solve(puzzle):
    problem = Problem()
    cells = [(r, c) for r in range(9) for c in range(9)]
    problem.addVariables(cells, range(1, 10))
    for r in range(9):
        problem.addConstraint(AllDifferentConstraint(), [(r, c) for c in range(9)])
    for c in range(9):
        problem.addConstraint(AllDifferentConstraint(), [(r, c) for r in range(9)])
    for br in range(0, 9, 3):
        for bc in range(0, 9, 3):
            problem.addConstraint(AllDifferentConstraint(), [(r, c) for r in range(br, br+3) for c in range(bc, bc+3)])
    for r, row in enumerate(puzzle):
        for c, value in enumerate(row):
            if value:
                problem.addConstraint(lambda x, value=value: x == value, [(r, c)])
    return problem.getSolutions()


def print_solution(solution):
    for r in range(9):
        print(' '.join(str(solution[(r, c)]) for c in range(9)))
    print()


def main():
    import json
    path = input('Unesite ime datoteke sa tablom za sudoku: ').strip()
    with open(path) as f:
        puzzle = json.load(f)
    for solution in solve(puzzle):
        print_solution(solution)


if __name__ == '__main__':
    main()
